package topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 接收三个等级的日志
public class ReceiveLogTopic2 {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置rabbitMQ地址
        factory.setHost("zhangblog.cn");
        factory.setUsername("test");
        factory.setPassword("test");
        // 建立连接
        Connection connection = factory.newConnection();
        // 获得信道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();
        String routingKey1 = "*.*.rabbit";
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey1);
        String routingKey2 = "lazy.#";
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey2);
        System.out.println("开始接收消息");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("收到消息：" + message + "，routingKey:" + envelope.getRoutingKey());
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
