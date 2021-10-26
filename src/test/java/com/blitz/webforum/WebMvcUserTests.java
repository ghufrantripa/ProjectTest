/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blitz.webforum;

import com.blitz.webforum.models.User;
import static com.fasterxml.jackson.databind.util.ClassUtil.name;
import net.bytebuddy.utility.RandomString;
import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *
 * @author Ghufran Tripa
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebMvcUserTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterWithRightCredentials() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());

        String email = RandomString.make(10).toLowerCase() + "@mail.com";
        String password = RandomString.make(10).toLowerCase();
        
        User user = new User();
        user.setEmail(email);
        user.setName("lala");
        user.setPassword(password);
        user.setAdress("jalan");

        mockMvc.perform(post("/register")
                .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }
    
    @Test
    public void testRegisterWithoutEmail() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());

        String email = RandomString.make(20).toLowerCase() + "@mail.com";
        String password = RandomString.make(20).toLowerCase();

        User user = new User();
        user.setEmail("");
        user.setName("Siapa");
        user.setPassword(password);
        user.setAdress("jalan");

        mockMvc.perform(post("/register")
                .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers
                        .flash().attributeExists("danger")
                )
                .andExpect(
                        MockMvcResultMatchers
                                .flash()
                                .attribute("danger", "Email cannot be null!")
                );
    }
    
    @Test
    public void testRegisterWithoutName() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());

        String email = RandomString.make(10).toLowerCase() + "@mail.com";
        String password = RandomString.make(10).toLowerCase();
        
        String adress = "";
        
        User user = new User();
        user.setEmail(email);
        user.setName("");
        user.setPassword(password);
        user.setAdress(adress);

        mockMvc.perform(post("/register")
                .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers
                        .flash().attributeExists("danger")
                )
                .andExpect(
                        MockMvcResultMatchers
                                .flash()
                                .attribute("danger", "Name cannot be null!")
                );
    }
    
    @Test
    public void testRegisterWithoutPassword() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());

        String email = RandomString.make(10).toLowerCase() + "@mail.com";
        String password = RandomString.make(10).toLowerCase();
        
        String name = "lala";
        String adress = "jalan";
        
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword("");
        user.setAdress(adress);

        mockMvc.perform(post("/register")
                .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers
                        .flash().attributeExists("danger")
                )
                .andExpect(
                        MockMvcResultMatchers
                                .flash()
                                .attribute("danger", "Password cannot be null!")
                );
    }
    
    @Test
    public void testRegisterWithoutAdress() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());

        String email = RandomString.make(10).toLowerCase() + "@mail.com";
        String password = RandomString.make(10).toLowerCase();
        
        String name = "lala";
        
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setAdress("");

        mockMvc.perform(post("/register")
                .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers
                        .flash().attributeExists("danger")
                )
                .andExpect(
                        MockMvcResultMatchers
                                .flash()
                                .attribute("danger", "Adress cannot be null!")
                );
    }

    @Test
    public void testRegisterThenLogin() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());
        
        String email = RandomString.make(10).toLowerCase() + "@mail.com";
        String password = RandomString.make(10).toLowerCase();
       
        User user = new User();
        user.setEmail(email);
        user.setName("lala");
        user.setPassword(password);
        user.setAdress("jalan");

        mockMvc.perform(post("/register")
                .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());

        
        User userLogin = new User();
        userLogin.setEmail(email);
        userLogin.setPassword(password);
        
        mockMvc.perform(post("/login")
                .flashAttr("user", userLogin))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"))
                .andDo(print());
    }
}