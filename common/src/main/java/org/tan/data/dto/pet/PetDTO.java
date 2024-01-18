package org.tan.data.dto.pet;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class PetDTO {
    private int id;
    private String name;
    private CategoryDTO category;
    private ArrayList<String> photoUrls;
    private ArrayList<TagDTO> tags;
    private String status;
}

