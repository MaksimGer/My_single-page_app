package org.example.domain.resources;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<ProductInfo> productInfoList = new ArrayList<>();
    private int cost = 0;

    public Cart() {
    }

    public List<ProductInfo> getProductInfoList() {
        return productInfoList;
    }

    public void addProduct(ProductInfo productInfo){

        if (productInfoList.isEmpty() || !productInfoList.contains(productInfo)){
            productInfoList.add(productInfo);
        }
        else{
            int productInd = productInfoList.indexOf(productInfo);
            ProductInfo curProduct = productInfoList.get(productInd);
            int curOrderCount = curProduct.getOrderCount();
            int newOrderCount = curOrderCount + productInfo.getOrderCount();

            curProduct.setOrderCount(newOrderCount);
        }

        calculateTheCost();
    }

    public void setProductInfoList(List<ProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        calculateTheCost();
        return cost;
    }

    public void clear(){
        if(!productInfoList.isEmpty())
            productInfoList.clear();
        cost = 0;
    }

    private void calculateTheCost(){
        cost = 0;

        if(!productInfoList.isEmpty()){

            for(ProductInfo productInfo: productInfoList){
                int curCost = productInfo.getPrice() * productInfo.getOrderCount();
                cost += curCost;
            }
        }
    }
}
