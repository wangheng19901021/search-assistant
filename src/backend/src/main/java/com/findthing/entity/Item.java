package com.findthing.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("tb_item")
public class Item {
    @TableId(type = IdType.AUTO)
    private Long itemId;
    
    private Long userId;
    private String name;
    private Long categoryId;
    private Long locationId;
    private String imageUrl;
    private String description;
    
    @TableLogic
    private Integer isDeleted;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastUpdateTime;
}