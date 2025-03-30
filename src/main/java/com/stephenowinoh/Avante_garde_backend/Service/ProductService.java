package com.stephenowinoh.Avante_garde_backend.Service;

import com.stephenowinoh.Avante_garde_backend.Dto.ProductDTO;
import com.stephenowinoh.Avante_garde_backend.Entity.Product;
import com.stephenowinoh.Avante_garde_backend.Mapper.ProductMapper;
import com.stephenowinoh.Avante_garde_backend.Repository.ProductRepository;
import com.stephenowinoh.Avante_garde_backend.enums.Category;
import com.stephenowinoh.Avante_garde_backend.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

        private final ProductRepository productRepository;
        private final ProductMapper productMapper;

        public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
                this.productRepository = productRepository;
                this.productMapper = productMapper;
        }

        @Transactional
        public ProductDTO createProduct(ProductDTO dto) {
                // Set creation timestamp and initialize views
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
                Category categoryEnum;
                try {
                        categoryEnum = Category.valueOf(category.toUpperCase());
                } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid category: " + category);
                }
                return productRepository.findByCategory(categoryEnum.name(), pageable)
                        .map(productMapper::toDTO);
        }

        public ProductDTO getProductById(Long id) {
                Product product = productRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
                // Increment views
                product.setViews(product.getViews() != null ? product.getViews() + 1 : 1);
                Product updatedProduct = productRepository.save(product);
                return productMapper.toDTO(updatedProduct);
        }

        @Transactional
        public ProductDTO updateProduct(Long id, ProductDTO dto) {
                Product product = productRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

                // Update fields if provided in DTO, otherwise retain existing values
                product.setDescription(dto.getDescription() != null ? dto.getDescription() : product.getDescription());
                product.setStatus(dto.getStatus() != null ? Status.valueOf(dto.getStatus()) : product.getStatus());
                product.setCategory(dto.getCategory() != null ? Category.valueOf(dto.getCategory()) : product.getCategory());
                product.setImageUrl(dto.getImageUrl() != null ? dto.getImageUrl() : product.getImageUrl());
                product.setPrice(dto.getPrice() != null ? dto.getPrice() : product.getPrice());
                product.setLocation(dto.getLocation() != null ? dto.getLocation() : product.getLocation());
                product.setType(dto.getType() != null ? dto.getType() : product.getType());
                product.setCondition(dto.getCondition() != null ? dto.getCondition() : product.getCondition());
                // postedAt and views are managed by the system, not updated via DTO
                product.setPostedAt(dto.getPostedAt() != null ? dto.getPostedAt() : product.getPostedAt());
                product.setViews(dto.getViews() != null ? dto.getViews() : product.getViews());

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
}
