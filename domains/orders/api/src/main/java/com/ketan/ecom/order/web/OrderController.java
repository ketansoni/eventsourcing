package com.ketan.ecom.order.web;

import com.ketan.ecom.order.command.PlaceAnOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by nikeshshetty on 3/15/17.
 */
@RestController
public class OrderController {
    private CommandGateway commandGateway;

    @Autowired
    public OrderController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }


    @RequestMapping(value = "/place/{customerId}", method = RequestMethod.POST)
    @ResponseBody
    public String placeAnOrder(@PathVariable("customerId") String customerId) {
        String orderName = "someOrder";
        int price = 45;
        commandGateway.send(new PlaceAnOrderCommand(UUID.randomUUID().toString(), orderName, price, customerId));
        return "OK";
    }

}
