package com.stephenowinoh.Avante_garde_backend.Mapper;



import com.stephenowinoh.Avante_garde_backend.Dto.AdminLoginDTO;
import com.stephenowinoh.Avante_garde_backend.Entity.AdminLogin;
import org.springframework.stereotype.Component;

@Component
public class AdminLoginMapper {
        public AdminLoginDTO toDTO(AdminLogin entity) {
                AdminLoginDTO dto = new AdminLoginDTO();
                dto.setId(entity.getId());
                dto.setPhone(entity.getPhone());
                dto.setEmail(entity.getEmail());
                dto.setPassword(entity.getPassword());
                return dto;
        }

        public AdminLogin toEntity(AdminLoginDTO dto) {
                AdminLogin entity = new AdminLogin();
                entity.setId(dto.getId());
                entity.setPhone(dto.getPhone());
                entity.setEmail(dto.getEmail());
                entity.setPassword(dto.getPassword());
                return entity;
        }
}
