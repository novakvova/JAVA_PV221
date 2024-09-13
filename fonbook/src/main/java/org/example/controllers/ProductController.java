package org.example.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.exception.InvoiceNotFoundException;
import org.example.models.product.*;
import org.example.service.impl.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public ResponseEntity<List<ProductItemDTO>> index() {
        return new ResponseEntity<>(productService.get(), HttpStatus.OK);
    }
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<String> saveProduct(@Valid @ModelAttribute ProductCreationModel productModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }
        try{
            Long id  = productService.saveProduct(productModel);
            return ResponseEntity.ok().body(id.toString());
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @GetMapping("/get/{page}/{size}")
    public PaginationResponse<ProductDto> getProducts(@PathVariable int page, @PathVariable int size) {
        return productService.getProducts(page,size);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        try {
            ProductDto productDto = productService.getProductById(id);
            return productDto != null ? ResponseEntity.ok().body(productDto)
                    : ResponseEntity.notFound().build();
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update",consumes = "multipart/form-data")
    public ResponseEntity<String> updateProduct(@Valid @ModelAttribute ProductUpdateModel productModel , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }
        try{
            return productService.updateProduct(productModel) ? ResponseEntity.ok().body(productModel.getId().toString())
                    : ResponseEntity.badRequest().body("Invalid invoice id");
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            return  productService.deleteProductById(id) ?  ResponseEntity.ok().body(null)
                    :  ResponseEntity.badRequest().body("Invalid invoice id");
        } catch (InvoiceNotFoundException | IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
