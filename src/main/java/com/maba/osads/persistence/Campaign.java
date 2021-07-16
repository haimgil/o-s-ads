package com.maba.osads.persistence;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "campaigns")
@EntityListeners(AuditingEntityListener.class)
public class Campaign {

    @Column(name="name", nullable = false)
    private String name;
    @Column(name="startDate", nullable = false)
    private Date startDate;
    @Column(name="bid", nullable = false)
    private Long bid;
    @Column(name="productIds",nullable = false)
    private String productIds; //comma separated

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String products) {
        this.productIds = products;
    }
}
