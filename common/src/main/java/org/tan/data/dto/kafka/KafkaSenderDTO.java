package org.tan.data.dto.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@Builder(toBuilder = true)
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KafkaSenderDTO {
    private String topic;
    private String file;
    private String message;
}