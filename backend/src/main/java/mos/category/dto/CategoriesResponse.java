package mos.category.dto;

import mos.category.entity.Category;

import java.util.List;

public record CategoriesResponse(List<String> categories) {

    public static CategoriesResponse from(List<Category> categories) {
        List<String> converted = categories.stream()
                .map(Category::getName)
                .toList();
        return new CategoriesResponse(converted);
    }
}
