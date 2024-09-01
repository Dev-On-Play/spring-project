package mos.category.dto;

import mos.category.entity.Category;

import java.util.List;

public record CategoriesResponse(List<CategoryResponse> categories) {

    public static CategoriesResponse from(List<Category> categories) {
        List<CategoryResponse> converted = categories.stream()
                .map(CategoryResponse::from)
                .toList();
        return new CategoriesResponse(converted);
    }
}
