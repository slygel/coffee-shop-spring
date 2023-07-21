package com.project.coffeeshop.service;

import com.project.coffeeshop.entity.Category;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.model.CategoryModel;
import com.project.coffeeshop.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryModel> getAll(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryModel> categoryModels = new ArrayList<>();
        for(Category category : categories){
            categoryModels.add(new CategoryModel(category));
        }
        return categoryModels;
    }

    public CategoryModel getByName(String name){
        Category category = categoryRepository.getCategoryByName(name);
        return new CategoryModel(category);
    }

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void deleteById(Long id){
        try {
            categoryRepository.deleteById(id);
        }catch (Exception e){
            String message = "Can not delete Catagory with id " + id;
            throw new CatchException(message);
        }
    }

    public void updateCategory(Long id , Category category){
        Category update = categoryRepository.getById(id);

        update.setName(category.getName());

        categoryRepository.save(update);

        new CategoryModel(update);
    }
}
