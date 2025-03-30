package com.stephenowinoh.Avante_garde_backend.Controller;

import com.stephenowinoh.Avante_garde_backend.Dto.ProductDTO;
import com.stephenowinoh.Avante_garde_backend.Service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

        private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

        @Value("${file.upload-dir:/home/stephen/uploads}")
        private String uploadDir;

        private final ProductService productService;

        public ProductController(ProductService productService) {
                this.productService = productService;
        }

        @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<String> uploadImage(@RequestPart("image") MultipartFile image) {
                try {
                        if (image == null || image.isEmpty()) {
                                logger.warn("Upload attempt with null or empty image");
                                throw new IllegalArgumentException("Image file is required.");
                        }
                        if (!image.getContentType().startsWith("image/")) {
                                logger.warn("Upload attempt with non-image file: {}", image.getContentType());
                                throw new IllegalArgumentException("Only image files are allowed.");
                        }
                        if (image.getSize() > 5 * 1024 * 1024) {
                                logger.warn("Image size exceeds 5MB: {}", image.getSize());
                                throw new IllegalArgumentException("File size exceeds 5MB limit.");
                        }

                        String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
                        Path filePath = Paths.get(uploadDir, fileName).normalize();
                        Files.createDirectories(filePath.getParent());
                        Files.write(filePath, image.getBytes());
                        logger.info("Image uploaded successfully to: {}", filePath);

                        String imageUrl = "/api/products/uploads/" + fileName;
                        return ResponseEntity.ok(imageUrl);
                } catch (IOException e) {
                        logger.error("Failed to upload image: {}", image.getOriginalFilename(), e);
                        throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
                }
        }

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO dto) {
                logger.info("Creating product with data: {}", dto);
                dto.setPostedAt(System.currentTimeMillis()); // Set creation timestamp
                dto.setViews(0); // Initialize views
                ProductDTO createdProduct = productService.createProduct(dto);
                logger.info("Product created successfully: {}", createdProduct);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }

        @GetMapping
        public ResponseEntity<Page<ProductDTO>> getAllProducts(@PageableDefault(size = 10) Pageable pageable) {
                logger.info("Fetching all products, page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
                return ResponseEntity.ok(productService.getAllProducts(pageable));
        }

        @GetMapping("/category/{category}")
        public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
                @PathVariable String category,
                @PageableDefault(size = 10) Pageable pageable) {
                logger.info("Fetching products by category: {}, page: {}, size: {}", category, pageable.getPageNumber(), pageable.getPageSize());
                return ResponseEntity.ok(productService.getProductsByCategory(category, pageable));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
                logger.info("Fetching product with id: {}", id);
                ProductDTO product = productService.getProductById(id);
                product.setViews(product.getViews() != null ? product.getViews() + 1 : 1); // Increment views
                ProductDTO updatedProduct = productService.updateProduct(id, product); // Save updated views
                logger.info("Product fetched and views updated: {}", updatedProduct);
                return ResponseEntity.ok(updatedProduct);
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ProductDTO> updateProduct(
                @PathVariable Long id,
                @RequestBody @Valid ProductDTO dto) {
                logger.info("Updating product with id: {}", id);
                ProductDTO updatedProduct = productService.updateProduct(id, dto);
                logger.info("Product updated successfully: {}", updatedProduct);
                return ResponseEntity.ok(updatedProduct);
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
                logger.info("Deleting product with id: {}", id);
                productService.deleteProduct(id);
                logger.info("Product deleted successfully: id {}", id);
                return ResponseEntity.noContent().build();
        }

        @GetMapping("/uploads/{filename:.+}")
        public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
                try {
                        Path filePath = Paths.get(uploadDir, filename).normalize();
                        logger.info("Serving image from: {}", filePath);
                        Resource resource = new UrlResource(filePath.toUri());

                        if (!resource.exists() || !resource.isReadable()) {
                                logger.warn("Image not found or unreadable: {}", filePath);
                                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                        }

                        String contentType = Files.probeContentType(filePath);
                        if (contentType == null) {
                                contentType = filename.endsWith(".png") ? "image/png" : "image/jpeg";
                                logger.debug("Defaulting content type to: {}", contentType);
                        }

                        return ResponseEntity.ok()
                                .contentType(MediaType.parseMediaType(contentType))
                                .body(resource);
                } catch (IOException e) {
                        logger.error("Failed to serve image: {}", filename, e);
                        throw new RuntimeException("Failed to serve image: " + e.getMessage(), e);
                }
        }
}