package direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 发送日志信息
public class EmitDirectLog {
    private static final String EXCHANGE_NAME = "direct_logs";

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
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String message = "Hello World";
        channel.basicPublish(EXCHANGE_NAME, "info", null, message.getBytes("UTF-8"));
        System.out.println("发送了消息，等级为info" + message);
        channel.basicPublish(EXCHANGE_NAME, "warning", null, message.getBytes("UTF-8"));
        System.out.println("发送了消息，等级为warning" + message);
        channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes("UTF-8"));
        System.out.println("发送了消息，等级为error" + message);
        channel.close();
        connection.close();
    }
}
