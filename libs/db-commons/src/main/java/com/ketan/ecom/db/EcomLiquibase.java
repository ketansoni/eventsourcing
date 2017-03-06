package com.ketan.ecom.db;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.changelog.ChangeSet;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;


import java.util.List;

public class EcomLiquibase extends SpringLiquibase {


    public boolean validateOnly;

    public boolean isValidateOnly() {
        return validateOnly;
    }

    public void setValidateOnly(boolean validateOnly) {
        this.validateOnly = validateOnly;
    }

    @Override
    protected void performUpdate(Liquibase liquibase) throws LiquibaseException {
        if (isValidateOnly()) {
            final List<ChangeSet> changeSets = liquibase.listUnrunChangeSets(new Contexts(getContexts()), new LabelExpression(getLabels()));
            if (!changeSets.isEmpty()) {
                throw new IllegalStateException("Target database '" + getBeanName() + "' is not up tp date. Following changesets not applied\n" + changeSets);
            } else {
                System.out.println("Target database '" + getBeanName() + " is up-to-date");
            }
        } else {
            super.performUpdate(liquibase);
        }

    }
}

