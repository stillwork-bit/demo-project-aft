package helper.kafka;

import com.fasterxml.jackson.databind.json.JsonMapper;
import helper.AllureHelper;

public class JsonKafkaHelper<T> extends AbstractKafkaHelper<T> {

    public JsonKafkaHelper(String server, String topic, String uniqueRecordId, Class<T> responseClass) {
        super(server, topic, uniqueRecordId, new JsonMapper(), responseClass);
    }

    public JsonKafkaHelper(String server, String topic, Class<T> responseClass) {
        this(server, topic, null, responseClass);
    }

    public JsonKafkaHelper(String server, String topic) {
        this(server, topic, null);
    }

    @Override
    protected AllureHelper.ContentType getContentType() {
        return AllureHelper.ContentType.JSON;
    }
}
