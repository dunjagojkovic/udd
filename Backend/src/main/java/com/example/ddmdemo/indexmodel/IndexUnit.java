package com.example.ddmdemo.indexmodel;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.lucene.spatial3d.geom.GeoPoint;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "contract_index")
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class IndexUnit {

    @Id
    private String id;

    @Field(type = FieldType.Text, store = true, name = "title")
    private String title;

    @Field(type = FieldType.Text, store = true, name = "name")
    private String name;

    @Field(type = FieldType.Text, store = true, name = "surname")
    private String surname;

    @Field(type = FieldType.Text, store = true, name = "governmentName")
    private String governmentName;

    @Field(type = FieldType.Text, store = true, name = "governmentType")
    private String governmentType;

    @Field(type = FieldType.Text, store = true, name = "contractContent", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String contractContent;

    @Field(type = FieldType.Text, store = true, name = "lawContent", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String lawContent;

    @Field(type = FieldType.Text, store = true, name = "server_filename", index = false)
    private String serverFilename;

    @GeoPointField
    @Field(store = true, name = "location")
    private GeoPoint location;

    @Field(type = FieldType.Integer, store = true, name = "database_id")
    private Integer databaseId;


}
