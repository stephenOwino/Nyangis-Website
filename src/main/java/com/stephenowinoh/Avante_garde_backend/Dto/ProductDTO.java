package com.stephenowinoh.Avante_garde_backend.Dto;

import com.stephenowinoh.Avante_garde_backend.enums.Category;
import com.stephenowinoh.Avante_garde_backend.enums.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {
        private Long id;

        private String imageUrl;

        @NotNull(message = "Description is required")
        private String description;

        @NotNull(message = "Status is required")
        private Status status;

        @NotNull(message = "Category is required")
        private Category category;

        @NotNull(message = "Price is required")
        @Min(value = 0, message = "Price must be non-negative")
        private Integer price;

        @NotNull(message = "Location is required")
        private String location;

        private Long postedAt;

        @Min(value = 0, message = "Views must be non-negative")
        private Integer views;

        @NotNull(message = "Type is required")
        private String type;

        @NotNull(message = "Condition is required")
        private String condition;

        // Manual getters and setters
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getImageUrl() {
                return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public Status getStatus() {
                return status;
        }

        public void setStatus(Status status) {
                this.status = status;
        }

        public Category getCategory() {
                return category;
        }

        public void setCategory(Category category) {
                this.category = category;
        }

        public Integer getPrice() {
                return price != null ? price : 0; // Fallback to 0 if price is null
        }

        public void setPrice(Integer price) {
                this.price = price;
        }

        public String getLocation() {
                return location;
        }

        public void setLocation(String location) {
                this.location = location;
        }

        public Long getPostedAt() {
                return postedAt != null ? postedAt : 0L; // Fallback to 0 if postedAt is null
        }

        public void setPostedAt(Long postedAt) {
                this.postedAt = postedAt;
        }

        public Integer getViews() {
                return views != null ? views : 0; // Fallback to 0 if views is null
        }

        public void setViews(Integer views) {
                this.views = views;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public String getCondition() {
                return condition;
        }

        public void setCondition(String condition) {
                this.condition = condition;
        }

        // Manual toString method for logging/debugging
        @Override
        public String toString() {
                return "ProductDTO{" +
                        "id=" + id +
                        ", imageUrl='" + imageUrl + '\'' +
                        ", description='" + description + '\'' +
                        ", status=" + status +
                        ", category=" + category +
                        ", price=" + price +
                        ", location='" + location + '\'' +
                        ", postedAt=" + postedAt +
                        ", views=" + views +
                        ", type='" + type + '\'' +
                        ", condition='" + condition + '\'' +
                        '}';
        }
}