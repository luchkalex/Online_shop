package ua.electro.servises;

import lombok.Getter;
import lombok.Setter;
import ua.electro.models.Category;
import ua.electro.models.ProductStatuses;

@Setter
@Getter
public class ProductFilter {
    private Long idMin;
    private Long idMax;
    private Integer priceMin;
    private Integer priceMax;
    private Long quantityMin;
    private Long quantityMax;
    private String title;
    private Category category;
    private ProductStatuses productStatus;

    public ProductFilter(String title, Long idMin, Long idMax, Integer priceMin,
                         Integer priceMax, Long quantityMin, Long quantityMax,
                         Category category, ProductStatuses productStatus) {

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
