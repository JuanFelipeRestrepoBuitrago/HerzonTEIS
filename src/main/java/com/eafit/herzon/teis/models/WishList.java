package com.eafit.herzon.teis.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "cartItems")
public class WishList {


    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // /**
    //  * The Jewel associated with the auction.
    //  */
    // @ManyToOne(fetch = FetchType.EAGER, optional = false)
    // @JoinColumn(name = "jewel_id", nullable = false)
    // private Jewel jewel;

    // /**
    //  * The Jewel associated with the auction.
    //  */
    // @ManyToOne(optional = false)
    // @JoinColumn(name = "user_id", nullable = false)
    // private Jewel jewel;
}
