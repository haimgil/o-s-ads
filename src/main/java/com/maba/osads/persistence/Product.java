package com.maba.osads.persistence;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Column(name="productSerialnumber", nullable = false)
    private String  productSerialNumber;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="category", nullable = false)
    private String category;
    @Column(name="isPromoted", nullable = false)
    private String isPromoted;
    @Column(name="price", nullable = false)
    private Long price;

    public Product() {}

    public Product(String productSerialNumber, String title, String category, String isPromoted, Long price) {
        this.productSerialNumber = productSerialNumber;
        this.title = title;
        this.category = category;
        this.isPromoted = isPromoted;
        this.price = price;
    }

    @Id
    public String getProductSerialNumber() {
        return productSerialNumber;
    }

    public void setProductSerialNumber(String productSerialNumber) {
        this.productSerialNumber = productSerialNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String isPromoted() {
        return isPromoted;
    }

    public void setPromoted(String promoted) {
        isPromoted = promoted;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
