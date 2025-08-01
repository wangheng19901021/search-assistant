package com.findthing.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ItemListResponse {
    private Long itemId;
    private String name;
    private String category;
    private String location;
    private String imageUrl;
    private LocalDateTime lastUpdateTime;
}