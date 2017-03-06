package com.ketan.ecom.command.domain;

import com.ketan.ecom.customer.command.NewCustomerCommand;
import com.ketan.ecom.customer.command.domain.Customer;
import com.ketan.ecom.customer.command.event.NewCustomerEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ketansoni on 07/10/2016.
 */
public class CustomerTest {

    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(Customer.class);
    }

    @Test
    public void testCreateToDoItem() throws Exception {
        fixture.given()
                .when(new NewCustomerCommand("customerId"))
                .expectEvents(new NewCustomerEvent("customerId"));
    }
}
