package helper.kafka;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Setter
@ToString
public class Record<T> {
    @Getter
    private T value;
    @Getter
    private String key;
    private Headers headers;

    public String getHeader(String headerName) {
        final byte[] value;

        try {
            value = headers.lastHeader(headerName).value();
            return new String(value, StandardCharsets.UTF_8);
        } catch (NullPointerException ignore) {
            log.error("Header named {} not found", headerName);
            return null;
        }
    }

    public Map<Object, Object> getHeaders() {
        var result = new HashMap<>();
        for (Header header : headers.toArray()) {
            result.put(header.key(), new String(header.value(), StandardCharsets.UTF_8));
        }

        return result;
    }
}
