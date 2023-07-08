package com.example.billsplitter.service.impl;

import com.example.billsplitter.dto.ClientDto;
import com.example.billsplitter.entity.Client;
import com.example.billsplitter.mapper.ClientMapper;
import com.example.billsplitter.repo.ClientRepository;
import com.example.billsplitter.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDto addUser(ClientDto clientDto) {
        Client savedClient = clientRepository.save(clientMapper.toEntity(clientDto));
        return clientMapper.toDto(savedClient);
    }
}
