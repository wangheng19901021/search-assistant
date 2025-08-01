package com.findthing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.findthing.entity.Location;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LocationMapper extends BaseMapper<Location> {
    
    @Select("SELECT COUNT(*) FROM tb_item WHERE location_id = #{locationId} AND is_deleted = 0")
    int countItemsByLocationId(Long locationId);
}