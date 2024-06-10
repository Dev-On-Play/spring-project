package mos.category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mos.category.dto.CategoriesResponse;
import mos.category.dto.CreateCategoryRequest;
import mos.category.entity.Category;
import mos.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long createCategory(CreateCategoryRequest request) {
        Category category = Category.createCategory(request.name());
        categoryRepository.save(category);
        return category.getId();
    }

    public CategoriesResponse findAll() {
        List<Category> categories = categoryRepository.findAll();
        return CategoriesResponse.from(categories);
    }
}
