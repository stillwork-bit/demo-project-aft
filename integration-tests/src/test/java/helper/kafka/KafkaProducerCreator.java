package helper.kafka;

import lombok.experimental.UtilityClass;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static helper.Constants.ACKS_CONFIG;
import static helper.Constants.GROUP_ID_CONFIG;
import static helper.kafka.KafkaSsl.sslEnabled;


@UtilityClass
public class KafkaProducerCreator {

    public static Producer<String, String> createProducer(String server) {
        var props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                server);
        props.put(ProducerConfig.ACKS_CONFIG, ACKS_CONFIG);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, GROUP_ID_CONFIG + Thread.currentThread().getId());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());

        sslEnabled(props);

        return new KafkaProducer<>(props);
    }
}
