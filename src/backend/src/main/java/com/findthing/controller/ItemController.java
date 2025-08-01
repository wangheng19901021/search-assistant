package com.findthing.controller;

import com.findthing.dto.ItemAddRequest;
import com.findthing.entity.Item;
import com.findthing.service.ItemService;
import com.findthing.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    
    private final ItemService itemService;
    
    @PostMapping("/add")
    public Result<Void> addItem(@RequestBody ItemAddRequest request) {
        try {
            itemService.addItem(request);
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("添加物品参数错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("添加物品失败", e);
            return Result.error("添加物品失败");
        }
    }
    
    @GetMapping("/list")
    public Result<Map<String, Object>> getItemList(
            @RequestParam Long userId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> result = itemService.getItemList(userId, keyword, page, size);
            return Result.success(result);
        } catch (Exception e) {
            log.error("获取物品列表失败", e);
            return Result.error("获取物品列表失败");
        }
    }
    
    @GetMapping("/{itemId}")
    public Result<Item> getItemDetail(@PathVariable Long itemId, @RequestParam Long userId) {
        try {
            Item item = itemService.getItemById(itemId, userId);
            if (item == null) {
                return Result.error("物品不存在");
            }
            return Result.success(item);
        } catch (IllegalArgumentException e) {
            log.warn("获取物品详情参数错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("获取物品详情失败", e);
            return Result.error("获取物品详情失败");
        }
    }
    
    @PutMapping("/{itemId}")
    public Result<Void> updateItem(@PathVariable Long itemId, @RequestBody Item item, @RequestParam Long userId) {
        try {
            item.setItemId(itemId);
            item.setUserId(userId);
            itemService.updateItem(item);
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("更新物品参数错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("更新物品失败", e);
            return Result.error("更新物品失败");
        }
    }
    
    @DeleteMapping("/{itemId}")
    public Result<Void> deleteItem(@PathVariable Long itemId, @RequestParam Long userId) {
        try {
            itemService.deleteItem(itemId, userId);
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("删除物品参数错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("删除物品失败", e);
            return Result.error("删除物品失败");
        }
    }
}