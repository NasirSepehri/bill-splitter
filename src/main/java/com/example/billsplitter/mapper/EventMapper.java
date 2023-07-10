package com.example.billsplitter.mapper;


import com.example.billsplitter.dto.client.ClientDto;
import com.example.billsplitter.dto.event.EventDto;
import com.example.billsplitter.entity.Client;
import com.example.billsplitter.entity.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CostMapper.class})
public interface EventMapper {


    default Client toClient(ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setUsername(clientDto.getUsername());
        return client;
    }

    default ClientDto toClientDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setUsername(client.getUsername());
        return clientDto;
    }

    EventDto toDto(Event event);

    Event toEntity(EventDto eventDto);
}
