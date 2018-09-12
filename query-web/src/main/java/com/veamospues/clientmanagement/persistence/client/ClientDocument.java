package com.veamospues.clientmanagement.persistence.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class ClientDocument {
  public static final String ID_FIELD = "id";
  public static final String NAME_FIELD = "name";
  public static final String DATE_CREATED_FIELD = "dateCreated";
  public static final String CREATED_BY_FIELD = "createdBy";
  public static final String LAST_UPDATED_FIELD = "lastUpdated";
  public static final String LAST_UPDATED_BY_FIELD = "lastUpdatedBy";

  @Id
  UUID id;

  @Indexed
  String name;

  Date dateCreated;
  Date lastUpdated;
  String createdBy;
  String updatedBy;
}
