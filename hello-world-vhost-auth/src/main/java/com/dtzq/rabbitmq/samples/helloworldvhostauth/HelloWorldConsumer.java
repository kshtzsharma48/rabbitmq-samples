package com.dtzq.rabbitmq.samples.helloworldvhostauth;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * <p>
 * Simple consumer class that connects the the specified RabbitMQ instance and waits for messages
 * to print to the console using a non-default user/pass and virtual host.
 * </p>
 */
public class HelloWorldConsumer {
    private static final String QUEUE_NAME = "vhost_test_queue";
    private static final String RABBITMQ_HOST = "localhost";
    private static final String USERNAME = "vhostauthtestuser";
    private static final String PASSWORD = "vhostauthtestpass";
    private static final String VIRTUAL_HOST = "vhostauthvhost";

    private void consumeMessages() throws IOException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RABBITMQ_HOST);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        connectionFactory.setVirtualHost(VIRTUAL_HOST);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicConsume(QUEUE_NAME, true, queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println("Received message: " + new String(delivery.getBody()));
        }
    }

    public static void main(String[] args) throws Exception {
        HelloWorldConsumer helloWorldConsumer = new HelloWorldConsumer();
        helloWorldConsumer.consumeMessages();
    }
}
