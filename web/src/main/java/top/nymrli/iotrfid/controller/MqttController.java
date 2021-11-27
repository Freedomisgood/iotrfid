package top.nymrli.iotrfid.controller;

/**
 * @description:
 * @author: MrLi
 * @create: 2021-10-11 17:15
 **/

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.nymrli.iotrfid.bean.IotMqttClient;

/**
 * MQTT消息发送
 *
 * @author mrli
 */
@RestController
public class MqttController {
    @Autowired
    private IotMqttClient iotMqttClient;

    /**
     * 发送MQTT消息
     *
     * @param topic   订阅的主题
     * @param message 要发送的信息
     * @return 操作结果
     * @throws MqttException
     */
    @PostMapping(value = "/mqtt")
    public ResponseEntity<String> sendMqtt(@RequestParam(value = "topic") String topic,
                                           @RequestBody String message) throws MqttException {
        iotMqttClient.publish(topic, message);
        System.out.println(topic + " " + message);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    /**
     * MQTT订阅主题
     *
     * @param topic 待订阅的主题
     * @return 操作结果
     * @throws MqttException
     */
    @PostMapping(value = "/mqtt/subs")
    public ResponseEntity<String> sendMqtt(@RequestParam(value = "topic") String topic) throws MqttException {
        iotMqttClient.subscribe(topic);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}

