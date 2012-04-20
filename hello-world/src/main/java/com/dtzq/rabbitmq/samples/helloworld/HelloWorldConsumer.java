package com.dtzq.rabbitmq.samples.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * <p>
 * Simple consumer class that connects the the specified RabbitMQ instance and waits for messages
 * to print to the console.
 * </p>
 */
public class HelloWorldConsumer {
    private static final String QUEUE_NAME = "test_queue";
    private static final String RABBITMQ_HOST = "localhost";

    private void consumeMessages(String hostname, String queueName) throws IOException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(hostname);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicConsume(queueName, true, queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println("Received message: " + new String(delivery.getBody()));
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HelloWorldConsumer helloWorldConsumer = new HelloWorldConsumer();
        helloWorldConsumer.consumeMessages(RABBITMQ_HOST, QUEUE_NAME);
    }
}
