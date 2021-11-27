package top.nymrli.iotrfid.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @description: MQTT配置类
 * @author: MrLi
 * @create: 2021-10-11 18:23
 **/
@PropertySource("classpath:mqtt.properties")
@Component
public class MqttConfiguration {
    @Value("${url}")
    public String host;
    @Value("${consumer.defaultTopic}")
    public String topic;
    @Value("${username}")
    public String name;
    @Value("${password}")
    public String passWord;
}
