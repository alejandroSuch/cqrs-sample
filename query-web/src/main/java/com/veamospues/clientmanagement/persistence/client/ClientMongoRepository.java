package com.veamospues.clientmanagement.persistence.client;

import com.veamospues.clientmanagement.domain.query.Client;
import com.veamospues.clientmanagement.domain.query.ClientQueryCriteria;
import com.veamospues.clientmanagement.domain.query.ClientQueryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static com.veamospues.clientmanagement.persistence.client.ClientDocument.NAME_FIELD;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@AllArgsConstructor
public class ClientMongoRepository implements ClientQueryRepository {
  private SpringDataClientMongoRepository clientRepository;
  private MongoOperations operations;

  ModelMapper modelMapper;

  @Override
  public Optional<Client> findById(UUID id) {
    final Optional<ClientDocument> byId = clientRepository.findById(id);

    if (!byId.isPresent()) {
      return empty();
    }

    return byId.map(toClient());
  }

  @Override
  public List<Client> findAll(ClientQueryCriteria criteria) {
    return operations
      .find(queryFrom(criteria), ClientDocument.class)
      .stream()
      .map(toClient())
      .collect(toList());
  }

  private Function<ClientDocument, Client> toClient() {
    return clientDocument -> modelMapper.map(clientDocument, Client.class);
  }

  private Query queryFrom(ClientQueryCriteria criteria) {
    final Query query = new Query();

    if (criteria.getName() != null) {
      query.addCriteria(where(NAME_FIELD).is(criteria.getName()));
    }

    return query;
  }
}
