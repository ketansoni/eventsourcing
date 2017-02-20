package db;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;


/**
 * Created by ketansoni on 10/10/2016.
 */
@Configuration
@EnableAutoConfiguration
public class LiquibaseConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource()  {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .type(org.h2.jdbcx.JdbcDataSource.class)
                .url("jdbc:h2:/Users/ketansoni/Documents/code/Personal/eventsourcing/domains/orders/db/target/orders-db;AUTO_SERVER=TRUE")
//                .url("jdbc:h2:mem:liquibase")
                .username("sa")
                .password("")
                .build();
    }

    @Bean
    public SpringLiquibase liquibase()  {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:readstore.xml");

        return liquibase;
    }

    @Bean
    public SpringLiquibase writeLiquibase(DataSource dataSource)  {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:eventstore.xml");

        return liquibase;
    }
}