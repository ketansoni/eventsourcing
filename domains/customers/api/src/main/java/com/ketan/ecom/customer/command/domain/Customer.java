package com.ketan.ecom.customer.command.domain;


import com.ketan.ecom.customer.command.CheckCreditForOrderCommand;
import com.ketan.ecom.customer.command.NewCustomerCommand;
import com.ketan.ecom.customer.command.event.NewCustomerEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class Customer extends AbstractAnnotatedAggregateRoot<String> {

    @AggregateIdentifier
    private String id;
    private int amount;

    public Customer() {
    }

    @CommandHandler
    public Customer(NewCustomerCommand command) {
        apply(new NewCustomerEvent(command.getCustomerId()));
    }

    @CommandHandler
    public void handle(CheckCreditForOrderCommand command) {
        if (this.amount < command.getPrice())
            apply(new CreditLimitExceededEvent(id, command.getOrderId()));
        else {
            apply(new CreditReservedEvent(id, command.getOrderId(), command.getPrice()));
        }

    }

    @EventSourcingHandler
    public void on(NewCustomerEvent event) {
        this.id = event.getCustomerId();
    }

    @EventSourcingHandler
    public void on(CreditReservedEvent event) {
        this.amount -= event.getPrice();
    }
}
