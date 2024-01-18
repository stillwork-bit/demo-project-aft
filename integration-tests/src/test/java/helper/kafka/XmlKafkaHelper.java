package helper.kafka;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import helper.AllureHelper;

public class XmlKafkaHelper<T> extends AbstractKafkaHelper<T> {

    public XmlKafkaHelper(String server, String topic, String uniqueRecordId, Class<T> responseClass) {
        super(server, topic, uniqueRecordId, new XmlMapper().configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true), responseClass);
    }

    public XmlKafkaHelper(String server, String topic, Class<T> responseClass) {
        this(server, topic, null, responseClass);
    }

    public XmlKafkaHelper(String server, String topic) {
        this(server, topic, null);
    }

    @Override
    protected AllureHelper.ContentType getContentType() {
        return AllureHelper.ContentType.XML;
    }
}
