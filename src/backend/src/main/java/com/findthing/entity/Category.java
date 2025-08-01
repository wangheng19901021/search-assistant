package com.findthing.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("tb_category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Long categoryId;
    
    private String name;
    private Integer isSystem;
    private Long userId;  // 新增：所属用户ID，NULL表示系统分类
}