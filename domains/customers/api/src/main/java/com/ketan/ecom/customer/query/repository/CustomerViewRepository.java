package com.ketan.ecom.customer.query.repository;

import com.ketan.ecom.customer.query.view.CustomerView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CustomerViewRepository extends JpaRepository<CustomerView,String>, JpaSpecificationExecutor<CustomerView>{
}
