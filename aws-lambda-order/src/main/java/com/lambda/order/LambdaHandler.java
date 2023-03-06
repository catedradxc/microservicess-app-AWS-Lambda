package com.lambda.order;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.lambda.order.model.Item;
import com.lambda.order.model.Order;
import com.lambda.order.model.OrderLine;
import com.lambda.order.model.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaHandler implements RequestHandler<Request, Object> {

    private List<Order> orders = new ArrayList<>();
    private long ids = 4;

    @Override
    public Object handleRequest(Request request, Context context) {


        Order o1 = new Order(1L);
        o1.addLine(3, 1);
        o1.setId(1L);
        Order o2 = new Order(2L);
        o2.addLine(2, 2);
        o2.setId(2L);
        Order o3 = new Order(3L);
        o3.addLine(1, 3);
        o3.setId(3L);
        orders.clear();
        orders.add(o1);
        orders.add(o2);
        orders.add(o3);


        switch (request.getHttpMethod()){
            case "GET":
                if(request.getId() == null){
                    return orders;
                }else {
                    return orders.stream().filter(c -> c.getId() == request.getId())
                            .collect(Collectors.toList());
                }
            case "POST":
                Order i = request.getOrder();
                i.setId(ids);
                ids++;
                orders.add(i);
                return i;
            case "DELETE":
                Order o = orders.stream().filter(or -> or.getId() == request.getId()).collect(Collectors.toList()).get(0);
                orders.remove(o);
                return orders;
        }
        return orders;
    }
}
