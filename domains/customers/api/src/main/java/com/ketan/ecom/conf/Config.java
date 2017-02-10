package com.ketan.ecom.conf;

import com.ketan.ecom.command.domain.Customer;
import com.ketan.ecom.query.listener.NewCustomerEventListener;
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
    public EventSourcingRepository<Customer> repository(EventStore eventStore, EventBus eventBus, CommandBus commandBus, NewCustomerEventListener eventListener) {

        System.out.println("******************************"+eventListener);
        EventSourcingRepository repository = new EventSourcingRepository(Customer.class, eventStore);
        repository.setEventBus(eventBus);
        AggregateAnnotationCommandHandler.subscribe(Customer.class, repository, commandBus);

        AnnotationEventListenerAdapter.subscribe(eventListener, eventBus);
        return repository;
    }
}
