package com.example.billsplitter.repo;

import com.example.billsplitter.entity.Event;
import com.example.billsplitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByUser(User user);
}
