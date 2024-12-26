package com.utopia.Sayes.Controllers;

import com.utopia.Sayes.Facades.ReportingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin()
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
    @GetMapping("/get-manager-lot-report")
    public ResponseEntity<?> getLotsReportForManager(@RequestHeader("Authorization") String token) {
        try {
            token = token.replace("Bearer ", "");
            Map<String , Object> response = reportingFacade.getLotsReportForManager(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-top-users")
    public ResponseEntity<?> getTopUsers(@RequestHeader("Authorization") String token) {
        try {
            token = token.replace("Bearer ", "");
            Map<String , Object> response = reportingFacade.getTopUsers(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-top-lots")
    public ResponseEntity<?> getTopLots(@RequestHeader("Authorization") String token) {
        try {
            token = token.replace("Bearer ", "");
            Map<String , Object> response = reportingFacade.getTopLots(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
