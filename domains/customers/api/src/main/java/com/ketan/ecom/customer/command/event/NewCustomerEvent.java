package com.ketan.ecom.customer.command.event;

public class NewCustomerEvent {

    private String customerId;

    public NewCustomerEvent(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
