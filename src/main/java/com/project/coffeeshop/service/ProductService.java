package com.project.coffeeshop.service;

import com.project.coffeeshop.entity.Product;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.model.ProductModel;
import com.project.coffeeshop.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductModel> getAllProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductModel> productModels = new ArrayList<>();
        for(Product product : products){ // Duyệt qua từng Product trong List products
            productModels.add(new ProductModel(product));
        }
        return productModels;
    }

    public ProductModel getProductModelById(Long id){
        try{
            Product product = productRepository.getById(id);
            return new ProductModel(product);
        }catch (Exception e){
            String message = "Not founded product with id "+ id;
            throw new CatchException(message);
        }
    }

    public ProductModel getProductModelByName(String name){
        Product product = productRepository.getProductByName(name);
        return new ProductModel(product);
    }

    @Transactional
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProductById(Long id){
        try {
            productRepository.deleteById(id);
        }catch (Exception e){
            String message = "Can not delete product with id "+ id;
            throw new CatchException(message);
        }
    }

    public ProductModel updateProduct(Long id , Product oldProduct){
        Product product = productRepository.getById(id);
            product.setName(oldProduct.getName());
            product.setImage(oldProduct.getImage());
            product.setPrice(oldProduct.getPrice());
            product.setCategory(oldProduct.getCategory());

            productRepository.save(product);

        return new ProductModel(product);
    }
}
