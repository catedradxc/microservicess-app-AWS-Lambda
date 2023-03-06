package com.lambda.order.model;

import com.lambda.order.clients.CatalogClient;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Order {

    private long id;

    private long customerId;

    private List<OrderLine> orderLine;

    public Order() {
        super();
        orderLine = new ArrayList<OrderLine>();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<OrderLine> getOrderLine() {
        return orderLine;
    }

    public Order(long customerId) {
        super();
        this.customerId = customerId;
        this.orderLine = new ArrayList<OrderLine>();
    }

    public void setOrderLine(List<OrderLine> orderLine) {
        this.orderLine = orderLine;
    }

    public void addLine(int count, long itemId) {
        this.orderLine.add(new OrderLine(count, itemId));
    }

    public int getNumberOfLines() {
        return orderLine.size();
    }

    public double totalPrice(CatalogClient itemClient) {
        return orderLine.stream()
                .map((ol) -> ol.getCount() * itemClient.price(ol.getItemId()))
                .reduce(0.0, (d1, d2) -> d1 + d2);
    }

    public void setCustomer(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getId() == order.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
