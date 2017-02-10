package com.ketan.ecom.axon.commons.conf;


import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.*;
import org.axonframework.eventhandling.amqp.spring.ListenerContainerLifecycleManager;
import org.axonframework.eventhandling.amqp.spring.SpringAMQPTerminal;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.serializer.xml.XStreamSerializer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class AxonConfiguration {

    private @Value("${rabbitmq.host}") String rabbitHost;
    private @Value("${rabbitmq.port}") Integer rabbitPort;
    private @Value("${rabbitmq.username}") String rabbitUsername;
    private @Value("${rabbitmq.password}") String rabbitPassword;
    private @Value("${rabbitmq.exchange.name}") String rabbitExchangeName;
    private @Value("${rabbitmq.exchange.autodelete}") boolean rabbitExchangeAutodelete;
    private @Value("${rabbitmq.exchange.durable}") boolean rabbitExchangeDurable;
    private @Value("${rabbitmq.queue.name}") String rabbitQueueName;
    private @Value("${rabbitmq.queue.durable}") Boolean rabbitQueueDurable;
    private @Value("${rabbitmq.queue.exclusive}") Boolean rabbitQueueExclusive;
    private @Value("${rabbitmq.queue.autodelete}") Boolean rabbitQueueAutoDelete;

    @Bean
    public XStreamSerializer xstreamSerializer() {
        return new XStreamSerializer();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitHost, rabbitPort);
        connectionFactory.setUsername(rabbitUsername);
        connectionFactory.setPassword(rabbitPassword);
        return connectionFactory;
    }

    @Bean
    public FanoutExchange eventBusExchange() {
        return new FanoutExchange(rabbitExchangeName, rabbitExchangeDurable, rabbitExchangeAutodelete);
    }

    @Bean
    public Queue eventBusQueue() {
        return new Queue(rabbitQueueName, rabbitQueueDurable, rabbitQueueExclusive, rabbitQueueAutoDelete);
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
    public CommandBus commandBus() {
        return new SimpleCommandBus();
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
        terminal.setExchangeName(rabbitExchangeName);
        terminal.setListenerContainerLifecycleManager(listenerContainerLifecycleManager);
        terminal.setDurable(true);
        terminal.setTransactional(false);
        return terminal;
    }

    @Bean
    public SimpleCluster simpleCluster() {
        SimpleCluster simpleCluster = new SimpleCluster(rabbitQueueName);
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

    @Bean
    public EventStore eventStore() {
        return new FileSystemEventStore(new SimpleEventFileResolver(new File("./events")));
    }
}
