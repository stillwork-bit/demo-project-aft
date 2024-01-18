package org.tan.data.dto.pet;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class TagDTO {
    private int id;
    private String name;
}
