package com.veamospues.clientmanagement.persistence.event.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document(collection = ClientEventDocument.COLLECTION_NAME)
public abstract class ClientEventDocument {
  public final static String COLLECTION_NAME = "clientEvents";

  @Id
  private UUID id;

  @Indexed
  private UUID aggregateId;

  private Long version;
  private Date date;
  private String user;
}
