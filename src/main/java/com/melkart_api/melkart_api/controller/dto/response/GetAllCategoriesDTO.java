package com.melkart_api.melkart_api.controller.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GetAllCategoriesDTO {
    private Long id;
    private String name;
    private List<GetAllSubCategoryDTO> subCategories;
}
