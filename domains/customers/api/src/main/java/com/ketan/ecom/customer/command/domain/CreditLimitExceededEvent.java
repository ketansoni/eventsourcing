package com.ketan.ecom.customer.command.domain;

/**
 * Created by nikeshshetty on 3/15/17.
 */
public class CreditLimitExceededEvent {
    private final String customerId;
    private final String orderId;

    public CreditLimitExceededEvent(String customerId, String orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }
}
