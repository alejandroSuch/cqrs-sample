package com.veamospues.clientmanagement.persistence.client;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface SpringDataClientMongoRepository extends MongoRepository<ClientDocument, UUID> {

}
