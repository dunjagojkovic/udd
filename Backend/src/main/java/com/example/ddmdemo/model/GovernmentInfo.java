package com.example.ddmdemo.model;

import com.example.ddmdemo.model.enumeration.GovernmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "government_info")
public class GovernmentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "government_name")
    private String name;

    @Column(name = "government_type")
    private GovernmentType type;

    private String city;

    private String address;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "contract_title")
    private String contractTitle;

}
