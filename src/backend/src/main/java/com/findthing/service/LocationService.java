package com.findthing.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.findthing.entity.Location;
import com.findthing.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {
    
    private final LocationMapper locationMapper;
    
    public List<Location> getLocationsByUserId(Long userId) {
        try {
            if (userId == null) {
                log.warn("用户ID为空，返回空列表");
                return Collections.emptyList();
            }
            
            List<Location> locations = locationMapper.selectList(new LambdaQueryWrapper<Location>()
                    .eq(Location::getUserId, userId));
            return locations != null ? locations : Collections.emptyList();
        } catch (Exception e) {
            log.error("获取位置列表失败，userId: {}", userId, e);
            return Collections.emptyList();
        }
    }
    
    public void addLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("位置信息不能为空");
        }
        if (!StringUtils.hasText(location.getName())) {
            throw new IllegalArgumentException("位置名称不能为空");
        }
        if (location.getUserId() == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        if (!StringUtils.hasText(location.getIcon())) {
            location.setIcon("📦");
        }
        
        locationMapper.insert(location);
    }
    
    public void updateLocation(Location location) {
        if (location == null || location.getLocationId() == null || location.getUserId() == null) {
            throw new IllegalArgumentException("更新信息不完整");
        }
        
        locationMapper.updateById(location);
    }
    
    @Transactional
    public void deleteLocation(Long locationId, Long userId) {
        if (locationId == null || userId == null) {
            throw new IllegalArgumentException("位置ID和用户ID不能为空");
        }
        
        try {
            int itemCount = locationMapper.countItemsByLocationId(locationId);
            if (itemCount > 0) {
                throw new RuntimeException("该位置下还有物品，无法删除");
            }
            
            int deleted = locationMapper.delete(new LambdaQueryWrapper<Location>()
                    .eq(Location::getLocationId, locationId)
                    .eq(Location::getUserId, userId));
            
            if (deleted == 0) {
                throw new RuntimeException("位置不存在或无权限删除");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("删除位置失败，locationId: {}, userId: {}", locationId, userId, e);
            throw new RuntimeException("删除位置失败");
        }
    }
}