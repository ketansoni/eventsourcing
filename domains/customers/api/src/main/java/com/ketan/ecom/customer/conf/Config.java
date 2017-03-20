package com.ketan.ecom.customer.conf;

import com.ketan.ecom.customer.command.domain.Customer;
import com.ketan.ecom.customer.command.listener.OrderCreatedListener;
import com.ketan.ecom.customer.query.listener.NewCustomerEventListener;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
public class Config {

    @Bean
    public EventSourcingRepository<Customer> customerEventSourcingRepository(EventStore eventStore, EventBus eventBus, CommandBus commandBus, NewCustomerEventListener eventListener, OrderCreatedListener orderCreatedListener) {

        EventSourcingRepository repository = new EventSourcingRepository(Customer.class, eventStore);
        repository.setEventBus(eventBus);
        AggregateAnnotationCommandHandler.subscribe(Customer.class, repository, commandBus);

        AnnotationEventListenerAdapter.subscribe(eventListener, eventBus);
        AnnotationEventListenerAdapter.subscribe(orderCreatedListener, eventBus);
        return repository;
    }

}
