package com.findthing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.findthing.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    
    @Select("SELECT * FROM tb_category WHERE user_id IS NULL OR user_id = #{userId} ORDER BY is_system DESC, category_id ASC")
    List<Category> selectCategoriesByUserId(@Param("userId") Long userId);
    
    @Select("SELECT COUNT(*) FROM tb_item WHERE category_id = #{categoryId} AND is_deleted = 0")
    int countItemsByCategoryId(@Param("categoryId") Long categoryId);
}