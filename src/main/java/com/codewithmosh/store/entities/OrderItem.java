package com.codewithmosh.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "quantity")
    private int quantity;
    
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @Column(name = "price_at_purchase")
    private BigDecimal priceAtPurchase;
    
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    
    @PrePersist
    public void calculateSubtotal(){
        if (priceAtPurchase == null && product != null){
            priceAtPurchase = product.getPrice();
        }
        subtotal = priceAtPurchase.multiply(BigDecimal.valueOf(quantity));
    }
}
