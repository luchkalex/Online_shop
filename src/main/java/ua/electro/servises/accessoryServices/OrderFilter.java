package ua.electro.servises.accessoryServices;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
public class OrderFilter {
    private Long idMin;
    private Long idMax;
    private String username;
    private Integer totalMin;
    private Integer totalMax;
    private Long payment;
    private Long delivery;
    private String address;
    private Long status;
    private Date dateMin;
    private Date dateMax;

    final private String pattern = "yyyy-MM-dd";
    final private SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

    public OrderFilter(
            Long idMin, Long idMax,
            String username, Integer totalMin,
            Integer totalMax, Long payment,
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
