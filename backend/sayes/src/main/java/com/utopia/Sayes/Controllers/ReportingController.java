package com.utopia.Sayes.Controllers;

import com.utopia.Sayes.Facades.ReportingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportingController {
    @Autowired
    ReportingFacade reportingFacade;
    @GetMapping("/get-logs")
    public ResponseEntity<?> getLogs(@RequestHeader("Authorization") String token) {
        try {
            token = token.replace("Bearer ", "");
            Map<String , Object> response = reportingFacade.getLogs(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
