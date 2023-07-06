package com.example.billsplitter.repo;

import com.example.billsplitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
