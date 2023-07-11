package com.example.billsplitter.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class Member {

    private UUID uuid = UUID.randomUUID();
    private String username;

    public Member(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(uuid, member.uuid) && Objects.equals(username, member.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, username);
    }
}
