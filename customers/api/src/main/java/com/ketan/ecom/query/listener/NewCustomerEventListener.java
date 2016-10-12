package com.ketan.ecom.query.listener;

import com.ketan.ecom.command.event.NewCustomerEvent;
import com.ketan.ecom.query.repository.CustomerViewRepository;
import com.ketan.ecom.query.view.CustomerView;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ketansoni on 10/10/2016.
 */

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
