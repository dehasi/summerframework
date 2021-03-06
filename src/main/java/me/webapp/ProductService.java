package me.webapp;

import me.summerframework.beans.factory.annotation.Autowired;
import me.summerframework.beans.factory.stereotype.Service;

/** Created by Ravil on 02/09/2018. */
@Service
public class ProductService {
    @Autowired
    private PromotionsService promotionsService;

    public PromotionsService getPromotionsService() {
        return promotionsService;
    }

    public void setPromotionsService(PromotionsService promotionsService) {
        this.promotionsService = promotionsService;
    }
}
