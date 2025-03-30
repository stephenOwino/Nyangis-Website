package com.stephenowinoh.Avante_garde_backend.Dto;

import jakarta.validation.constraints.NotNull;

public class ProductDTO {
        private Long id;
        private String imageUrl;

        @NotNull(message = "Description is required")
        private String description;

        @NotNull(message = "Status is required")
        private String status;

        @NotNull(message = "Category is required")
        private String category;

        @NotNull(message = "Price is required")
        private Integer price;

        @NotNull(message = "Location is required")
        private String location;

        private Long postedAt; // Timestamp in milliseconds

        private Integer views; // View count

        @NotNull(message = "Type is required")
        private String type;

        @NotNull(message = "Condition is required")
        private String condition;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public Integer getPrice() { return price; }
        public void setPrice(Integer price) { this.price = price; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        public Long getPostedAt() { return postedAt; }
        public void setPostedAt(Long postedAt) { this.postedAt = postedAt; }
        public Integer getViews() { return views; }
        public void setViews(Integer views) { this.views = views; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getCondition() { return condition; }
        public void setCondition(String condition) { this.condition = condition; }

        @Override
        public String toString() {
                return "ProductDTO{id=" + id + ", description='" + description + "', status='" + status + "', category='" + category + "', imageUrl='" + imageUrl + "', price=" + price + ", location='" + location + "', postedAt=" + postedAt + ", views=" + views + ", type='" + type + "', condition='" + condition + "'}";
        }
}