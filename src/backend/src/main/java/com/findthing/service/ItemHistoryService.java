package com.findthing.service;

import com.findthing.entity.ItemHistory;
import com.findthing.mapper.ItemHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemHistoryService {
    
    private final ItemHistoryMapper itemHistoryMapper;
    
    public List<ItemHistory> getItemHistory(Long itemId, Long userId) {
        return itemHistoryMapper.getItemHistory(itemId, userId);
    }
    
    public void addItemHistory(ItemHistory history) {
        history.setCreateTime(LocalDateTime.now());
        if (history.getTimestamp() == null) {
            history.setTimestamp(LocalDateTime.now());
        }
        itemHistoryMapper.insert(history);
    }
    
    public void recordItemChange(Long itemId, Long userId, String action, String actionType, String changes, String operator) {
        ItemHistory history = new ItemHistory();
        history.setItemId(itemId);
        history.setUserId(userId);
        history.setAction(action);
        history.setActionType(actionType);
        history.setChanges(changes);
        history.setOperator(operator);
        history.setTimestamp(LocalDateTime.now());
        
        addItemHistory(history);
    }
}