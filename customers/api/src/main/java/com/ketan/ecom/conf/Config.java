package com.ketan.ecom.conf;

//import com.ketan.ecom.command.domain.Customer;
//import org.axonframework.commandhandling.CommandBus;
//import org.axonframework.commandhandling.SimpleCommandBus;
//import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
//import org.axonframework.commandhandling.gateway.CommandGateway;
//import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
//import org.axonframework.eventhandling.EventBus;
//import org.axonframework.eventhandling.SimpleEventBus;
//import org.axonframework.eventsourcing.EventSourcingRepository;
//import org.axonframework.eventstore.EventStore;
//import org.axonframework.eventstore.fs.FileSystemEventStore;
//import org.axonframework.eventstore.fs.SimpleEventFileResolver;
//import org.springframework.context.annotation.Configuration;

import com.ketan.ecom.command.domain.Customer;
import com.ketan.ecom.query.listener.NewCustomerEventListener;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.io.File;
import java.util.Properties;

/**
 * Created by ketansoni on 07/10/2016.
 */

@Configuration
public class Config {

    @Bean
    public CommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus) {
        return new DefaultCommandGateway(commandBus);
    }

    @Bean
    public EventStore eventStore() {
        return new FileSystemEventStore(new SimpleEventFileResolver(new File("./events")));
    }

    @Bean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

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
