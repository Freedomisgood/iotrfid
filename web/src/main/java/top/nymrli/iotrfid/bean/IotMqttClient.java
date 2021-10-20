package top.nymrli.iotrfid.bean;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: MrLi
 * @create: 2021-10-11 16:30
 **/
@Slf4j
@Component
@PropertySource("classpath:mqtt.properties")
public class IotMqttClient {
    @Value("${url}")
    public String host;
    @Value("${consumer.defaultTopic}")
    public String topic;
    @Value("${username}")
    private String name;
    @Value("${password}")
    private String passWord;

    private MqttClient client;
    private static volatile IotMqttClient iotMqttClient = null;
    private static List<String> subedTopics = new ArrayList<>();

    @Bean
    public static IotMqttClient getInstance() throws MqttException {
        if (null == iotMqttClient) {
            synchronized (IotMqttClient.class) {
                if (null == iotMqttClient) {
                    iotMqttClient = new IotMqttClient();
                }
            }
        }
        return iotMqttClient;
    }

    @PostConstruct
    public void connect() {
        // clientId不能重复所以这里我设置为系统时间
        String clientId = String.valueOf(System.currentTimeMillis());
        int qos = 0;
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(host, clientId, new MemoryPersistence());
            // 设置回调
            client.setCallback(new ReportMqtt());
            client.connect(makeUpMqttOption());
            // 将配置中的主题订阅
//            String[] topics = {topic};
            subscribe(topic, qos);
        } catch (Exception e){
            log.info("ReportMqtt客户端连接异常，异常信息：" + e);
        }

    }

    private MqttConnectOptions makeUpMqttOption(){
        // MQTT的连接设置
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 设置连接的用户名
        options.setUserName(name);
        // 设置连接的密码
        options.setPassword(passWord.toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(3600);
        return options;
    }
    /**
     * 发布，默认qos为0，非持久化
     *
     * @param topic
     * @param pushMessage
     */
    public void publish(String topic, String pushMessage) {
        publish(0, false, topic, pushMessage);
    }

    /**
     * 发布
     *
     * @param qos
     * @param retained
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());

        MqttTopic mTopic = client.getTopic(topic);
        if (null == mTopic) {
            log.error("topic not exist");
        }
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
            token.waitForCompletion();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅某个主题，qos默认为0
     *
     * @param topic
     */
    public void subscribe(String topic) {
        subscribe(topic, 0);
        subedTopics.add(topic);
    }

    /**
     * 订阅某个主题
     *
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getSubedTopics() {
        return subedTopics;
    }
}