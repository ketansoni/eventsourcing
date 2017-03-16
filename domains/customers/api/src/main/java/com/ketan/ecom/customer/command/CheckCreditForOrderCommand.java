package com.ketan.ecom.customer.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nikeshshetty on 3/15/17.
 */
public class CheckCreditForOrderCommand {
    private final String orderId;
    private final String customerId;
    private final String orderName;
    private final int price;

    public CheckCreditForOrderCommand(String orderId, String customerId, String orderName, int price) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderName = orderName;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getOrderName() {
        return orderName;
    }

    public int getPrice() {
        return price;
    }
}
