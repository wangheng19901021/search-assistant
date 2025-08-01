package com.findthing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.findthing.dto.ItemListResponse;
import com.findthing.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {
    
    @Select("""
        <script>
        SELECT i.item_id, i.name, c.name as category, l.name as location, 
               i.image_url, i.last_update_time
        FROM tb_item i 
        LEFT JOIN tb_category c ON i.category_id = c.category_id
        LEFT JOIN tb_location l ON i.location_id = l.location_id
        WHERE i.user_id = #{userId} AND i.is_deleted = 0
        <if test="keyword != null and keyword != ''">
            AND (i.name LIKE CONCAT('%', #{keyword}, '%') 
                OR c.name LIKE CONCAT('%', #{keyword}, '%') 
                OR l.name LIKE CONCAT('%', #{keyword}, '%'))
        </if>
        ORDER BY i.last_update_time DESC
        </script>
        """)
    Page<ItemListResponse> selectItemListPage(Page<ItemListResponse> page, 
                                            @Param("userId") Long userId, 
                                            @Param("keyword") String keyword);
}