package org.example.controllers;

import jakarta.validation.Valid;
import org.example.exception.InvoiceNotFoundException;
import org.example.models.category.CategoryCreateDTO;
import org.example.models.category.CategoryDto;
import org.example.models.category.CategoryResponse;
import org.example.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/category",produces = "application/json")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @PostMapping(value = "", consumes = "multipart/form-data")
    public ResponseEntity<String> saveCategory(@ModelAttribute CategoryCreateDTO categoryModel) {
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
//        }
        try{
            Integer id  = categoryService.saveCategory(categoryModel);
            return ResponseEntity.ok().body(id.toString());
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/get/{page}/{size}/{name}")
    public CategoryResponse getAllInvoices(@PathVariable int page, @PathVariable int size, @PathVariable String name) {
        return categoryService.getCategoryByName(page,size,name);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDto> getInvoiceById(@PathVariable Integer id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return categoryDto != null ? ResponseEntity.ok().body(categoryDto)
                : ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/update",consumes = "multipart/form-data")
    public ResponseEntity<String> updateInvoice(@Valid @ModelAttribute CategoryCreateDTO  categoryModel , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }
        try{
            return categoryService.updateCategory(categoryModel) ? ResponseEntity.ok().body(Integer.toString(categoryModel.getId()))
                    : ResponseEntity.badRequest().body("Invalid invoice id");
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable Integer id) {
        try {
            return  categoryService.deleteCategoryById(id) ?  ResponseEntity.ok().body(null)
                    :  ResponseEntity.badRequest().body("Invalid invoice id");
        } catch (InvoiceNotFoundException | IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
