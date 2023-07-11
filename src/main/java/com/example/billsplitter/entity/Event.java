package com.example.billsplitter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "event")
@Setter
@Getter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    @SequenceGenerator(name = "event_seq", sequenceName = "event_seq", allocationSize = 1)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @CollectionTable(name = "event_member", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "username", length = 100)
    @ElementCollection
    private List<Member> eventMembers = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Cost> costs = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Instant createdDate = Instant.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
