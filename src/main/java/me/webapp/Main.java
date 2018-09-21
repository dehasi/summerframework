package me.webapp;

import me.summerframework.beans.factory.BeanFactory;

public class Main {

    private void run() {

    }

    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.instantiate("me.webapp");
        beanFactory.populateProperties();
        ProductService productService = (ProductService) beanFactory.getBean("productService");
        System.out.println(productService);//ProductService@612
        System.out.println(productService.getPromotionsService());//ProductService@612
    }
}
