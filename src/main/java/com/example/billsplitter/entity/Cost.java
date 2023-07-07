package com.example.billsplitter.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cost")
@Setter
@Getter
@EqualsAndHashCode
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cost_seq")
    @SequenceGenerator(name = "cost_seq", sequenceName = "cost_seq", allocationSize = 1)
    @Column(name = "cost_id")
    private Long id;

    @Column(name = "cost_amount", nullable = false)
    private Float costAmount;

    @Column(name = "cost_description", length = 500, nullable = false)
    private String costDescription;

    @CollectionTable(name = "split_between_user" , joinColumns = @JoinColumn(name = "cost_id"))
    @Column(name = "split_between", length = 100)
    @ElementCollection
    private List<String> splitBetween;

    @Column(name = "paid_by", length = 200, nullable = false)
    private String paidBy;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
