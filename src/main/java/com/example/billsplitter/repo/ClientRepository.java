package com.example.billsplitter.repo;

import com.example.billsplitter.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {


    Optional<Client> findByUsername(String username);
}
