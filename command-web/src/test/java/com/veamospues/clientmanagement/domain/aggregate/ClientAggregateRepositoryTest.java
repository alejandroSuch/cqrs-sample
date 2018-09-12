package com.veamospues.clientmanagement.domain.aggregate;

import com.veamospues.cqrs.event.EventBus;
import com.veamospues.cqrs.event.EventStore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientAggregateRepositoryTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private EventStore eventStore;

  @Mock
  private EventBus eventBus;

  private ClientAggregateRepository aggregateRepository;

  @Test
  public void fails_on_null_event_store() {
    thrown.expect(NullPointerException.class);

    new ClientAggregateRepository(null, eventBus);
  }

  @Test
  public void fails_on_null_event_bus() {
    thrown.expect(NullPointerException.class);

    new ClientAggregateRepository(eventStore, null);
  }

  @Test
  public void ok_on_proper_event_bus_and_event_store() {
    new ClientAggregateRepository(eventStore, eventBus);
  }

}