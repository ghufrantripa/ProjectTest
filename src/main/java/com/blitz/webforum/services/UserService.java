/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blitz.webforum.services;

import com.blitz.webforum.interfaces.UserInterface;
import com.blitz.webforum.models.User;
import com.blitz.webforum.repositories.UserRepository;
import java.math.BigInteger;
import java.security.MessageDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ghufran Tripa
 */
@Service
public class UserService implements UserInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void register(User user) throws Exception {
        String hashed = this.hash(user.getPassword());
        user.setPassword(hashed);
        
        this.userRepository.save(user);
    }

    @Override
    public User auth(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        if (!this.match(user.getPassword(), password)) {
            return null;
        }

       return user;
    }

    private String hash(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDiggest = md.digest(password.getBytes());

        BigInteger no = new BigInteger(1, messageDiggest);

        String hashText = no.toString(16);
        while (hashText.length() < 32) {
            hashText = "0" + hashText;
        }

        return hashText;
    }

    private boolean match(String password, String rawPassword)
            throws Exception {
        rawPassword = this.hash(rawPassword);
        return password.equals(rawPassword);
    }

    
}
