package com.codewithmosh.store.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "email", unique=true)
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @OneToMany(targetEntity = Address.class, mappedBy="user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Address> addresses;
    
    @OneToOne(targetEntity = Cart.class, mappedBy = "user")
    private Cart cart;
    
    @OneToMany(targetEntity = Order.class, mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Order> order;
    
}