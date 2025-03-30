package com.stephenowinoh.Avante_garde_backend.Dto;

public class AdminLoginDTO {
        private Long id;
        private String phone;
        private String email;
        private String password;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
}
