package com.example.warehouse.controllers;

import com.example.warehouse.models.Category;
import com.example.warehouse.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления категориями.
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    /**
     * Конструктор контроллера с внедрением репозитория категорий.
     *
     * @param categoryRepository репозиторий категорий
     */
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Получить список всех категорий.
     *
     * @return список категорий
     */
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Создать новую категорию.
     *
     * @param category новая категория
     * @return сохраненная категория
     */
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Обновить категорию по ID.
     *
     * @param id              идентификатор категории
     * @param updatedCategory обновленные данные категории
     * @return обновленная категория
     */
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(updatedCategory.getName());
            category.setDescription(updatedCategory.getDescription());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    /**
     * Удалить категорию по ID.
     *
     * @param id идентификатор категории
     */
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}
