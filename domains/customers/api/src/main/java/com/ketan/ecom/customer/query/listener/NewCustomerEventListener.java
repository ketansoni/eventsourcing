package com.ketan.ecom.customer.query.listener;

import com.ketan.ecom.customer.command.event.NewCustomerEvent;
import com.ketan.ecom.customer.query.repository.CustomerViewRepository;
import com.ketan.ecom.customer.query.view.CustomerView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewCustomerEventListener {

    private CustomerViewRepository customerViewRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public  NewCustomerEventListener(CustomerViewRepository customerViewRepository){
        this.customerViewRepository = customerViewRepository;
    }

    @EventHandler
    public void on(NewCustomerEvent event) {

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&"+event);
        customerViewRepository.save(new CustomerView(event.getCustomerId()));
    }
}
