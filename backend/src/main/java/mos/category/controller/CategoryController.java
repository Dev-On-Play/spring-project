package mos.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mos.category.dto.CategoriesResponse;
import mos.category.dto.CreateCategoryRequest;
import mos.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Tag(name = "카테고리 관련 API")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "신규 카테고리 생성")
    @PostMapping("/api/categories/create")
    public ResponseEntity<Void> createCategory(@RequestBody CreateCategoryRequest request) {
        Long createdCategoryId = categoryService.createCategory(request);
        return ResponseEntity.created(URI.create("api/categories/" + createdCategoryId)).build();
    }

    @Operation(summary = "전체 카테고리 조회")
    @GetMapping("/api/categories")
    public ResponseEntity<CategoriesResponse> findCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}
