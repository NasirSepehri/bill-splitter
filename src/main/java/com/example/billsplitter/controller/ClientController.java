package com.example.billsplitter.controller;


import com.example.billsplitter.dto.ClientDto;
import com.example.billsplitter.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(
            summary = "Add a new client",
            description = "Add a new Client by ClientDto field such as a username, first name and etc. The response is clientDto with client id")
    @PostMapping
    public ClientDto add(@RequestBody @Valid ClientDto clientDto) {
        return clientService.addUser(clientDto);
    }

}
