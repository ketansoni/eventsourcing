package com.ketan.ecom.customer.command.event;

/**
 * Created by ketansoni on 07/10/2016.
 */
public class NewCustomerEvent {

    private String customerId;

    public NewCustomerEvent(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
