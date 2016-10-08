package com.ketan.ecom.command.domain;


import com.ketan.ecom.command.NewCustomerCommand;
import com.ketan.ecom.command.event.NewCustomerEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

/**
 * Created by ketansoni on 07/10/2016.
 */
public class Customer extends AbstractAnnotatedAggregateRoot {

    @AggregateIdentifier
    private String id;

    public Customer() {
    }

    @CommandHandler
    public Customer(NewCustomerCommand command) {
        apply(new NewCustomerEvent(command.getCustomerId()));
    }

    @EventHandler
    public void on(NewCustomerEvent event) {
        this.id = event.getCustomerId();
    }
}
