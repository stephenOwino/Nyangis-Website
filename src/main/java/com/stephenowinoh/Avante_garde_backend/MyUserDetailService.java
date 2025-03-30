package com.stephenowinoh.Avante_garde_backend;

import com.stephenowinoh.Avante_garde_backend.Entity.AdminLogin;
import com.stephenowinoh.Avante_garde_backend.Repository.AdminLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
        @Autowired
        private AdminLoginRepository repository;

        @Override
        public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
                AdminLogin admin = repository.findByPhone(phone);
                if (admin == null) {
                        throw new UsernameNotFoundException("User not found with phone: " + phone);
                }
                return User.withUsername(admin.getPhone())
                        .password(admin.getPassword())
                        .roles("ADMIN")
                        .build();
        }
}
