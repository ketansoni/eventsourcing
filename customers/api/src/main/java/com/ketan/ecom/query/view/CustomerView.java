package com.ketan.ecom.query.view;


import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ketansoni on 12/10/2016.
 */

@Entity
public class CustomerView implements Persistable<String>{

    @Id
    private String customerId;

    public CustomerView() {
    }

    public CustomerView(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
