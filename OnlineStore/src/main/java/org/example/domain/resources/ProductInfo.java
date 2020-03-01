package org.example.domain.resources;

import org.example.domain.entityes.Subject;

public class ProductInfo {
    private Subject product;
    private int price;
    private int count;
    private int orderCount;

    public ProductInfo(Subject product, int price, int count, int orderCount) {
        this.setProduct(product);
        this.setPrice(price);
        this.setCount(count);
        this.setOrderCount(orderCount);
    }

    public ProductInfo() {
    }

    public Subject getProduct() {
        return product;
    }

    public void setProduct(Subject product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if(price > 0) {
            this.price = price;
        }else{
            this.price = 1;
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if(count > 0) {
            this.count = count;
        }
        else {
            this.count = 0;
        }
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        if(orderCount > 0){
            if(orderCount < this.count) {
                this.orderCount = orderCount;
            }else{
                this.orderCount = this.count;
            }
        }else{
            this.orderCount = 0;
        }
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ProductInfo that = (ProductInfo) o;

        return this.price == that.price &&
                this.product.equals(that.product);
    }

    @Override
    public int hashCode() {
        int hashCode = 23;

        hashCode = 31 * hashCode + product.hashCode();
        hashCode = 31 * hashCode + price;

        return hashCode;
    }
}
