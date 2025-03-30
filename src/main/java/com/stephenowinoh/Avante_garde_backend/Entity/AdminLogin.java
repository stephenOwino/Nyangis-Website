package com.stephenowinoh.Avante_garde_backend.Entity;



import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "admin_logins")
public class AdminLogin {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String phone;

        @Column(unique = true, nullable = false)
        private String email;

        @Column(nullable = false)
        private String password;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}