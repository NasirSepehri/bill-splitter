package com.example.billsplitter.repo;

import com.example.billsplitter.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByClientUsernameOrderByIdAsc(String username);

    @Query(value = "delete from event_member where event_id= :eventId and username=:memberUsername", nativeQuery = true)
    void deleteMemberByEventIdAndMemberUsername(Long eventId, String memberUsername);


}
