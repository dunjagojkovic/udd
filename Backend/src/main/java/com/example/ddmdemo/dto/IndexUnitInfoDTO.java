package com.example.ddmdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class IndexUnitInfoDTO {

    private String title;
    private String name;
    private String surname;
    private String governmentName;
    private String governmentType;
    private MultipartFile contract;
    private MultipartFile law;

}
