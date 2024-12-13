package com.genpact.onlineShoppingApp.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genpact.onlineShoppingApp.entity.ContactMessage;
import com.genpact.onlineShoppingApp.service.ContactMessageService;

import org.springframework.web.bind.annotation.CrossOrigin;
@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:5173")
public class ContactMessageController {

    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendContactMessage(@RequestBody ContactMessage contactMessage) {
        try {
            return ResponseEntity.ok(contactMessageService.sendContactMessage(contactMessage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Failed to send contact message");
        }
    }
}
