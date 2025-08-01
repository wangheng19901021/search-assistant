package com.findthing.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("tb_location")
public class Location {
    @TableId(type = IdType.AUTO)
    private Long locationId;
    
    private Long userId;
    private String name;
    private String icon;
}