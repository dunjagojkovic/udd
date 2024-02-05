package com.example.ddmdemo.indexmodel;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "contract_index")
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class IndexUnit {

    @Id
    private String id;

    @Field(type = FieldType.Text, store = true, name = "name")
    private String name;

    @Field(type = FieldType.Text, store = true, name = "surname")
    private String surname;

    @Field(type = FieldType.Text, store = true, name = "governmentName")
    private String governmentName;

    @Field(type = FieldType.Text, store = true, name = "governmentType")
    private String governmentType;

    @Field(type = FieldType.Text, store = true, name = "contractContent", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    @HighlightField(name = "contractContent")
    private String contractContent;

    @Field(type = FieldType.Text, store = true, name = "lawContent", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    @HighlightField
    private String lawContent;

    @GeoPointField
    @Field(store = true, name = "location")
    private GeoPoint location;



}
