package com.genpact.onlineShoppingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genpact.onlineShoppingApp.entity.ContactMessage;
import com.genpact.onlineShoppingApp.repository.ContactMessageRepository;

@Service
public class ContactMessageService {
    
    @Autowired
    private ContactMessageRepository contactMessageRepository;

    public ContactMessage sendContactMessage(ContactMessage contactMessage) {
        try {
            return contactMessageRepository.save(contactMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send contact message", e);
        }
    }
}
