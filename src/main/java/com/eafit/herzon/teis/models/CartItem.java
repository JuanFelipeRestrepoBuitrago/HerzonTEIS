package com.eafit.herzon.teis.models;


import com.eafit.herzon.teis.models.Cart;
import com.eafit.herzon.teis.models.Jewel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "cartItems")
public class CartItem {
    /**
     * The ID of the relationship.d
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private int quantity;


    // /**
    //  * The Jewel associated with the auction.
    //  */
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "jewel_id", nullable = false)
    private Jewel jewel;

    // /**
    //  * Sets the Jewel associated with the auction.

    //  * @param jewel The Jewel associated with the auction.
    //  */
    public void setJewel(Jewel jewel) {
       this.jewel = jewel;
    }

    /**
     * The date and time when the auction was created.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * The date and time when the auction was last updated. It is automatically
     * updated by Hibernate when the entity is modified.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public CartItem() {
    }

    public CartItem(Jewel jewel, int quantity) {
        this.jewel = jewel;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}