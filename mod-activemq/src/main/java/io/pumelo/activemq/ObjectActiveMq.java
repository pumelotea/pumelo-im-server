package io.pumelo.activemq;

import com.alibaba.fastjson.JSON;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * author: pumelo
 * 2018/5/4
 */
@Component
public class ObjectActiveMq {
    @Autowired
    private JmsMessagingTemplate messagingTemplate;

    /**
     * 发送消息
     * @param destinationName
     * @param message
     */
    public void send(String destinationName,String message){
        messagingTemplate.convertAndSend(new ActiveMQQueue(destinationName),message);
    }

    public <T> void send(String destinationName,T object){
        messagingTemplate.convertAndSend(new ActiveMQQueue(destinationName), JSON.toJSONString(object));
    }

    /**
     * 发送消息
     * 支持延迟
     * @param destinationName
     * @param object
     * @param delayMs
     * @param <T>
     */
    public <T> void send(String destinationName,T object,long delayMs){
        ConnectionFactory connectionFactory = messagingTemplate.getConnectionFactory();
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(destinationName);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage textMessage = session.createTextMessage(JSON.toJSONString(object));
            //设置延迟时间
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayMs);
            //发送
            producer.send(textMessage);
            session.commit();
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
