package com.stephenowinoh.Avante_garde_backend.Mapper;

import com.stephenowinoh.Avante_garde_backend.Dto.ProductDTO;
import com.stephenowinoh.Avante_garde_backend.Entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

        public Product toEntity(ProductDTO dto) {
                Product product = new Product();
                product.setId(dto.getId());
                product.setImageUrl(dto.getImageUrl());
                product.setDescription(dto.getDescription());
                product.setStatus(dto.getStatus());
                product.setCategory(dto.getCategory());
                product.setPrice(dto.getPrice());
                product.setLocation(dto.getLocation());
                product.setPostedAt(dto.getPostedAt());
                product.setViews(dto.getViews());
                product.setType(dto.getType());
                product.setCondition(dto.getCondition());
                return product;
        }

        public ProductDTO toDTO(Product product) {
                ProductDTO dto = new ProductDTO();
                dto.setId(product.getId());
                dto.setImageUrl(product.getImageUrl());
                dto.setDescription(product.getDescription());
                dto.setStatus(product.getStatus());
                dto.setCategory(product.getCategory());
                dto.setPrice(product.getPrice());
                dto.setLocation(product.getLocation());
                dto.setPostedAt(product.getPostedAt());
                dto.setViews(product.getViews());
                dto.setType(product.getType());
                dto.setCondition(product.getCondition());
                return dto;
        }
}