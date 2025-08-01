package com.findthing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("item_history")
public class ItemHistory {
    
    @TableId(type = IdType.AUTO)
    private Long historyId;
    
    private Long itemId;
    
    private Long userId;
    
    private String action;
    
    private String actionType;
    
    private String changes;
    
    private String operator;
    
    private LocalDateTime timestamp;
    
    private LocalDateTime createTime;
}