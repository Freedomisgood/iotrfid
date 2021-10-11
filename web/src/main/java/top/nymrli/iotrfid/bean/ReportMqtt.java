package top.nymrli.iotrfid.bean;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @description:
 * @author: MrLi
 * @create: 2021-10-11 16:34
 **/
@Slf4j
public class ReportMqtt implements MqttCallback {
    @Override
    public void connectionLost(Throwable throwable) {
        log.warn("程序出现异常，DReportMqtt断线！");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        log.info("接收消息主题:" + topic);
        log.info("接收消息Qos:" + message.getQos());
        log.info("接收消息内容 :" + new String(message.getPayload()));
        if ("/rfid/login".equals(topic)) {
            System.out.println("发送前");
            IotMqttClient.getInstance().publish("/rfid_callback", "200");
            System.out.println("发送成功");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("消息发送成功");
    }
}
