package com.example.billsplitter.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
@Setter
@Getter
@EqualsAndHashCode
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
    private List<String> eventMembers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private List<Cost> costs = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Instant createdDate = Instant.now();
}
