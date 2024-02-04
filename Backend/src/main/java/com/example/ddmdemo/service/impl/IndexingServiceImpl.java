package com.example.ddmdemo.service.impl;

import com.example.ddmdemo.dto.CreateIndexDTO;
import com.example.ddmdemo.exceptionhandling.exception.LoadingException;
import com.example.ddmdemo.exceptionhandling.exception.StorageException;
import com.example.ddmdemo.indexmodel.DummyIndex;
import com.example.ddmdemo.indexmodel.IndexUnit;
import com.example.ddmdemo.indexrepository.DummyIndexRepository;
import com.example.ddmdemo.indexrepository.IndexUnitRepository;
import com.example.ddmdemo.model.DummyTable;
import com.example.ddmdemo.model.GovernmentInfo;
import com.example.ddmdemo.model.IndexUnitInfo;
import com.example.ddmdemo.respository.DummyRepository;
import com.example.ddmdemo.respository.GovernmentInfoRepository;
import com.example.ddmdemo.respository.IndexUnitInfoRepository;
import com.example.ddmdemo.service.interfaces.FileService;
import com.example.ddmdemo.service.interfaces.IndexingService;
import jakarta.transaction.Transactional;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.apache.tika.language.detect.LanguageDetector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService {

    private final DummyIndexRepository dummyIndexRepository;

    private final DummyRepository dummyRepository;

    private final IndexUnitRepository indexUnitRepository;

    private final GovernmentInfoRepository governmentInfoRepository;
    private final FileService fileService;

    private final LanguageDetector languageDetector;

    //private final LocationIqClient locationIqClient;

   /* @Value("${location.api.key}")
    private String apiKey;*/


    @Override
    @Transactional
    public String indexDocument(MultipartFile documentFile) {
        var newEntity = new DummyTable();
        var newIndex = new DummyIndex();

        var title = Objects.requireNonNull(documentFile.getOriginalFilename()).split("\\.")[0];
        newIndex.setTitle(title);
        newEntity.setTitle(title);

        var documentContent = extractDocumentContent(documentFile);
        if (detectLanguage(documentContent).equals("SR")) {
            newIndex.setContentSr(documentContent);
        } else {
            newIndex.setContentEn(documentContent);
        }
        newEntity.setTitle(title);

        var serverFilename = fileService.store(documentFile, UUID.randomUUID().toString());
        newIndex.setServerFilename(serverFilename);
        newEntity.setServerFilename(serverFilename);

        newEntity.setMimeType(detectMimeType(documentFile));
        var savedEntity = dummyRepository.save(newEntity);

        newIndex.setDatabaseId(savedEntity.getId());
        dummyIndexRepository.save(newIndex);

        return serverFilename;
    }

    @Override
    @Transactional
    public void createIndex(CreateIndexDTO indexingUnit, Integer governmentId) {

        GovernmentInfo governmentInfo = governmentInfoRepository.findById(governmentId).get();

        IndexUnit newIndex = new IndexUnit();
        newIndex.setName(indexingUnit.getName()); //treba da isparsira iz dokumenta
        newIndex.setSurname(indexingUnit.getSurname()); //treba da isparsira iz dokumenta
        newIndex.setGovernmentName(governmentInfo.getName());
        newIndex.setGovernmentType(governmentInfo.getType().toString());
        newIndex.setContractContent(extractContractContent(indexingUnit.getContract()));
        newIndex.setLawContent(extractDocumentContent(indexingUnit.getLaw()));
//        var location = locationIqClient.forwardGeolocation(apiKey, indexingUnit.getAddress(), "json").get(0);
//        newIndex.setLocation(new GeoPoint(location.getLat(), location.getLon()));
        indexUnitRepository.save(newIndex);

        fileService.store(indexingUnit.getContract(), UUID.randomUUID().toString());
        fileService.store(indexingUnit.getLaw(), UUID.randomUUID().toString());

    }

    private String extractDocumentContent(MultipartFile multipartPdfFile) {  // full-text -> zakon
        String documentContent;
        try (var pdfFile = multipartPdfFile.getInputStream()) {
            var pdDocument = PDDocument.load(pdfFile);
            var textStripper = new PDFTextStripper();
            documentContent = textStripper.getText(pdDocument);
            pdDocument.close();
        } catch (IOException e) {
            throw new LoadingException("Error while trying to load PDF file content.");
        }

        return documentContent;
    }

    private String extractContractContent(MultipartFile multipartPdfFile) {  // full-text -> zakon
        String documentContent;
        try (var pdfFile = multipartPdfFile.getInputStream()) {
            var pdDocument = PDDocument.load(pdfFile);
            var textStripper = new PDFTextStripper();
            documentContent = textStripper.getText(pdDocument);
            Stream.of(documentContent.split("\n"))
                    .map(String::trim)
                    .collect(Collectors.toList());
            pdDocument.close();
        } catch (IOException e) {
            throw new LoadingException("Error while trying to load PDF file content.");
        }

        return documentContent;
    }

    private String detectLanguage(String text) {
        var detectedLanguage = languageDetector.detect(text).getLanguage().toUpperCase();
        if (detectedLanguage.equals("HR")) {
            detectedLanguage = "SR";
        }

        return detectedLanguage;
    }

    private String detectMimeType(MultipartFile file) {
        var contentAnalyzer = new Tika();

        String trueMimeType;
        String specifiedMimeType;
        try {
            trueMimeType = contentAnalyzer.detect(file.getBytes());
            specifiedMimeType =
                Files.probeContentType(Path.of(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            throw new StorageException("Failed to detect mime type for file.");
        }

        if (!trueMimeType.equals(specifiedMimeType) &&
            !(trueMimeType.contains("zip") && specifiedMimeType.contains("zip"))) {
            throw new StorageException("True mime type is different from specified one, aborting.");
        }

        return trueMimeType;
    }
}