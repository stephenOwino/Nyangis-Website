package com.stephenowinoh.Avante_garde_backend.Service;

import com.stephenowinoh.Avante_garde_backend.Dto.ProductDTO;
import com.stephenowinoh.Avante_garde_backend.Entity.Product;
import com.stephenowinoh.Avante_garde_backend.Mapper.ProductMapper;
import com.stephenowinoh.Avante_garde_backend.Repository.ProductRepository;
import com.stephenowinoh.Avante_garde_backend.enums.Category;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import org.springframework.data.domain.Page;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

@Service
public class ProductService {

        private final ProductRepository productRepository;
        private final ProductMapper productMapper;
        private final Validator validator;

        public ProductService(ProductRepository productRepository, ProductMapper productMapper, Validator validator) {
                this.productRepository = productRepository;
                this.productMapper = productMapper;
                this.validator = validator;
        }

        @Transactional
        public ProductDTO createProduct(ProductDTO dto) {
                validateDTO(dto);
                dto.setPostedAt(System.currentTimeMillis());
                dto.setViews(0);
                Product product = productMapper.toEntity(dto);
                Product savedProduct = productRepository.save(product);
                return productMapper.toDTO(savedProduct);
        }

        public Page<ProductDTO> getAllProducts(Pageable pageable) {
                return productRepository.findAll(pageable)
                        .map(productMapper::toDTO);
        }

        public Page<ProductDTO> getProductsByCategory(String category, Pageable pageable) {
                if (category == null || category.trim().isEmpty()) {
                        throw new IllegalArgumentException("Category cannot be null or empty");
                }

                Category categoryEnum;
                try {
                        categoryEnum = Category.valueOf(category.toUpperCase());
                } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid category: " + category + ". Valid categories are: " +
                                Arrays.toString(Category.values()));
                }

                return productRepository.findByCategory(String.valueOf(categoryEnum), pageable)
                        .map(productMapper::toDTO);
        }

        @Transactional
        public ProductDTO getProductById(Long id) {
                Product product = productRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
                product.setViews(product.getViews() + 1);
                Product updatedProduct = productRepository.save(product);
                return productMapper.toDTO(updatedProduct);
        }

        @Transactional
        public ProductDTO updateProduct(Long id, ProductDTO dto) {
                validateDTO(dto);
                Product product = productRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

                product.setDescription(dto.getDescription());
                product.setStatus(dto.getStatus());
                product.setCategory(dto.getCategory());
                product.setPrice(dto.getPrice());
                product.setLocation(dto.getLocation());
                product.setType(dto.getType());
                product.setCondition(dto.getCondition());

                if (dto.getImageUrl() != null) {
                        product.setImageUrl(dto.getImageUrl());
                }
                if (dto.getPostedAt() != null) {
                        product.setPostedAt(dto.getPostedAt());
                }
                if (dto.getViews() != null) {
                        product.setViews(dto.getViews());
                }

                Product updatedProduct = productRepository.save(product);
                return productMapper.toDTO(updatedProduct);
        }

        @Transactional
        public void deleteProduct(Long id) {
                if (!productRepository.existsById(id)) {
                        throw new IllegalArgumentException("Product not found with id: " + id);
                }
                productRepository.deleteById(id);
        }

        private void validateDTO(ProductDTO dto) {
                Set<ConstraintViolation<ProductDTO>> violations = validator.validate(dto);
                if (!violations.isEmpty()) {
                        throw new ConstraintViolationException(violations);
                }
        }
}
