package helper;


import static helper.ReadSystemVariables.readParam;

public class Constants {
    public static final String CONTOUR = readParam("url.contour");
    public static final String CONTOUR_ENV = readParam("url.contourEnv");
    public static final String BASE_URL = readParam("url.baseUrl");

    public static final String BASE_URL_UI = readParam("url.baseUrlUi");
    public static final String LOGIN = readParam("credential.login");
    public static final String PASSWORD = readParam("credential.password");

    public static final String SELENIUM_SERVER = readParam("selenium.seleniumServer");
    public static final String KAFKA_SERVER = readParam("kafka.kafkaServer");
    public static final String CONSUMER_TOPIC = readParam("kafka.consumerTopic");
    public static final String PRODUCER_TOPIC = readParam("kafka.producerTopic");
    public static final String SSL_TRUSTSTORE_PASSWORD = readParam("kafka.producerTopic");
    public static final String SSL_KEYSTORE_PASSWORD = readParam("kafka.producerTopic");
    public static final String GROUP_ID_CONFIG = readParam("kafka.groupId");
    public static final String MAX_POLL_INTERVAL_MS_CONFIG = readParam("kafka.ssl.maxPollIntervalMs");
    public static final String MAX_POLL_RECORDS_CONFIG = readParam("kafka.ssl.maxPollRecordsConfig");
    public static final String AUTO_OFFSET_RESET_CONFIG = readParam("kafka.ssl.autoOffsetResetConfig");
//    public static final String SSL_TRUSTSTORE_PASSWORD = readParam("kafka.ssl.trustStorePassword");
//    public static final String SSL_KEYSTORE_PASSWORD = readParam("kafka.ssl.keyStorePassword");
    public static final String ACKS_CONFIG = readParam("kafka.acksConfig");

    public static final String SSL_ENABLE = readParam("kafka.ssl.enable");
    public static final String SECURITY_PROTOCOL_CONFIG = readParam("kafka.ssl.securityProtocol");
    public static final String SSL_TRUSTSTORE_TYPE_CONFIG = readParam("kafka.ssl.sslTrustStoreTypeConfig");
    public static final String SSL_KEYSTORE_TYPE_CONFIG = readParam("kafka.ssl.sslTrustStoreTypeConfig");
    public static final String SSL_TRUSTSTORE_LOCATION_CONFIG = readParam("kafka.ssl.sslTrustStoreLocation");
    public static final String SSL_KEYSTORE_LOCATION_CONFIG = readParam("kafka.ssl.sslKeyStoreLocation");
}
