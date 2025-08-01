package com.findthing.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.findthing.dto.ItemAddRequest;
import com.findthing.dto.ItemListResponse;
import com.findthing.entity.Item;
import com.findthing.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    
    private final ItemMapper itemMapper;
    
    public void addItem(ItemAddRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("添加请求不能为空");
        }
        if (!StringUtils.hasText(request.getName())) {
            throw new IllegalArgumentException("物品名称不能为空");
        }
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        Item item = new Item();
        BeanUtils.copyProperties(request, item);
        itemMapper.insert(item);
    }
    
    public Map<String, Object> getItemList(Long userId, String keyword, int page, int size) {
        try {
            if (userId == null) {
                log.warn("用户ID为空，返回空列表");
                return Map.of("items", Collections.emptyList(), "total", 0L);
            }
            
            if (page < 1) page = 1;
            if (size < 1 || size > 100) size = 10;
            
            Page<ItemListResponse> pageObj = new Page<>(page, size);
            Page<ItemListResponse> result = itemMapper.selectItemListPage(pageObj, userId, keyword);
            
            return Map.of(
                "items", result.getRecords() != null ? result.getRecords() : Collections.emptyList(),
                "total", result.getTotal()
            );
        } catch (Exception e) {
            log.error("获取物品列表失败，userId: {}, keyword: {}", userId, keyword, e);
            return Map.of("items", Collections.emptyList(), "total", 0L);
        }
    }
    
    public Item getItemById(Long itemId, Long userId) {
        if (itemId == null || userId == null) {
            throw new IllegalArgumentException("物品ID和用户ID不能为空");
        }
        
        return itemMapper.selectOne(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Item>()
                .eq(Item::getItemId, itemId)
                .eq(Item::getUserId, userId));
    }
    
    public void updateItem(Item item) {
        if (item == null || item.getItemId() == null || item.getUserId() == null) {
            throw new IllegalArgumentException("更新信息不完整");
        }
        
        itemMapper.updateById(item);
    }
    
    public void deleteItem(Long itemId, Long userId) {
        if (itemId == null || userId == null) {
            throw new IllegalArgumentException("物品ID和用户ID不能为空");
        }
        
        itemMapper.update(new Item(), 
            new com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper<Item>()
                .eq(Item::getItemId, itemId)
                .eq(Item::getUserId, userId)
                .set(Item::getIsDeleted, 1));
    }
}