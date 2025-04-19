package producers;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.BooleanSerializer;
import org.apache.kafka.common.serialization.StringSerializer;


import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

//produtor
public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        var producerFraud = new KafkaProducer<String, Boolean>(propertiesForFraud());
        var producerEmail = new KafkaProducer<String, String>(propertiesForEmail());

        for (int i = 0; i < 10; i++) {
            var key = UUID.randomUUID().toString();
            var fraudRecord = new ProducerRecord<String, Boolean>("ECOMMERCE_RECORD_ORDER", key, true);
            var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", key, "dummyValueEmail");

            producerFraud.send(fraudRecord, getCallback()).get();
            producerEmail.send(emailRecord, getCallback()).get();
        }
    }

    private static Callback getCallback() {
        return (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando " + data.topic() + ":::partition"
                    + data.partition() + "/ offset" + data.offset() + "/ timestamp" + data.timestamp());
        };
    }

    private static Properties propertiesForEmail() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    private static Properties propertiesForFraud() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BooleanSerializer.class.getName());
        return properties;
    }
}
