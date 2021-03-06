package com.ketan.ecom.customer.web;

import com.ketan.ecom.customer.command.NewCustomerCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomerController {

    @Autowired
    public CommandGateway commandGateway;

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.POST)
    @ResponseBody
    public String newCustomer(@PathVariable("customerId") String customerId) {
        commandGateway.sendAndWait(new NewCustomerCommand(customerId));
        return "OK";
    }

    @RequestMapping("/customer")
    @ResponseBody
    String test() {
        return "its working";
    }
}