package com.estore.ecomerce.repository;

import com.estore.ecomerce.domain.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    @Query("SELECT c from Category c WHERE c.status=true")
    public List<Category> listCategoryActive();

    @Query("SELECT c from Category c WHERE c.status=false")
    public List<Category> listCategoryInactive();

}
