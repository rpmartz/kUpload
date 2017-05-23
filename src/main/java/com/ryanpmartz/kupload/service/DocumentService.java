package com.ryanpmartz.kupload.service;

import com.ryanpmartz.kupload.domain.Document;
import com.ryanpmartz.kupload.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void save(Document doc) {
        documentRepository.save(doc);
    }
}
