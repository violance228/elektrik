package com.konex.elektrik.Const;

import com.konex.elektrik.Entity.Order;
import com.konex.elektrik.Entity.Status;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Service.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class PushNotification {

    @Autowired
    private OrderService orderService;
    
    public String getExecuteBeforeDatePushNotification(Subdivision subdivision, Status status) {
        
        String pushNotificationMessege = "Для виконання заявки для відділа(ів): ";
        List<Order> orderList = orderService.getAllByStatusAndSubdivisionsAndExecuteBeforeDateIsNotNullOrderByDateOfApplicationAsc(status, subdivision);

        for (Order order : orderList) {
            pushNotificationMessege += order.getSubdivisions().getTypeSubdivisions() + "-" +
                                       order.getSubdivisions().getName() + "-" +
                                       order.getSubdivisions().getCities().getCity() + ", ";
        }

        pushNotificationMessege += "залишилось меньше доби";
        return pushNotificationMessege;
    }
}
