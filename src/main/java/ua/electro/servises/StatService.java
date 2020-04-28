package ua.electro.servises;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.electro.models.views.SalesStat;
import ua.electro.repos.SalesStatRepo;

import java.util.List;

@Service
public class StatService {

    private final SalesStatRepo salesStatRepo;

    public StatService(SalesStatRepo salesStatRepo) {
        this.salesStatRepo = salesStatRepo;
    }

    public List<SalesStat> findAllSalesStat() {
        return salesStatRepo.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }
}
