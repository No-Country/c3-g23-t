package com.estore.ecomerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelImage {
    private String idImage;
    private String imageName;
    private String urlImage;
}
