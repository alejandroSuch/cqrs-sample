package com.veamospues.clientmanagement.persistence.event.document;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = ClientEventDocument.COLLECTION_NAME)
public class ClientDeletedEventDocument extends ClientEventDocument {
}
