package me.webapp;

import me.summerframework.beans.factory.Component;

/** Created by Ravil on 02/09/2018. */
@Component
public class ProductService {
    private PromotionsService promotionsService;

    public PromotionsService getPromotionsService() {
        return promotionsService;
    }

    public void setPromotionsService(PromotionsService promotionsService) {
        this.promotionsService = promotionsService;
    }
}
