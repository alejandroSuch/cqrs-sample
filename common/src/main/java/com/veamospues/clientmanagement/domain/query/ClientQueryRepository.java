package com.veamospues.clientmanagement.domain.query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientQueryRepository {
  Optional<Client> findById(UUID id);

  List<Client> findAll(ClientQueryCriteria criteria);


}
