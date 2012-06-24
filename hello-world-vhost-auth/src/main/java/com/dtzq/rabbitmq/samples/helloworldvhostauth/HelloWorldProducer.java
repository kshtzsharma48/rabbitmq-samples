package com.dtzq.rabbitmq.samples.helloworldvhostauth;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * <p>
 * Simple producer class that sends a message to the RabbitMQ broker using a non-default user/pass and virtual host.
 * </p>
 */
public class HelloWorldProducer {
    private static final String QUEUE_NAME = "vhost_test_queue";
    private static final String RABBITMQ_HOST = "localhost";
    private static final String TEST_MESSAGE = "Hello world";
    private static final String USERNAME = "vhostauthtestuser";
    private static final String PASSWORD = "vhostauthtestpass";
    private static final String VIRTUAL_HOST = "vhostauthvhost";

    private void produceMessage() throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RABBITMQ_HOST);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        connectionFactory.setVirtualHost(VIRTUAL_HOST);

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
