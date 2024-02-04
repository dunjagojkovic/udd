package com.example.ddmdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ContractParsedDataDTO {
    private String employeeFullName;
    private String governmentName;
    private String governmentType;
    private String governmentAddress;
}
