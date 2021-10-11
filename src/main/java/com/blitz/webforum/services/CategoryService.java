/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blitz.webforum.services;

import com.blitz.webforum.interfaces.CategoryInterface;
import com.blitz.webforum.models.Category;
import com.blitz.webforum.repositories.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ghufran Tripa
 */
@Service
public class CategoryService implements CategoryInterface{

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    
}
