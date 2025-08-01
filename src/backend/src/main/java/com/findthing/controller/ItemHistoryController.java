package com.findthing.controller;

import com.findthing.entity.ItemHistory;
import com.findthing.service.ItemHistoryService;
import com.findthing.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemHistoryController {
    
    private final ItemHistoryService itemHistoryService;
    
    @GetMapping("/{itemId}/history")
    public Result<List<ItemHistory>> getItemHistory(
            @PathVariable Long itemId,
            @RequestParam Long userId) {
        try {
            List<ItemHistory> history = itemHistoryService.getItemHistory(itemId, userId);
            return Result.success(history);
        } catch (Exception e) {
            log.error("获取物品历史记录失败", e);
            return Result.error("获取历史记录失败");
        }
    }
    
    @PostMapping("/{itemId}/history")
    public Result<Void> addItemHistory(
            @PathVariable Long itemId,
            @RequestParam Long userId,
            @RequestBody ItemHistory history) {
        try {
            history.setItemId(itemId);
            history.setUserId(userId);
            itemHistoryService.addItemHistory(history);
            return Result.success();
        } catch (Exception e) {
            log.error("添加物品历史记录失败", e);
            return Result.error("添加历史记录失败");
        }
    }
}