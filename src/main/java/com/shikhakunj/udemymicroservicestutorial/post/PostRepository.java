package com.shikhakunj.udemymicroservicestutorial.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
