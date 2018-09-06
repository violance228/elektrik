package com.konex.elektrik.Const;

import com.konex.elektrik.Entity.Order;
import com.konex.elektrik.Entity.Status;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Service.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PushNotification {
    @Autowired
    private OrderService orderService;
    
    public String getExecuteBeforeDatePushNotification(Subdivision subdivision, Status status) {
        
        String pushNotoficationMessege = "";
        List<Order> orderList = orderService.getAllByStatusAndSubdivisionsAndExecuteBeforeDateIsNotNullOrderByDateOfApplicationAsc(status, subdivision);

        for (Order order : orderList) {
            
        }
        
        return "";
    }
}
