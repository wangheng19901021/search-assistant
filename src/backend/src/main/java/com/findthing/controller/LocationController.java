package com.findthing.controller;

import com.findthing.entity.Location;
import com.findthing.service.LocationService;
import com.findthing.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {
    
    private final LocationService locationService;
    
    @GetMapping("/list")
    public Result<List<Location>> getLocations(@RequestParam Long userId) {
        try {
            List<Location> locations = locationService.getLocationsByUserId(userId);
            return Result.success(locations);
        } catch (Exception e) {
            log.error("获取位置列表失败", e);
            return Result.error("获取位置列表失败");
        }
    }
    
    @PostMapping("/add")
    public Result<Void> addLocation(@RequestBody Location location) {
        try {
            locationService.addLocation(location);
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("添加位置参数错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("添加位置失败", e);
            return Result.error("添加位置失败");
        }
    }
    
    @PutMapping("/{locationId}")
    public Result<Void> updateLocation(@PathVariable Long locationId, @RequestBody Location location, @RequestParam Long userId) {
        try {
            location.setLocationId(locationId);
            location.setUserId(userId);
            locationService.updateLocation(location);
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("更新位置参数错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("更新位置失败", e);
            return Result.error("更新位置失败");
        }
    }
    
    @DeleteMapping("/{locationId}")
    public Result<Void> deleteLocation(@PathVariable Long locationId, @RequestParam Long userId) {
        try {
            locationService.deleteLocation(locationId, userId);
            return Result.success();
        } catch (RuntimeException e) {
            log.warn("删除位置业务错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("删除位置失败", e);
            return Result.error("删除位置失败");
        }
    }
}