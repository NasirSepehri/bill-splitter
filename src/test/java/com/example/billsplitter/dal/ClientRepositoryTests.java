package com.example.billsplitter.dal;

import com.example.billsplitter.entity.Client;
import com.example.billsplitter.entity.Cost;
import com.example.billsplitter.entity.Event;
import com.example.billsplitter.enums.ClientRolesEnum;
import com.example.billsplitter.repo.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTests {

    @Autowired
    ClientRepository clientRepository;


    @Test
    void test_addClient() {

        Client client = new Client();

        client.setUsername("Username");
        client.setFirstName("FirstName");
        client.setUsername("Username");
        client.setLastName("LastName");
        client.setPassword("12345678");
        client.setEmail("test@gmail.com");
        client.setRole(ClientRolesEnum.ROLE_USER);

        Event event1 = new Event();
        event1.setName("Event1");
        event1.setClient(new Client());
        event1.setEventMembers(List.of("Client1", "Client2", "Client3"));

        Cost cost1 = new Cost();
        cost1.setCostAmount(60.0F);
        cost1.setCostDescription("Cost1");
        cost1.setSplitBetween(List.of("Client1", "Client2"));
        cost1.setPaidBy("Client3");
        cost1.setEvent(event1);

        Cost cost2 = new Cost();
        cost2.setCostAmount(70.0F);
        cost2.setCostDescription("Cost2");
        cost2.setSplitBetween(List.of("Client1", "Client2", "Client3"));
        cost2.setPaidBy("Client3");
        cost2.setEvent(event1);

        event1.getCosts().addAll(List.of(cost1, cost2));

        client.setEvents(List.of(event1));

        event1.setClient(client);
        Client savedClient = clientRepository.save(client);
        Client findClient = clientRepository.findById(savedClient.getId()).orElseThrow();

        assertEquals("Username", findClient.getUsername());
        assertEquals("FirstName", findClient.getFirstName());
        assertEquals("LastName", findClient.getLastName());
        assertEquals("test@gmail.com", findClient.getEmail());

        assertEquals("Event1", findClient.getEvents().get(0).getName());
        assertTrue(findClient.getEvents().get(0).getEventMembers().containsAll(List.of("Client1", "Client2", "Client3")));


        Event savedEvent = findClient.getEvents().get(0);
        assertEquals("Cost1", savedEvent.getCosts().get(0).getCostDescription());
        assertEquals("Cost2", savedEvent.getCosts().get(1).getCostDescription());

        assertTrue(savedEvent.getCosts().get(0).getSplitBetween().containsAll(List.of("Client1", "Client2")));
        assertTrue(savedEvent.getCosts().get(1).getSplitBetween().containsAll(List.of("Client1", "Client2", "Client3")));

    }
}
