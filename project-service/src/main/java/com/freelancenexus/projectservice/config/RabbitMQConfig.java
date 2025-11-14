package com.freelancenexus.projectservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQConfig
 *
 * <p>Configuration for RabbitMQ message broker integration in the Project Service.
 * Sets up the messaging topology including exchanges, queues, and bindings.
 * Configures JSON message serialization and provides a RabbitTemplate bean for
 * publishing messages to the broker.</p>
 *
 * <p>Messaging flow:
 * <ul>
 *   <li>Project events (creation) published to the project exchange</li>
 *   <li>Proposal events (submission) published to the project exchange</li>
 *   <li>Topic-based routing directs messages to appropriate queues</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 */
@Configuration
public class RabbitMQConfig {

    /**
     * Name of the topic exchange for project-related events.
     * Loaded from property: {@code rabbitmq.exchange.project}
     */
    @Value("${rabbitmq.exchange.project}")
    private String projectExchange;

    /**
     * Name of the queue for project creation events.
     * Loaded from property: {@code rabbitmq.queue.project.created}
     */
    @Value("${rabbitmq.queue.project.created}")
    private String projectCreatedQueue;

    /**
     * Name of the queue for proposal submission events.
     * Loaded from property: {@code rabbitmq.queue.proposal.submitted}
     */
    @Value("${rabbitmq.queue.proposal.submitted}")
    private String proposalSubmittedQueue;

    /**
     * Routing key for project creation events.
     * Loaded from property: {@code rabbitmq.routing.project.created}
     */
    @Value("${rabbitmq.routing.project.created}")
    private String projectCreatedRoutingKey;

    /**
     * Routing key for proposal submission events.
     * Loaded from property: {@code rabbitmq.routing.proposal.submitted}
     */
    @Value("${rabbitmq.routing.proposal.submitted}")
    private String proposalSubmittedRoutingKey;

    /**
     * Create a TopicExchange bean for project-related events.
     *
     * <p>Topic exchanges use routing keys to direct messages to bound queues
     * based on pattern matching.</p>
     *
     * @return configured {@link TopicExchange}
     */
    @Bean
    public TopicExchange projectExchange() {
        return new TopicExchange(projectExchange);
    }

    /**
     * Create a durable queue for project creation events.
     *
     * @return configured {@link Queue} for project creation
     */
    @Bean
    public Queue projectCreatedQueue() {
        return new Queue(projectCreatedQueue, true);
    }

    /**
     * Create a durable queue for proposal submission events.
     *
     * @return configured {@link Queue} for proposal submission
     */
    @Bean
    public Queue proposalSubmittedQueue() {
        return new Queue(proposalSubmittedQueue, true);
    }

    /**
     * Bind the project creation queue to the project exchange.
     *
     * <p>Messages published to the exchange with the project creation routing key
     * will be delivered to this queue.</p>
     *
     * @return configured {@link Binding}
     */
    @Bean
    public Binding projectCreatedBinding() {
        return BindingBuilder
                .bind(projectCreatedQueue())
                .to(projectExchange())
                .with(projectCreatedRoutingKey);
    }

    /**
     * Bind the proposal submission queue to the project exchange.
     *
     * <p>Messages published to the exchange with the proposal submission routing key
     * will be delivered to this queue.</p>
     *
     * @return configured {@link Binding}
     */
    @Bean
    public Binding proposalSubmittedBinding() {
        return BindingBuilder
                .bind(proposalSubmittedQueue())
                .to(projectExchange())
                .with(proposalSubmittedRoutingKey);
    }

    /**
     * Create a message converter that serializes messages to/from JSON format.
     *
     * @return {@link MessageConverter} using Jackson2 JSON conversion
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Create a RabbitTemplate bean configured for publishing messages.
     *
     * <p>The template is pre-configured with the JSON message converter for
     * automatic serialization of message payloads.</p>
     *
     * @param connectionFactory the RabbitMQ connection factory
     * @return configured {@link RabbitTemplate} for message publishing
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}