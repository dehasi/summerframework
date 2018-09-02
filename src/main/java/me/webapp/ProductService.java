package me.webapp;

import me.summerframework.beans.factory.stereotype.Component;
import me.summerframework.beans.factory.stereotype.Service;

/** Created by Ravil on 02/09/2018. */
@Service
public class ProductService {
    private PromotionsService promotionsService;

    public PromotionsService getPromotionsService() {
        return promotionsService;
    }

    public void setPromotionsService(PromotionsService promotionsService) {
        this.promotionsService = promotionsService;
    }
}
