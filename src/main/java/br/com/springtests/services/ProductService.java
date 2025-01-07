package br.com.springtests.services;

import br.com.springtests.models.ProductModel;
import br.com.springtests.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductModel> findAll(){
        return productRepository.findAll();
    }

    public ProductModel findById(UUID id){
        return productRepository.findById(id).get();
    }

    public ProductModel save(ProductModel productModel){
        return productRepository.save(productModel);
    }

    public List<ProductModel> findAllByOrderByValueAsc(){
        return productRepository.findAllByOrderByValueAsc();
    }

    public void deleteById(UUID id){
        productRepository.deleteById(id);
    }
}
