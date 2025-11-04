package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
    
}
