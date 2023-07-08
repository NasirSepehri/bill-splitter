package com.example.billsplitter.repo;

import com.example.billsplitter.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {


    Optional<Client> findByUsername(String username);
}
