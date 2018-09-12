package com.veamospues.clientmanagement.rest;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
class UpdateClientRequest {
  @NotBlank
  String name;
}
