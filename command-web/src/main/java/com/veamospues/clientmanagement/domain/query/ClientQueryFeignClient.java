package com.veamospues.clientmanagement.domain.query;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(url = "${client-management-query-web-url}/clients", name = "ClientQueryFeignClient")
public interface ClientQueryFeignClient {

  @RequestMapping(method = GET, value = "/{id}")
  Client findById(@PathVariable("id") UUID id);

  @RequestMapping(method = GET, value = "/")
  List<Client> findAll(@RequestParam("name") String name);
}
