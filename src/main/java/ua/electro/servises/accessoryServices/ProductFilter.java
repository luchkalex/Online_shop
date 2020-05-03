package ua.electro.servises.accessoryServices;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter
@Getter
public class ProductFilter {

    @Min(value = 0, message = "Min Id can't be negative!")
    private Long idMin;

    @Min(value = 0, message = "Max Id can't be negative!")
    @Max(value = Long.MAX_VALUE, message = "Id is too large!")
    private Long idMax;

    @Min(value = 0, message = "Min Price can't be negative!")
    private Float priceMin;

    @Min(value = 0, message = "Max Price can't be negative!")
    @Max(value = 300000, message = "Price is too large! (Max 300000)")
    private Float priceMax;

    @Min(value = 0, message = "Min Quantity can't be negative!")
    private Long quantityMin;

    @Min(value = 0, message = "Max Quantity can't be negative!")
    @Max(value = Long.MAX_VALUE, message = "Quantity is too large!")
    private Long quantityMax;

    @Length(max = 255, message = "Title is too long (max - 255 symbols)")
    private String title;

    private Long category;
    private Long productStatus;

    public ProductFilter(String title, Long idMin, Long idMax, Float priceMin,
                         Float priceMax, Long quantityMin, Long quantityMax,
                         Long category, Long productStatus) {

        this.title = title;
        this.idMin = idMin;
        this.idMax = idMax;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.quantityMin = quantityMin;
        this.quantityMax = quantityMax;
        this.category = category;
        this.productStatus = productStatus;
    }

}
