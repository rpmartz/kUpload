package com.ryanpmartz.kupload.repository;

import com.ryanpmartz.kupload.domain.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

}
