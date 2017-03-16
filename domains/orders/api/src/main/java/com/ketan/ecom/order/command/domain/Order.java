package com.ketan.ecom.order.command.domain;

import com.ketan.ecom.order.ORDERSTATUS;
import com.ketan.ecom.order.command.PlaceAnOrderCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * Created by nikeshshetty on 3/15/17.
 */
public class Order extends AbstractAnnotatedAggregateRoot<String> {
    private String id;
    private String name;
    private int price;
    private ORDERSTATUS orderStatus;

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        this.id = orderCreatedEvent.getId();
        this.name = orderCreatedEvent.getOrderName();
        this.price = orderCreatedEvent.getPrice();
        this.orderStatus = ORDERSTATUS.PENDING;
    }


    @CommandHandler
    public void handle(PlaceAnOrderCommand command) {
        apply(new OrderCreatedEvent(command.getId(), command.getOrderName(), command.getPrice(), command.getCustomerId()));
    }

}
