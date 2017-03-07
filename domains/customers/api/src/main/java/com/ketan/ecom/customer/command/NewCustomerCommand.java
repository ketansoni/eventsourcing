package com.ketan.ecom.customer.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

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
