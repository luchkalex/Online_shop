package ua.electro.servises.accessoryServices;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductFilter {

    private Long idMin;
    private Long idMax;
    private Float priceMin;
    private Float priceMax;
    private Long quantityMin;
    private Long quantityMax;
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
