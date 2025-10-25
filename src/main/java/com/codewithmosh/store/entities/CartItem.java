package com.codewithmosh.store.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(targetEntity = Cart.class)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(name = "quantity")
    private int quantity;
    
    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @PrePersist
    @PreUpdate
    public void calculateSubtotal(){
        this.subtotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}