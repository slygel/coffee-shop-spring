package com.project.coffeeshop.controller;

import com.project.coffeeshop.entity.Category;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.exception.DeleteResponse;
import com.project.coffeeshop.model.CategoryModel;
import com.project.coffeeshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryModel>> getAllCategory(){
        List<CategoryModel> categoryModels = new ArrayList<>(categoryService.getAll());
        return new ResponseEntity<>(categoryModels, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/category-name/{name}")
    public ResponseEntity<CategoryModel> getByName(@PathVariable("name") String name){
        CategoryModel categoryModel = categoryService.getByName(name);
        return new ResponseEntity<>(categoryModel,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post/category")
    public void add(@RequestBody Category category){
        categoryService.addCategory(category);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/category/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            categoryService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CatchException e){
            String message = e.getMessage();
            DeleteResponse deleteResponse = new DeleteResponse(message);
            return new ResponseEntity<>(deleteResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/category/{id}")
    public void update(@PathVariable Long id ,
                       @RequestBody Category category){
        categoryService.updateCategory(id,category);
    }
}
