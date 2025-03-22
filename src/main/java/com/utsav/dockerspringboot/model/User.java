package com.utsav.dockerspringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utsav.dockerspringboot.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    // Automatically set the creation timestamp for createdAt
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Relationships
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private RewardPoints rewardPoints;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;


    public void initializeRewardPoints() {
        RewardPoints rewardPoints = new RewardPoints();
        rewardPoints.setUser(this); // Link the RewardPoints to the User
        rewardPoints.setPointsEarned(0); // Start with 0 points earned
        rewardPoints.setPointsRedeemed(0); // Start with 0 points redeemed
        rewardPoints.setLastUpdated(LocalDateTime.now()); // Set the last updated timestamp
        this.rewardPoints = rewardPoints; // Assign the RewardPoints to the User
    }



}
