package com.example.ddmdemo.indexmodel;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.lucene.spatial3d.geom.GeoPoint;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "contract_index")
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class IndexUnit {

    @Id
    private String id;

    @Field(type = FieldType.Text, store = true, name = "name", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String name;

    @Field(type = FieldType.Text, store = true, name = "surname", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String surname;

    @Field(type = FieldType.Text, store = true, name = "governmentName", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String governmentName;

    @Field(type = FieldType.Text, store = true, name = "governmentType", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String governmentType;

    @Field(type = FieldType.Text, store = true, name = "contractContent", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String contractContent;

    @Field(type = FieldType.Text, store = true, name = "lawContent", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String lawContent;

    @Field(type = FieldType.Text, store = true, name = "contract_filename")
    private String contractFilename;

    @Field(type = FieldType.Text, store = true, name = "law_filename")
    private String lawFilename;

    @GeoPointField
    @Field(store = true, name = "location")
    private GeoPoint location;

    private String highlight;

    public List<String> getFieldNames() {
        return List.of("name", "surname", "governmentName", "governmentType",  "contractContent", "lawContent");
    }



}
