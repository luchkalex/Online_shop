package ua.electro.models.views;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Immutable
@Entity
@NoArgsConstructor
public class QuantityOfProducts {

    @Id
    private Long id;

    private Long quantity;

}

