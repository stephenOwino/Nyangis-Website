package com.stephenowinoh.Avante_garde_backend.Repository;

import com.stephenowinoh.Avante_garde_backend.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
        Page<Product> findByCategory(String category, Pageable pageable);
}