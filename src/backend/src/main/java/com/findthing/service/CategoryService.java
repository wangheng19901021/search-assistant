package com.findthing.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.findthing.entity.Category;
import com.findthing.mapper.CategoryMapper;
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
public class CategoryService {
    
    private final CategoryMapper categoryMapper;
    
    public List<Category> getAllCategories() {
        try {
            List<Category> categories = categoryMapper.selectList(null);
            return categories != null ? categories : Collections.emptyList();
        } catch (Exception e) {
            log.error("获取分类列表失败", e);
            return Collections.emptyList();
        }
    }
    
    public List<Category> getCategoriesByUserId(Long userId) {
        try {
            if (userId == null) {
                log.warn("用户ID为空，返回系统分类");
                return getSystemCategories();
            }
            
            List<Category> categories = categoryMapper.selectCategoriesByUserId(userId);
            return categories != null ? categories : Collections.emptyList();
        } catch (Exception e) {
            log.error("获取用户分类列表失败，userId: {}", userId, e);
            return getSystemCategories();
        }
    }
    
    public List<Category> getSystemCategories() {
        try {
            List<Category> categories = categoryMapper.selectList(
                new LambdaQueryWrapper<Category>()
                    .isNull(Category::getUserId)
                    .eq(Category::getIsSystem, 1)
                    .orderByAsc(Category::getCategoryId)
            );
            return categories != null ? categories : Collections.emptyList();
        } catch (Exception e) {
            log.error("获取系统分类失败", e);
            return Collections.emptyList();
        }
    }
    
    public void addCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("分类信息不能为空");
        }
        if (!StringUtils.hasText(category.getName())) {
            throw new IllegalArgumentException("分类名称不能为空");
        }
        if (category.getUserId() == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        // 检查用户是否已有同名分类
        long count = categoryMapper.selectCount(
            new LambdaQueryWrapper<Category>()
                .eq(Category::getUserId, category.getUserId())
                .eq(Category::getName, category.getName())
        );
        
        if (count > 0) {
            throw new RuntimeException("分类名称已存在");
        }
        
        // 设置为用户自定义分类
        category.setIsSystem(0);
        categoryMapper.insert(category);
    }
    
    public void updateCategory(Category category) {
        if (category == null || category.getCategoryId() == null || category.getUserId() == null) {
            throw new IllegalArgumentException("更新信息不完整");
        }
        
        // 检查是否为系统分类
        Category existing = categoryMapper.selectById(category.getCategoryId());
        if (existing == null) {
            throw new RuntimeException("分类不存在");
        }
        if (existing.getIsSystem() == 1) {
            throw new RuntimeException("系统分类不允许修改");
        }
        if (!existing.getUserId().equals(category.getUserId())) {
            throw new RuntimeException("无权限修改此分类");
        }
        
        // 检查名称是否与其他分类重复
        long count = categoryMapper.selectCount(
            new LambdaQueryWrapper<Category>()
                .eq(Category::getUserId, category.getUserId())
                .eq(Category::getName, category.getName())
                .ne(Category::getCategoryId, category.getCategoryId())
        );
        
        if (count > 0) {
            throw new RuntimeException("分类名称已存在");
        }
        
        categoryMapper.updateById(category);
    }
    
    @Transactional
    public void deleteCategory(Long categoryId, Long userId) {
        if (categoryId == null || userId == null) {
            throw new IllegalArgumentException("分类ID和用户ID不能为空");
        }
        
        try {
            // 检查分类是否存在且属于该用户
            Category category = categoryMapper.selectById(categoryId);
            if (category == null) {
                throw new RuntimeException("分类不存在");
            }
            if (category.getIsSystem() == 1) {
                throw new RuntimeException("系统分类不允许删除");
            }
            if (!category.getUserId().equals(userId)) {
                throw new RuntimeException("无权限删除此分类");
            }
            
            // 检查是否有物品使用此分类
            int itemCount = categoryMapper.countItemsByCategoryId(categoryId);
            if (itemCount > 0) {
                throw new RuntimeException("该分类下还有物品，无法删除");
            }
            
            int deleted = categoryMapper.deleteById(categoryId);
            if (deleted == 0) {
                throw new RuntimeException("删除分类失败");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("删除分类失败，categoryId: {}, userId: {}", categoryId, userId, e);
            throw new RuntimeException("删除分类失败");
        }
    }
}