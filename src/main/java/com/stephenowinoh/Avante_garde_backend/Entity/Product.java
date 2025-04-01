package com.stephenowinoh.Avante_garde_backend.Entity;

import com.stephenowinoh.Avante_garde_backend.enums.Category;
import com.stephenowinoh.Avante_garde_backend.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "imageurl") // Match the actual column name in the database
        private String imageUrl;

        @Column(nullable = false)
        private String description;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Status status;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Category category;

        @Column(nullable = false)
        private Integer price;

        @Column(nullable = false)
        private String location;

        @Column(name = "posted_at")
        private Long postedAt;

        @Column(nullable = false)
        private Integer views;

        @Column(nullable = false)
        private String type;

        @Column(nullable = false)
        private String condition;


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
                return price;
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
                return postedAt;
        }

        public void setPostedAt(Long postedAt) {
                this.postedAt = postedAt;
        }

        public Integer getViews() {
                return views;
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
}