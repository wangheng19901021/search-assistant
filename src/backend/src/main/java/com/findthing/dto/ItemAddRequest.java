package com.findthing.dto;

import lombok.Data;

@Data
public class ItemAddRequest {
    private Long userId;
    private String name;
    private Integer categoryId;
    private Long locationId;
    private String imageUrl;
    private String description;
}