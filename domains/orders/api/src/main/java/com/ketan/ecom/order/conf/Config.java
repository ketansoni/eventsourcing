package com.ketan.ecom.order.conf;

import com.ketan.ecom.order.command.domain.Order;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public EventSourcingRepository<Order> customerEventSourcingRepository(EventStore eventStore, EventBus eventBus, CommandBus commandBus) {

        EventSourcingRepository repository = new EventSourcingRepository(Order.class, eventStore);
        repository.setEventBus(eventBus);
        AggregateAnnotationCommandHandler.subscribe(Order.class, repository, commandBus);

        //AnnotationEventListenerAdapter.subscribe( eventBus);
        return repository;
    }

}
