package com.stephenowinoh.Avante_garde_backend.Service;

import com.stephenowinoh.Avante_garde_backend.Dto.AdminLoginDTO;
import com.stephenowinoh.Avante_garde_backend.Entity.AdminLogin;
import com.stephenowinoh.Avante_garde_backend.Mapper.AdminLoginMapper;
import com.stephenowinoh.Avante_garde_backend.Repository.AdminLoginRepository;
import com.stephenowinoh.Avante_garde_backend.Security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminLoginService {
        @Autowired
        private AdminLoginRepository repository;

        @Autowired
        private AdminLoginMapper mapper;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private JwtService jwtService;

        private final Map<String, String> ALLOWED_USERS = new HashMap<>() {{
                put("0719483910", "berilanyango52@gmail.com");
                put("0704760842", "beril.owino@outlook.com");
        }};

        public String register(AdminLoginDTO dto) {
                String phone = dto.getPhone();
                String email = dto.getEmail();

                if (!ALLOWED_USERS.containsKey(phone) || !ALLOWED_USERS.get(phone).equals(email)) {
                        throw new IllegalArgumentException("You are not authorized to use this system.");
                }

                if (repository.findByPhone(phone) != null) {
                        throw new IllegalArgumentException("User with this phone number already exists.");
                }

                AdminLogin entity = mapper.toEntity(dto);
                entity.setPassword(passwordEncoder.encode(dto.getPassword()));
                repository.save(entity);
                return jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                        phone, entity.getPassword(), new java.util.ArrayList<>()));
        }

        public String login(String phone, String password) {
                AdminLogin entity = repository.findByPhone(phone);
                if (entity == null || !passwordEncoder.matches(password, entity.getPassword())) {
                        throw new IllegalArgumentException("Invalid credentials.");
                }

                if (!ALLOWED_USERS.get(phone).equals(entity.getEmail())) {
                        throw new IllegalArgumentException("You are not authorized to use this system.");
                }

                return jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                        phone, entity.getPassword(), new java.util.ArrayList<>()));
        }

        public boolean isAdminExists() {
                return repository.count() > 0;
        }

        public boolean isValidAdmin(String phone) {
                return ALLOWED_USERS.containsKey(phone);
        }

        public boolean isBanned(String phone) {
                return false; // Implement banning logic if needed
        }
}