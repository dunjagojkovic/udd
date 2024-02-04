package com.example.ddmdemo.controller;

import com.example.ddmdemo.dto.GovernmentRegistrationDTO;
import com.example.ddmdemo.service.interfaces.GovernmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/government")
@RequiredArgsConstructor
public class GovernmentInfoController {

    @Autowired
    GovernmentService governmentService;


    @PostMapping
    public ResponseEntity<?> createNewGovernment(@RequestBody GovernmentRegistrationDTO dto) throws Exception {
            if(governmentService.registerGovernment(dto) == null){
                return new ResponseEntity<>("Unsuccessful registration!", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Successful registration of new Government", HttpStatus.CREATED);
    }

}


