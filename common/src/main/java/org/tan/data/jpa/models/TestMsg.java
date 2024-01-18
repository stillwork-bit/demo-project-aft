package org.tan.data.jpa.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(doNotUseGetters = true)
@Entity
@Getter
@Table(name = "test_msg", schema = "test_schema")
public class TestMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "json", length = 10000)
    private String json;
}
