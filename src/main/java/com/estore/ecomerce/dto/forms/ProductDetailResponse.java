package com.estore.ecomerce.dto.forms;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.ClientResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {
    private String name;
    private Double price;
    private String description;
    private int stock = 0;
    private String content;
    private double discount = 0.0;
    private List<Category> categories = new ArrayList<Category>();
    private ImageProfile imageProfile;
    private List<ImagePost> imagePost = new ArrayList<ImagePost>();

    private ClientResponse clientResponse;
}
