package ua.electro.servises;

import org.springframework.stereotype.Service;
import ua.electro.models.ProductStatuses;
import ua.electro.repos.StatusesRepo;

import java.util.List;

@Service
public class StatusesService {

    private final StatusesRepo statusesRepo;

    public StatusesService(StatusesRepo statusesRepo) {
        this.statusesRepo = statusesRepo;
    }


    public List<ProductStatuses> findAll() {
        return statusesRepo.findAll();
    }
}
