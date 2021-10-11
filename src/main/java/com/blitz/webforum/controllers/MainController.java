/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blitz.webforum.controllers;

import com.blitz.webforum.interfaces.CategoryInterface;
import com.blitz.webforum.interfaces.PostInterface;
import com.blitz.webforum.models.Post;
import com.blitz.webforum.models.Category;
import com.blitz.webforum.models.User;
import com.blitz.webforum.services.CategoryService;
import com.blitz.webforum.services.PostService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Ghufran Tripa
 */
@Controller
public class MainController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private CategoryService categoryService;

   @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request .getSession(true);
        
        long user_id = (long) session.getAttribute("id");
        
        List<Post> post = postService.findByUserId(user_id);
        
        model.addAttribute("list", postService.getAll());
        model.addAttribute("post", post);
        
        return "index";
    }
    
    //CRUD POST
    @GetMapping("/post/create")
    public String createPost(Model model) {
        
        List<Category> category = categoryService.getAll();
        model.addAttribute("category", category);
        
        Post post = new Post();
        model.addAttribute("post", post);

        return "create";
    }

    @PostMapping("/post/store")
    public String storePost(@ModelAttribute("post") Post post, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        
        User user = new User();
        user.setId((long) session.getAttribute("id"));
        
        post.setUser(user);

        postService.store(post);
        return "redirect:/";
    }

    @GetMapping("/post/{id}/edit")
    public String editPost(@PathVariable(value = "id") long id, Model model) {
        Post post = postService.getById(id);
        List<Category> category = categoryService.getAll();
        model.addAttribute("category", category);
        


        model.addAttribute("post", post);
        return "editpost";
    }

    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable(value = "id") long id) {
        postService.delete(id);
        return "redirect:/";
    }
    //END CRUD POST
    
} 


