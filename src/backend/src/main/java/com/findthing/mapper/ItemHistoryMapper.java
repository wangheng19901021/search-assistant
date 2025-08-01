package com.findthing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.findthing.entity.ItemHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemHistoryMapper extends BaseMapper<ItemHistory> {
    
    @Select("SELECT * FROM item_history WHERE item_id = #{itemId} AND user_id = #{userId} ORDER BY timestamp DESC LIMIT 50")
    List<ItemHistory> getItemHistory(Long itemId, Long userId);
}