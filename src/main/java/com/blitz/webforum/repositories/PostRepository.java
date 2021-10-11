/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blitz.webforum.repositories;

import com.blitz.webforum.models.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ghufran Tripa
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findByUserId(long user_id);
}
