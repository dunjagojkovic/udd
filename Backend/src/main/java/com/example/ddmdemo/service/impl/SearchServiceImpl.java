package com.example.ddmdemo.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.ddmdemo.exceptionhandling.exception.MalformedQueryException;
import com.example.ddmdemo.indexmodel.IndexUnit;
import com.example.ddmdemo.service.interfaces.SearchService;
import java.util.List;
import io.minio.CloseableIterator;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.stereotype.Service;
import org.springframework.lang.Nullable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ElasticsearchOperations elasticsearchTemplate;

     @Override
    public Page<IndexUnit> simpleSearch(List<String> keywords, Pageable pageable) {

        var v = new HighlightFieldParameters.HighlightFieldParametersBuilder().withMatchedFields("lawContent").withType("plain").withFragmentSize(500).withFragmentOffset(250).withPostTags("</b>").withPreTags("<b>");
        var c = new HighlightParameters.HighlightParametersBuilder().withType("plain").withRequireFieldMatch(false).build();
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildSimpleSearchQuery(keywords))
                        .withHighlightQuery(new HighlightQuery(new Highlight(c, List.of(new HighlightField("lawContent", v.build()))), String.class))
                        .withPageable(pageable);

        return executeQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<IndexUnit> advancedSearch(List<String> expression, Pageable pageable) {
        if (expression.size() != 3) {
            throw new MalformedQueryException("Search query malformed.");
        }

        String operation = expression.get(1);
        expression.remove(1);
        var searchQueryBuilder =
            new NativeQueryBuilder().withQuery(buildAdvancedSearchQuery(expression, operation))
                .withPageable(pageable);

        return executeQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<IndexUnit> phraseSearch(String query, Pageable pageable) {

        var v = new HighlightFieldParameters.HighlightFieldParametersBuilder().withMatchedFields("lawContent").withType("plain").withFragmentSize(500).withFragmentOffset(250).withPostTags("</b>").withPreTags("<b>");
        var c = new HighlightParameters.HighlightParametersBuilder().withType("plain").withRequireFieldMatch(false).build();

        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildPhraseSearchQuery(query))
                        .withHighlightQuery(new HighlightQuery(new Highlight(c, List.of(new HighlightField("lawContent", v.build()))), String.class))
                        .withPageable(pageable);

        return executeQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<IndexUnit> geoSearch(String address, String radius, Pageable pageable) {
        return null;
    }

    private Query buildSimpleSearchQuery(List<String> tokens) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            tokens.forEach(token -> {
                b.should(sb -> sb.match(m -> m.field("name").fuzziness(Fuzziness.ONE.asString()).query(token)));
                b.should(sb -> sb.match(m -> m.field("surname").query(token)));
                b.should(sb -> sb.match(m -> m.field("governmentName").query(token)));
                b.should(sb -> sb.match(m -> m.field("governmentType").query(token)));
                b.should(sb -> sb.match(m -> m.field("contractContent").query(token)));
                b.should(sb -> sb.match(m -> m.field("lawContent").query(token)));
            });
            return b;
        })))._toQuery();
    }

    private Query buildAdvancedSearchQuery(List<String> operands, String operation) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            var field1 = operands.get(0).split(":")[0];
            var value1 = operands.get(0).split(":")[1];
            var field2 = operands.get(1).split(":")[0];
            var value2 = operands.get(1).split(":")[1];

            switch (operation) {
                case "AND":
                    b.must(sb -> sb.match(
                        m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.must(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
                case "OR":
                    b.should(sb -> sb.match(
                        m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.should(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
                case "NOT":
                    b.must(sb -> sb.match(
                        m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.mustNot(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
            }

            return b;
        })))._toQuery();
    }

    private Query buildPhraseSearchQuery(String query) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            b.should(sb -> sb.matchPhrase(m -> m.field("name").query(query).slop(1)));
            b.should(sb -> sb.matchPhrase(m -> m.field("surname").query(query).slop(1)));
            b.should(sb -> sb.matchPhrase(m -> m.field("governmentName").query(query).slop(1)));
            b.should(sb -> sb.matchPhrase(m -> m.field("governmentType").query(query).slop(1)));
            b.should(sb -> sb.matchPhrase(m -> m.field("contractContent").query(query).slop(1)));
            b.should(sb -> sb.matchPhrase(m -> m.field("lawContent").query(query).slop(1)));
            return b;
        })))._toQuery();
    }

    private Page<IndexUnit> executeQuery(NativeQuery searchQuery) {

        var searchHits = elasticsearchTemplate.search(searchQuery, IndexUnit.class,
                IndexCoordinates.of("contract_index"));

        var searchHitsPaged = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());

        return (Page<IndexUnit>) SearchServiceImpl.unwrapSearchHits(searchHitsPaged);
    }

    @Nullable
    public static Object unwrapSearchHits(@Nullable Object result) {
        if (result == null) {
            return result;
        } else if (result instanceof SearchHit) {
            var index = (IndexUnit) ((SearchHit) result).getContent();
            if (!((SearchHit<?>) result).getHighlightField("lawContent").isEmpty()) {
                index.setHighlight(((SearchHit<?>) result).getHighlightField("lawContent").get(0));
            }
            return index;
//            return new CustomResponse(((SearchHit<DummyIndex>) result).getContent(), ((SearchHit<DummyIndex>) result).getHighlightField("contentSr"));
        } else if (result instanceof List) {
            return ((List) result).stream().map(SearchServiceImpl::unwrapSearchHits).collect(Collectors.toList());
        } else if (result instanceof Stream) {
            return ((Stream) result).map(SearchHitSupport::unwrapSearchHits);
        } else if (result instanceof SearchHits) {
            SearchHits<?> searchHits = (SearchHits) result;
            return unwrapSearchHits(searchHits.getSearchHits());
        } else if (result instanceof SearchHitsIterator) {
            return unwrapSearchHitsIterator((SearchHitsIterator) result);
        } else if (result instanceof SearchPage) {
            SearchPage<?> searchPage = (SearchPage) result;
            List<?> content = (List) unwrapSearchHits(searchPage.getSearchHits());
            return new PageImpl(content, searchPage.getPageable(), searchPage.getTotalElements());
        } else {
            return result;
        }
    }

    private static CloseableIterator<?> unwrapSearchHitsIterator(final SearchHitsIterator<?> iterator) {
        return new CloseableIterator<Object>() {
            public boolean hasNext() {
                return iterator.hasNext();
            }

            public Object next() {
                return SearchServiceImpl.unwrapSearchHits(iterator.next());
            }

            public void close() {
                iterator.close();
            }
        };
    }
}
