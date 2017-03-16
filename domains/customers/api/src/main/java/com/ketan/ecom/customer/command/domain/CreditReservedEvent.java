package com.ketan.ecom.customer.command.domain;

/**
 * Created by nikeshshetty on 3/15/17.
 */
public class CreditReservedEvent {

    private final String customerId;
    private final String orderId;
    private final int price;

    public CreditReservedEvent(String customerId, String orderId, int price) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.price = price;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getPrice() {
        return price;
    }
}