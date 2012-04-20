package com.dtzq.rabbitmq.samples.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * <p>
 * Simple producer class that sends a message to the RabbitMQ broker.
 * </p>
 */
public class HelloWorldProducer {
    private static final String QUEUE_NAME = "test_queue";
    private static final String RABBITMQ_HOST = "localhost";
    private static final String TEST_MESSAGE = "Hello world";

    private void produceMessage(String hostname, String queueName, String message) throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(hostname);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicPublish("", queueName, null, message.getBytes());

        System.out.println("Sent message: " + message);

        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws IOException {
        HelloWorldProducer helloWorldProducer = new HelloWorldProducer();
        helloWorldProducer.produceMessage(RABBITMQ_HOST, QUEUE_NAME, TEST_MESSAGE);
    }
}
