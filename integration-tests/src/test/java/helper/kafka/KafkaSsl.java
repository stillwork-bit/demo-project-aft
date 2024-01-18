package helper.kafka;

import lombok.experimental.UtilityClass;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SslConfigs;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static helper.Constants.*;
import static org.apache.kafka.common.config.SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG;
import static org.apache.kafka.common.config.SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG;

@UtilityClass
public class KafkaSsl {

    public static void sslEnabled(Properties properties) {
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SECURITY_PROTOCOL_CONFIG);
        properties.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, SSL_TRUSTSTORE_TYPE_CONFIG);
        properties.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, SSL_KEYSTORE_TYPE_CONFIG);
        properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, SSL_TRUSTSTORE_LOCATION_CONFIG);
        properties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, SSL_KEYSTORE_LOCATION_CONFIG);
        properties.put(SSL_TRUSTSTORE_PASSWORD_CONFIG, SSL_TRUSTSTORE_PASSWORD_CONFIG);
        properties.put(SSL_KEYSTORE_PASSWORD_CONFIG, SSL_KEYSTORE_PASSWORD_CONFIG);
    }
}
