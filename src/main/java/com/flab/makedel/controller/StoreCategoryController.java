package com.flab.makedel.controller;

import com.flab.makedel.dto.StoreCategoryDTO;
import com.flab.makedel.service.StoreCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores/categories")
@RequiredArgsConstructor
public class StoreCategoryController {

    private final StoreCategoryService storeCategoryService;

    @GetMapping
    public ResponseEntity<List<StoreCategoryDTO>> loadStoreCategory() {
        List<StoreCategoryDTO> categoryList = storeCategoryService.getAllStoreCategory();
        return ResponseEntity.ok().body(categoryList);
    }

}
