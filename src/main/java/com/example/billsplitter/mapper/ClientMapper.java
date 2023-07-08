package com.example.billsplitter.mapper;

import com.example.billsplitter.dto.ClientDto;
import com.example.billsplitter.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface ClientMapper {

    ClientDto toDto(Client client);

    Client toEntity(ClientDto clientDto);
}
