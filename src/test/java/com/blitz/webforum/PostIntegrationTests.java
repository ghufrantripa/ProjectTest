/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blitz.webforum;

import com.blitz.webforum.models.Category;
import com.blitz.webforum.models.Post;
import com.blitz.webforum.models.User;
import com.blitz.webforum.repositories.PostRepository;
import com.blitz.webforum.repositories.UserRepository;
import com.blitz.webforum.services.PostService;
import com.blitz.webforum.services.UserService;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 *
 * @author Ghufran Tripa
 */
@SpringBootTest

 
public class PostIntegrationTests {

@MockBean
    private PostRepository postRepository;

    @InjectMocks
    @Autowired
    private PostService postService;

    Throwable e = null;
    String expectedMessage = null;
    @Test
    public void createPostTest() throws Exception {

        User user = new User();
        user.setId(1);

        Category cat = new Category();
        cat.setId(1);

        Post post = new Post();
        post.setId(2);
        post.setCategory(cat);
        post.setContentpost("Avanza baru 1m harganya");

        postService.store(post);
    }
    
    @Test
    public void testPostExistsInDbThensuccess() {
        
        
        User user = new User();
        user.setId(1);

        Category cat = new Category();
        cat.setId(1);

        Post post = new Post();
        post.setId(2);
        post.setCategory(cat);
        post.setContentpost("Avanza baru 1m harganya");
        
        List<Post> postList = new ArrayList<>();
        postList.add(post);

        when(postRepository.findAll()).thenReturn(postList);

        List<Post> getPost = postService.getAll();
        assertThat(getPost.size()).isGreaterThan(0);
    }
    
    @Test
    public void testDeletePost() {
        
        
        User user = new User();
        user.setId(1);

        Category cat = new Category();
        cat.setId(1);

        Post post = new Post();
        post.setId(2);
        post.setCategory(cat);
        post.setContentpost("Avanza baru 1m harganya");
        
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        long id = 3;

        postRepository.deleteById(id);
    } 
    
    @Test
    public void testPostWithEmptyContentpost() throws Exception {

        String actualMessage = "Contentpost cannot be null!";

        try {

            User user = new User();
        user.setId(1);

        Category cat = new Category();
        cat.setId(1);

        Post post = new Post();
        post.setId(2);
        post.setCategory(cat);
        post.setContentpost("");

            when(postRepository.save(post))
                    .thenThrow(new RuntimeException(actualMessage));

            postService.store(post);

        } catch (RuntimeException re) {
            e = re;
            expectedMessage = e.getMessage();
        }

        Assertions.assertTrue(e instanceof Exception);
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

}
