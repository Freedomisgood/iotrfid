package top.nymrli.iotrfid;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.nymrli.iotrfid.bean.IotMqttClient;

@SpringBootTest
class IotrfidApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private IotMqttClient iotMqttClient;

    @Test
    public void test_mqtt(){
        System.out.println(iotMqttClient.topic);
        iotMqttClient.publish(iotMqttClient.topic, "hello");
    }

}
