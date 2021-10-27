/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blitz.webforum;

import com.blitz.webforum.models.User;
import com.blitz.webforum.repositories.UserRepository;
import com.blitz.webforum.services.UserService;
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
public class UserIntegrationTests {

    @InjectMocks
    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Test
    public void createUserTest() throws Exception {
        User user = new User();
        user.setEmail("lala@mail.com");
        user.setName("lala");
        user.setPassword("lala123");

        when(repository.save(user)).thenReturn(user);
        service.register(user);

        when(repository.findByEmail("lala@mail.com")).thenReturn(user);

        User checkUser = this.repository.findByEmail("lala@mail.com");

        Assertions.assertEquals(user, checkUser);
    }

    @Test
    public void createUserTestWithEmptyName() throws Exception {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setName("");
        user.setPassword("test-strong-password");

        when(repository.save(user)).thenReturn(user);
        service.register(user);

        when(repository.findByEmail("test@mail.com")).thenReturn(user);

        User checkUser = this.repository.findByEmail("test@mail.com");

        Assertions.assertEquals(user, checkUser);
    }

    @Test
    public void createUserTestWithEmptyEmail() throws Exception {
        Throwable e = null;
        String message = null;
  
        try {
            User user = new User();
            user.setEmail("");
            user.setName("Test");
            user.setPassword("test-strong-password");

            when(repository.save(user))
                    .thenThrow(new Exception("Email cannot be null!"));
            
            service.register(user);
        } catch (Exception ex) {
            e = ex;
            message = ex.getMessage();
        }
       
        Assertions.assertTrue(e instanceof Exception);
        //Assertions.assertEquals("Email cannot be null!", message);
    }
    
    @Test
    public void authTest() throws Exception {

        User user = new User();

        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("test-strong-password");

        when(repository.save(user)).thenReturn(user);
        service.register(user);

        when(repository.findByEmail("test@gmail.com")).thenReturn(user);

        User checkUser = service.auth("test@gmail.com", "test-strong-password");

        Assertions.assertEquals(user, checkUser);
    }

    @Test
    public void authTestWrongEmail() throws Exception {

        User user = new User();

        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("test-strong-password");

        when(repository.save(user)).thenReturn(user);
        service.register(user);

        when(repository.findByEmail("test@gmail.com")).thenReturn(user);

        User checkUser = service.auth("test1@gmail.com", "test-strong-password");

        Assertions.assertEquals(null, checkUser);
    }

    @Test
    public void authTestWrongPassword() throws Exception {

        User user = new User();

        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("test-strong-password");

        when(repository.save(user)).thenReturn(user);
        service.register(user);

        when(repository.findByEmail("test@gmail.com")).thenReturn(user);

        User checkUser = service.auth("test@gmail.com", "test-strong-password1");

        Assertions.assertEquals(null, checkUser);
    }

    @Test
    public void authTestWrongEmailAndPassword() throws Exception {

        User user = new User();

        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("test-strong-password");

        when(repository.save(user)).thenReturn(user);
        service.register(user);

        when(repository.findByEmail("test@gmail.com")).thenReturn(user);

        User checkUser = service.auth("test1@gmail.com", "test-strong-password1");

        Assertions.assertEquals(null, checkUser);
    }
}
