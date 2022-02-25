package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.dto.CategoryRequest;
import com.estore.ecomerce.dto.CategoryResponse;
import com.estore.ecomerce.mapper.CategoryMapper;
import com.estore.ecomerce.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import com.estore.ecomerce.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String ERROR_FIND_ID = "No se econtro la categoria";
    private static final String ERROR_CONECTION = "Error al intentar conectar con la BD";
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private IProductRepository productRepository;

    @Transactional
    @Override
    public CategoryResponse addCategory(CategoryRequest entity) {
        try {
            Category newCategory = categoryMapper.categoryDtoEntity(entity);
            categoryRepository.save(newCategory);
            CategoryResponse responseCategory = categoryMapper.categoryEntityDto(newCategory);
            return responseCategory;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_CONECTION);
        }
    }

    @Transactional
    @Override
    public List<CategoryResponse> findAll() {
        try {
            List<CategoryResponse> listResponse = new ArrayList<>();
            List<Category> entities = categoryRepository.findAll();
            for (Category entity : entities) {
                listResponse.add(categoryMapper.categoryEntityDto(entity));
            }
            return listResponse;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_CONECTION);
        }

    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest entity) {
        try {
            Optional<Category> entityById = categoryRepository.findById(id);
            if (entityById.isPresent()) {
                CategoryResponse entityResponse = categoryMapper.categoryEntityDto(entityById.get());
                Category newCategory = categoryMapper.categoryDtoEntity(entity);
                entityResponse = categoryMapper.categoryEntityDto(newCategory);
                newCategory = categoryRepository.save(newCategory);
                return entityResponse;
            } else {
                throw new EntityNotFoundException(ERROR_FIND_ID);
            }

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_CONECTION);
        }

    }

    @Override
    public CategoryResponse findById(Long id) {
        try {
            Optional<Category> entityById = categoryRepository.findById(id);
            if (entityById.isPresent()) {
                CategoryResponse entityResponse = categoryMapper.categoryEntityDto(entityById.get());
                return entityResponse;
            } else {
                throw new EntityNotFoundException(ERROR_FIND_ID);
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_CONECTION);
        }
    }

    @Override
    public List<CategoryResponse> listCategoryActive() {
        try {
            List<CategoryResponse> listResponse = new ArrayList<>();
            List<Category> entities = categoryRepository.listCategoryActive();
            for (Category entity : entities) {
                listResponse.add(categoryMapper.categoryEntityDto(entity));
            }
            return listResponse;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_CONECTION);
        }
    }

    @Override
    public List<CategoryResponse> listCategoryInactive() {
      try {
            List<CategoryResponse> listResponse = new ArrayList<>();
            List<Category> entities = categoryRepository.listCategoryInactive();
            for (Category entity : entities) {
                listResponse.add(categoryMapper.categoryEntityDto(entity));
            }
            return listResponse;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ERROR_CONECTION);
        }   }

    @Override
    public void delete(Long id)throws EntityNotFoundException {
        Category category = getCategory(id);
        category.setSoftDeleted(true);
        categoryRepository.save(category);
    }

    private Category getCategory(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty() || category.get().isSoftDeleted()){
            throw new EntityNotFoundException(ERROR_FIND_ID);
        }
        return category.get();
    }

}
