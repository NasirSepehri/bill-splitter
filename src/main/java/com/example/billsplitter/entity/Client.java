package com.example.billsplitter.entity;

import com.example.billsplitter.enums.ClientRolesEnum;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client", indexes = @Index(columnList = "username"))
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Client {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
//    @SequenceGenerator(name = "client_seq", sequenceName = "client_seq", allocationSize = 1)
    @Column(name = "client_id")
    private UUID id = UUID.randomUUID();

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", length = 255, unique = true, nullable = false)
    private String username;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "email", length = 255, unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private ClientRolesEnum role;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Event> events = new ArrayList<>();

    public Client(UUID clientId) {
        this.setId(clientId);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", password='" + "*****" + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", events=" + events +
                '}';
    }
}
