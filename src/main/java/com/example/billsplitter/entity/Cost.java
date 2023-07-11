package com.example.billsplitter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cost")
@Setter
@Getter
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

    @CollectionTable(name = "split_between_user", joinColumns = @JoinColumn(name = "cost_id"))
//    @Column(name = "split_between", length = 100)
    @ElementCollection
    private List<Member> splitBetween;

    //    @Column(name = "paid_by")//, length = 200, nullable = false)
    @Embedded
    @AttributeOverride(name = "uuid", column = @Column(name = "paid_by_uuid"))
    @AttributeOverride(name = "username", column = @Column(name = "paid_by_username"))
    private Member paidBy;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cost cost = (Cost) o;
        return Objects.equals(id, cost.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
