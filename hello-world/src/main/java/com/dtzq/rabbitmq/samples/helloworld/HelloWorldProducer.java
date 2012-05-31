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

    private void produceMessage() throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RABBITMQ_HOST);

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, TEST_MESSAGE.getBytes());

        System.out.println("Sent message: " + TEST_MESSAGE);

        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws Exception {
        HelloWorldProducer helloWorldProducer = new HelloWorldProducer();
        helloWorldProducer.produceMessage();
    }
}
