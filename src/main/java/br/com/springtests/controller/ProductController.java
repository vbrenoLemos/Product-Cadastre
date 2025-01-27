package br.com.springtests.controller;

import br.com.springtests.repository.ProductRepository;
import br.com.springtests.dtos.ProductRecordDTO;
import br.com.springtests.models.ProductModel;
import br.com.springtests.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/products")
    public ResponseEntity <List<ProductModel>> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDTO, productModel);
        productService.save(productModel);
        var allProducts = productService.findAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(allProducts);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> products = productService.findAll();
        if (!products.isEmpty()) {
            for (ProductModel product : products) {
                UUID id = product.getId();
                product.add(linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable UUID id) {
        Optional<ProductModel> product = Optional.ofNullable(productService.findById(id));
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }
        product.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Product List"));
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }

    @GetMapping("/products/price")
    public ResponseEntity<List<ProductModel>> getProductByPriceAsc() {
        List<ProductModel> products = productService.findAllByOrderByValueAsc();
        if (!products.isEmpty()) {
            for (ProductModel product : products) {
                UUID id = product.getId();
                product.add(linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductRecordDTO productRecordDTO) {
        Optional<ProductModel> product = Optional.ofNullable(productService.findById(id));
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }
        ProductModel productModel = product.get();
        BeanUtils.copyProperties(productRecordDTO, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id) {
        Optional<ProductModel> product = Optional.ofNullable(productService.findById(id));
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product Deleted Successfully");
    }

}
