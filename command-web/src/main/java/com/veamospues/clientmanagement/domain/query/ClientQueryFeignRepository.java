package com.veamospues.clientmanagement.domain.query;

import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class ClientQueryFeignRepository implements ClientQueryRepository {
  private ClientQueryFeignClient clientQueryFeignClient;

  @Override
  public Optional<Client> findById(UUID id) {
    try {
      return Optional.of(clientQueryFeignClient.findById(id));
    } catch (FeignException e) {
      if (e.status() == 404) {
        return Optional.empty();
      }

      throw e;
    }
  }

  @Override
  public List<Client> findAll(ClientQueryCriteria criteria) {
    return clientQueryFeignClient.findAll(criteria.getName());
  }
}
