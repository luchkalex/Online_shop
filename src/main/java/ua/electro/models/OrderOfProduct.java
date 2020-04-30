package ua.electro.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "order_of_product")
public class OrderOfProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Address can't be empty")
    private String address;

    private float total;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_status", columnDefinition = "bigint default 1")
    private OrderStatuses orderStatuses;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_of_payment")
    private TypesOfPayment typeOfPayment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_of_delivery")
    private TypesOfDelivery typesOfDelivery;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
}
