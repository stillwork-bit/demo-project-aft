package org.tan.data.dto.pet;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class CategoryDTO {
    private int id;
    private String name;
}
