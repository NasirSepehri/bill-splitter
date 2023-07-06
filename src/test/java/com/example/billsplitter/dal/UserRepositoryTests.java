package com.example.billsplitter.dal;

//import com.example.billsplitter.config.interfaces.ContainerTest;
import com.example.billsplitter.entity.Cost;
import com.example.billsplitter.entity.Event;
import com.example.billsplitter.entity.User;
import com.example.billsplitter.repo.UserRepository;
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
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;


    @Test
    void test_addUser(){

        User user = new User();

        user.setUsername("Username");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmail("test@gmail.com");


        Event event1 = new Event();
        event1.setName("Event1");
        event1.setUser(new User());
        event1.setEventMembers(List.of("User1", "User2", "User3"));

        Cost cost1 = new Cost();
        cost1.setCostAmount(60.0F);
        cost1.setCostDescription("Cost1");
        cost1.setSplitBetween(List.of("User1", "User2"));
        cost1.setPaidBy("User3");
        cost1.setEvent(event1);

        Cost cost2 = new Cost();
        cost2.setCostAmount(70.0F);
        cost2.setCostDescription("Cost2");
        cost2.setSplitBetween(List.of("User1", "User2", "User3"));
        cost2.setPaidBy("User3");
        cost2.setEvent(event1);

        event1.getCosts().addAll(List.of(cost1, cost2));

        user.setEvents(List.of(event1));


        User savedUser = userRepository.save(user);
        User findUser = userRepository.findById(savedUser.getId()).orElseThrow();

        assertEquals("Username", findUser.getUsername());
        assertEquals("FirstName", findUser.getFirstName());
        assertEquals("LastName", findUser.getLastName());
        assertEquals("test@gmail.com", findUser.getEmail());

        assertEquals("Event1", findUser.getEvents().get(0).getName());
        assertTrue(findUser.getEvents().get(0).getEventMembers().containsAll(List.of("User1", "User2", "User3")));


        Event savedEvent = findUser.getEvents().get(0);
        assertEquals("Cost1", savedEvent.getCosts().get(0).getCostDescription());
        assertEquals("Cost2", savedEvent.getCosts().get(1).getCostDescription());

        assertTrue(savedEvent.getCosts().get(0).getSplitBetween().containsAll(List.of("User1", "User2")));
        assertTrue(savedEvent.getCosts().get(1).getSplitBetween().containsAll(List.of("User1", "User2","User3")));

    }
}
