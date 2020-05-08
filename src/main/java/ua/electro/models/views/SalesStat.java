package ua.electro.models.views;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Immutable
@Entity
@NoArgsConstructor
@Table(name = "sales_stat")
public class SalesStat {

    @Id
    private Date date;

    private Long sold;

    private Long orderPlaced;

    private Long revenue;

}

