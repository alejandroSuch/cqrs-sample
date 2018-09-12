package com.veamospues.clientmanagement.persistence.event.repository;

import com.veamospues.clientmanagement.persistence.event.document.ClientEventDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ClientEventMongoRepository extends MongoRepository<ClientEventDocument, UUID> {
  List<ClientEventDocument> findAllByAggregateIdOrderByVersionAsc(UUID aggregateId);
}
