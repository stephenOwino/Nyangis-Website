package com.stephenowinoh.Avante_garde_backend.Controller;

import com.stephenowinoh.Avante_garde_backend.Dto.AdminLoginDTO;
import com.stephenowinoh.Avante_garde_backend.Service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
        @Autowired
        private AdminLoginService adminLoginService;

        @PostMapping("/register")
        public ResponseEntity<String> register(@RequestBody AdminLoginDTO dto) {
                try {
                        String token = adminLoginService.register(dto);
                        return ResponseEntity.ok(token);
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
                }
        }

        @PostMapping("/login")
        public ResponseEntity<String> login(@RequestParam String phone, @RequestParam String password) {
                try {
                        String token = adminLoginService.login(phone, password);
                        return ResponseEntity.ok(token);
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
                }
        }
}