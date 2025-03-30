package com.stephenowinoh.Avante_garde_backend.Repository;



import com.stephenowinoh.Avante_garde_backend.Entity.AdminLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminLoginRepository extends JpaRepository<AdminLogin, Long> {
        AdminLogin findByPhone(String phone);
}
