package com.ketan.ecom.customer.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * Created by ketansoni on 07/10/2016.
 */
public class NewCustomerCommand {

    @TargetAggregateIdentifier
    private final String customerId;

    public NewCustomerCommand(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
