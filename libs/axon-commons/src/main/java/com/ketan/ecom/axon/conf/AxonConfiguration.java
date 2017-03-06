package com.ketan.ecom.axon.conf;


import com.ketan.ecom.axon.jpa.DefaultEventEntryFactory;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.*;
import org.axonframework.eventhandling.amqp.spring.ListenerContainerLifecycleManager;
import org.axonframework.eventhandling.amqp.spring.SpringAMQPTerminal;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventstore.SnapshotEventStore;
import org.axonframework.eventstore.jpa.DefaultEventEntryStore;
import org.axonframework.eventstore.jpa.JpaEventStore;
import org.axonframework.eventstore.jpa.SQLErrorCodesResolver;
import org.axonframework.serializer.xml.XStreamSerializer;
import org.axonframework.unitofwork.SpringTransactionManager;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:/axonconfiguration.properties")
public class AxonConfiguration {

    private
//    @Value("${rabbitmq.host}")
    @Value("localhost")
    String rabbitHost;

    private
//    @Value("${rabbitmq.port}")
    @Value("5672")
    Integer rabbitPort;

    private
//    @Value("${rabbitmq.username}")
    @Value("guest")
    String rabbitUsername;

    private
//    @Value("${rabbitmq.password}")
    @Value("guest")
    String rabbitPassword;

    private
//    @Value("${rabbitmq.exchange.name}")
    @Value("ecommerce.exchange")
    String rabbitExchangeName;

    private
//    @Value("${rabbitmq.exchange.autodelete}")
    boolean rabbitExchangeAutodelete;

    private
//    @Value("${rabbitmq.exchange.durable}")
    @Value("true")
    boolean rabbitExchangeDurable;

    private
//    @Value("${rabbitmq.queue.name}")
    @Value("ecommerce.customer.queue")
    String rabbitQueueName;

    private
//    @Value("${rabbitmq.queue.durable}")
    @Value("false")
    Boolean rabbitQueueDurable;

    private
//    @Value("${rabbitmq.queue.exclusive}")
    @Value("false")
    Boolean rabbitQueueExclusive;

    private
//    @Value("${rabbitmq.queue.autodelete}")
    @Value("false")
    Boolean rabbitQueueAutoDelete;

    @Bean
    public XStreamSerializer xstreamSerializer() {
        return new XStreamSerializer();
    }

    @Bean
    @Autowired
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost", 5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public FanoutExchange eventBusExchange() {
        return new FanoutExchange("ecommerce.exchange", true, false);
    }

    @Bean
    public Queue eventBusQueue() {
        return new Queue("ecommerce.customer.queue", true, false, false);
    }

    @Bean
    public Binding binding(Queue eventBusQueue, FanoutExchange eventBusExchange) {
        return BindingBuilder.bind(eventBusQueue).to(eventBusExchange);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public CommandBus commandBus(@Qualifier("writeTransactionManager") PlatformTransactionManager platformTransactionManager) {

        SimpleCommandBus simpleCommandBus = new SimpleCommandBus();
        simpleCommandBus.setTransactionManager(new SpringTransactionManager(platformTransactionManager));
        return simpleCommandBus;
    }


    @Bean
    public EventBus eventBus(SimpleCluster simpleCluster, EventBusTerminal terminal) {
        ClusteringEventBus clusteringEventBus = new ClusteringEventBus(new DefaultClusterSelector(simpleCluster), terminal);
        return clusteringEventBus;
    }

    @Bean
    public EventBusTerminal terminal(ConnectionFactory connectionFactory, XStreamSerializer xstreamSerializer, ListenerContainerLifecycleManager listenerContainerLifecycleManager) {
        SpringAMQPTerminal terminal = new SpringAMQPTerminal();
        terminal.setConnectionFactory(connectionFactory);
        terminal.setSerializer(xstreamSerializer);
        terminal.setExchangeName("ecommerce.exchange");
        terminal.setListenerContainerLifecycleManager(listenerContainerLifecycleManager);
        terminal.setDurable(true);
        terminal.setTransactional(false);
        return terminal;
    }

    @Bean
    public SimpleCluster simpleCluster() {
        SimpleCluster simpleCluster = new SimpleCluster("ecommerce.customer.queue");
        return simpleCluster;
    }

    @Bean
    public ListenerContainerLifecycleManager listenerContainerLifecycleManager(ConnectionFactory connectionFactory) {
        ListenerContainerLifecycleManager listenerContainerLifecycleManager = new ListenerContainerLifecycleManager();
        listenerContainerLifecycleManager.setConnectionFactory(connectionFactory);
        return listenerContainerLifecycleManager;
    }

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus) {
        return new DefaultCommandGateway(commandBus);
    }

//    @Bean
//    public EventStore eventStore() {
//        return new FileSystemEventStore(new SimpleEventFileResolver(new File("./events")));
//    }

    @Bean
    public EntityManagerProvider writeEntityManager() {
        return new EntityManagerProvider() {
            private EntityManager entityManager;

            @Override
            public EntityManager getEntityManager() {
                return entityManager;
            }

            @PersistenceContext(unitName = "write")
            public void setEntityManager(EntityManager entityManager) {
                this.entityManager = entityManager;
            }
        };
    }

    @Bean
    public PersistenceAnnotationBeanPostProcessor jpaPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

    @Bean
    public JpaEventStore eventStore(XStreamSerializer xStreamSerializer,
//                                    UpcasterChain upcasterChain,
                                    @Qualifier("writeEntityManager") EntityManagerProvider entityManagerProvider,
                                    @Qualifier("writeDataSource") DataSource dataSource) throws SQLException {

        DefaultEventEntryStore<String> eventEntryStore = new DefaultEventEntryStore<>(new DefaultEventEntryFactory());
        JpaEventStore result = new JpaEventStore(entityManagerProvider, xStreamSerializer, eventEntryStore);
//        result.setUpcasterChain(upcasterChain);
        result.setPersistenceExceptionResolver(new SQLErrorCodesResolver(dataSource));
        return result;
    }

    @Bean
    public Snapshotter snapshotter(SnapshotEventStore eventStore,
                                   @Qualifier("writeTransactionManager") PlatformTransactionManager transactionManager) {
        AggregateSnapshotter result = new SpringAggregateSnapshotter();
        result.setEventStore(eventStore);
        result.setTxManager(new SpringTransactionManager(transactionManager));
        return result;
    }

    @Bean
    public SnapshotterTrigger snapshotterTrigger(Snapshotter snapshotter) {
        EventCountSnapshotterTrigger result = new EventCountSnapshotterTrigger();
        result.setSnapshotter(snapshotter);
        result.setTrigger(5);
        return result;
    }
}
