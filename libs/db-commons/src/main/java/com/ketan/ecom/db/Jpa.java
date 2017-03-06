package com.ketan.ecom.db;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;


@Configuration
@ConditionalOnWebApplication
public class Jpa implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Autowired
    private JpaProperties jpaProperties;

    @Bean(name = {"readTransactionManager", "transactionManager"})
    @Primary
    public PlatformTransactionManager readTransactionManager(@Qualifier("readEntityManagerFactory") EntityManagerFactory factory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate(@Qualifier("readTransactionManager") PlatformTransactionManager jpaTransactionManager) {
        return new TransactionTemplate(jpaTransactionManager);
    }

    @Bean
    @ConditionalOnProperty(prefix="database.write", name = "url")
    public PlatformTransactionManager writeTransactionManager(@Qualifier("writeEntityManagerFactory") EntityManagerFactory factory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }

    @Bean(name = {"readEntityManagerFactory", "entityManagerFactory"})
    @DependsOn("readDatabaseUpdater")
    @Primary
    public LocalContainerEntityManagerFactoryBean readEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("readDataSource") DataSource dataSource) {
        return entityManagerFromProperties("read", builder, dataSource, packagesToScan());
    }

    @Bean
    @ConditionalOnProperty(prefix="database.write", name = "url")
    @DependsOn("writeDatabaseUpdater")
    public LocalContainerEntityManagerFactoryBean writeEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                            @Qualifier("writeDataSource") DataSource dataSource) {
        return entityManagerFromProperties("write", builder, dataSource, "com.ketan.ecom.axon.jpa");
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    private LocalContainerEntityManagerFactoryBean entityManagerFromProperties(String name, EntityManagerFactoryBuilder builder, DataSource dataSource, String... packagesToScan) {
        return builder
                .dataSource(dataSource)
                .packages(packagesToScan)
                .persistenceUnit(name)
                .properties(jpaProperties.getHibernateProperties(dataSource))
                .build();
    }

    private String[] packagesToScan() {
        if (AutoConfigurationPackages.has(this.beanFactory)) {
            List<String> basePackages = AutoConfigurationPackages.get(this.beanFactory);
            return basePackages.toArray(new String[basePackages.size()]);
        }
        return new String[0];
    }

}
