package com.findthing.controller;

import com.findthing.entity.Category;
import com.findthing.service.CategoryService;
import com.findthing.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping("/list")
    public Result<List<Category>> getCategories(@RequestParam(required = false) Long userId) {
        try {
            List<Category> categories;
            if (userId != null) {
                categories = categoryService.getCategoriesByUserId(userId);
            } else {
                categories = categoryService.getAllCategories();
            }
            return Result.success(categories);
        } catch (Exception e) {
            log.error("获取分类列表失败", e);
            return Result.error("获取分类列表失败");
        }
    }
    
    @PostMapping("/add")
    public Result<Void> addCategory(@RequestBody Category category) {
        try {
            categoryService.addCategory(category);
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("添加分类参数错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (RuntimeException e) {
            log.warn("添加分类业务错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("添加分类失败", e);
            return Result.error("添加分类失败");
        }
    }
    
    @PutMapping("/{categoryId}")
    public Result<Void> updateCategory(@PathVariable Long categoryId, @RequestBody Category category, @RequestParam Long userId) {
        try {
            category.setCategoryId(categoryId);
            category.setUserId(userId);
            categoryService.updateCategory(category);
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("更新分类参数错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (RuntimeException e) {
            log.warn("更新分类业务错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("更新分类失败", e);
            return Result.error("更新分类失败");
        }
    }
    
    @DeleteMapping("/{categoryId}")
    public Result<Void> deleteCategory(@PathVariable Long categoryId, @RequestParam Long userId) {
        try {
            categoryService.deleteCategory(categoryId, userId);
            return Result.success();
        } catch (RuntimeException e) {
            log.warn("删除分类业务错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("删除分类失败", e);
            return Result.error("删除分类失败");
        }
    }
}