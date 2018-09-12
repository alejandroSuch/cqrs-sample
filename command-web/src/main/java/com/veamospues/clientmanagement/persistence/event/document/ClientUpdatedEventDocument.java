package com.veamospues.clientmanagement.persistence.event.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = ClientEventDocument.COLLECTION_NAME)
public class ClientUpdatedEventDocument extends ClientEventDocument {
  String name;
}
