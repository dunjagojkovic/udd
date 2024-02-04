package com.example.ddmdemo.dto;

import com.example.ddmdemo.model.enumeration.GovernmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GovernmentRegistrationDTO {

    private String governmentName;
    private String type;
    private String city;
    private String address;
    private String username;
    private String password;
}
