package com.ketan.ecom.customer.query.repository;

import com.ketan.ecom.customer.query.view.CustomerView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ketansoni on 10/10/2016.
 */


//public interface CustomerViewRepository extends CrudRepository<CustomerView, String> {
//}
//@Repository
public interface CustomerViewRepository extends JpaRepository<CustomerView,String>, JpaSpecificationExecutor<CustomerView>{
}
