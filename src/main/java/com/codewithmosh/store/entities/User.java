package com.codewithmosh.store.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    @OneToMany(mappedBy="user")
    private List<Address> direcciones = new ArrayList<>();
    //private List<Product> carrito = new ArrayList<>();