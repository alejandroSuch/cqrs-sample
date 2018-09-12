package com.veamospues.clientmanagement.domain.aggregate;

import com.veamospues.clientmanagement.domain.event.ClientCreatedEvent;
import com.veamospues.clientmanagement.domain.event.ClientDeletedEvent;
import org.junit.Test;

import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static java.util.stream.StreamSupport.stream;
import static org.junit.Assert.*;

public class ClientAggregateTest {

  private static final String CLIENT_NAME = "a client";
  private static final UUID CLIENT_ID = randomUUID();

  @Test
  public void new_client_aggregate_has_an_uncommited_change_and_data_is_properly_set() {
    final ClientAggregate clientAggregate = new ClientAggregate(CLIENT_ID, CLIENT_NAME);

    assertTrue(clientAggregate.hasUncommitedChanges());
    stream(clientAggregate.getUncommitedChanges().spliterator(), false).count();
    assertEquals(CLIENT_NAME, clientAggregate.getName());
    assertEquals(CLIENT_ID, clientAggregate.getId());
    assertFalse(clientAggregate.getDeleted());
  }

  @Test
  public void new_client_aggregate_has_an_uncommited_change_and_id_and_name_are_properly_set() {
    final ClientAggregate clientAggregate = new ClientAggregate(CLIENT_ID, CLIENT_NAME);

    assertTrue(clientAggregate.hasUncommitedChanges());
    assertEquals(CLIENT_NAME, clientAggregate.getName());
    assertEquals(CLIENT_ID, clientAggregate.getId());
    assertFalse(clientAggregate.getDeleted());
  }

  @Test
  public void when_setting_same_name_there_are_no_changes() {
    final ClientAggregate clientAggregate = new ClientAggregate(CLIENT_ID, CLIENT_NAME);
    clientAggregate.markChangesAsCommited();

    clientAggregate.changeName(CLIENT_NAME);

    assertFalse(clientAggregate.hasUncommitedChanges());
  }

  @Test
  public void existing_client_aggregate_has_one_change_after_deletion() {
    ClientAggregate clientAggregate = new ClientAggregate();
    clientAggregate.loadFromHistory(singletonList(new ClientCreatedEvent(CLIENT_ID, CLIENT_NAME)));

    clientAggregate.delete();

    assertTrue(clientAggregate.getDeleted());
    assertTrue(clientAggregate.hasUncommitedChanges());
    assertTrue(stream(clientAggregate.getUncommitedChanges().spliterator(), FALSE).findFirst().get() instanceof ClientDeletedEvent);
  }

}