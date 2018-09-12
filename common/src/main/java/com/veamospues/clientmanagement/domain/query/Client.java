package com.veamospues.clientmanagement.domain.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
  UUID id;
  String name;
  Date dateCreated;
  Date lastUpdated;
  String createdBy;
  String updatedBy;
}
