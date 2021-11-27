package top.nymrli.iotrfid.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: MrLi
 * @create: 2021-10-11 16:34
 **/
@Slf4j
public class ReportMqtt implements MqttCallback {
    private IotMqttClient iotMqttClient;

    private Map<String, String> accountMap = new HashMap();

    public ReportMqtt(IotMqttClient iotMqttClient) {
        this.iotMqttClient = iotMqttClient;
        accountMap.put("cardlogin", "E47EF2AD");
    }

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
            // 查数据库db, 这边改用hashmap预存的值简化处理
            RFIDMsg rfidMsg = JSONObject.parseObject(message.getPayload(), RFIDMsg.class);
            String rfid = accountMap.get(rfidMsg.getFunction());
            if ( null != rfid ){
                if ( rfid.equals(rfidMsg.getRfid())){
                    // 如果密码正确
                    iotMqttClient.publish("/rfid/callback", "{\"msg\": \"RFID login succeed\", " +
                            "\"function\": " +
                            "\"cardlogin\", \"status\": \"1\"}");
                }else{
                    // 如果密码正确
                    iotMqttClient.publish("/rfid/callback", "{\"msg\": \"RFID login failed\", \"function\":" +
                            "\"cardlogin\", \"status\": \"0\"}");
                }
            }
            System.out.println("发送成功");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("消息发送成功");
    }
}

@Data
class RFIDMsg{
    private String function;
    private String rfid;
}

