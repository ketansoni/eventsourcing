package com.ketan.ecom.query.repository;

import com.ketan.ecom.query.view.CustomerView;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ketansoni on 10/10/2016.
 */

@Repository
public interface CustomerViewRepository extends CrudRepository<CustomerView, String> {
}
