package com.example.ddmdemo.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "index_unit_info")
public class IndexUnitInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "minio_contract_filename")
    private String serverContractFilename;

    @Column(name = "minio_law_filename")
    private String serverLawFilename;

    @Column(name = "contract_title")
    private String contractTitle;

    @Column(name = "law_title")
    private String lawTitle;

    private String name;

    private String surname;

    @Column(name = "government_name")
    private String governmentName;

    @Column(name = "government_type")
    private String governmentType;

    private String location;



}
