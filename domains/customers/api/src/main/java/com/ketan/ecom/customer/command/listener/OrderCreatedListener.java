package com.ketan.ecom.customer.command.listener;

import com.ketan.ecom.customer.command.CheckCreditForOrderCommand;
import com.ketan.ecom.customer.external.events.OrderCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nikeshshetty on 3/15/17.
 */
@Component
public class OrderCreatedListener {
    private CommandGateway commandGateway;

    @Autowired
    public OrderCreatedListener(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @EventHandler
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        commandGateway.send(new CheckCreditForOrderCommand(orderCreatedEvent.getId(), orderCreatedEvent.getCustomerId(), orderCreatedEvent.getOrderName()
                , orderCreatedEvent.getPrice()));

    }


}
