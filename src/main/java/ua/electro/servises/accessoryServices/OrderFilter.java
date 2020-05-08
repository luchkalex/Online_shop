package ua.electro.servises.accessoryServices;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
public class OrderFilter {

    @Min(value = 0, message = "Min Id can't be negative!")
    private Long idMin;

    @Positive(message = "Max Id can't be negative!")
    @Max(value = Long.MAX_VALUE, message = "Id is too large!")
    private Long idMax;

    @Length(max = 255, message = "Username is too long (max - 255 symbols)")
    private String username;

    @Min(value = 0, message = "Min total can't be negative!")
    private Float totalMin;

    @Positive(message = "Max total can't be negative!")
    @Max(value = Long.MAX_VALUE, message = "Max total is too large!")
    private Float totalMax;

    private Long payment;
    private Long delivery;

    @Length(max = 255, message = "Address is too long (max - 255 symbols)")
    private String address;

    private Long status;

    private Date dateMin;
    private Date dateMax;

    final private String pattern = "yyyy-MM-dd";
    final private SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

    public OrderFilter(
            Long idMin, Long idMax,
            String username, Float totalMin,
            Float totalMax, Long payment,
            Long delivery, String address,
            Long status, String dateMin_str,
            String dateMax_str) throws ParseException {

        this.idMin = idMin;
        this.idMax = idMax;
        this.username = username;
        this.totalMin = totalMin;
        this.totalMax = totalMax;
        this.payment = payment;
        this.delivery = delivery;
        this.address = address;
        this.status = status;
        if (!StringUtils.isEmpty(dateMin_str)) {
            this.dateMin = dateFormat.parse(dateMin_str);
        }
        if (!StringUtils.isEmpty(dateMax_str)) {
            this.dateMax = dateFormat.parse(dateMax_str);
        }
        System.out.println(getDateMax());
        System.out.println(getDateMin());
    }
}
