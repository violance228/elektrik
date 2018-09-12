package com.konex.elektrik.Service.Order;

import com.konex.elektrik.Entity.Order;
import com.konex.elektrik.Entity.Status;
import com.konex.elektrik.Entity.Subdivision;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Repository.OrderRepository;
import com.konex.elektrik.Service.Status.StatusService;
import com.konex.elektrik.Specification.OrderSpecs;
import com.konex.elektrik.filter.OrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StatusService statusService;
    @Autowired
    private OrderService orderService;

    private Date date = new Date();

    @Transactional
    public Order addOrder(Order order, Subdivision subdivision, User user) {

        System.out.println(date);
        order.setDateOfApplication(date);
        order.setSubdivisions(subdivision);
        order.setUsers(user);
        order.setStatus(statusService.getById(3L));
        return orderRepository.save(order);
    }

    @Transactional
    public void delete(Long id) {

        orderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Order getById(Long id) {

        return orderRepository.getOne(id);
    }

    @Transactional
    public Order editOrderStatus(Order order, Status status, String surname) {

        order.setSurname(surname);
        order.setStatus(status);
        if(status.getId() == 1) {
            order.setDateOfCompletion(date);

        }
        System.out.println("---------------"+order.getStatus().getId());
        return orderRepository.saveAndFlush(order);
    }

    @Transactional
    public Order editOrder(Order order) {

        System.out.println("---------------"+order.getStatus().getId());
        return orderRepository.saveAndFlush(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getAll(Sort sort) {

        return orderRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public Set<Order> getAllByUser(User user) {

        return orderRepository.findAllByUsersOrderByDateOfApplicationDesc(user);
    }

    @Transactional(readOnly = true)
    public Set<Order> getAllBySubdivision(Subdivision subdivision) {

        return orderRepository.findAllBySubdivisionsOrderByDateOfApplicationDesc(subdivision);
    }

    @Transactional
    public List<Order> getByCriteria(OrderFilter orderFilter, Sort sort) {
        Specification<Order> orderSpec = OrderSpecs.orderSpecsByFilter(orderFilter);
        return orderRepository.findAll(orderSpec, sort);
    }

    @Transactional(readOnly = true)
    public  List<Order> getAllByStatusAndSubdivisionsAndExecuteBeforeDateIsNotNullOrderByDateOfApplicationAsc(Status status, Subdivision subdivision) {

        return orderRepository.getAllByStatusAndSubdivisionsAndExecuteBeforeDateIsNotNullOrderByDateOfApplicationAsc(status, subdivision);
    }

    @Transactional(readOnly = true)
    public String getExecuteBeforeDatePushNotification(Subdivision subdivision, Status status) {

        StringBuilder pushNotificationMessege = new StringBuilder("\"Для виконання заявки для відділа(ів): ");
        List<Order> orderList = orderService.getAllByStatusAndSubdivisionsAndExecuteBeforeDateIsNotNullOrderByDateOfApplicationAsc(status, subdivision);
        Date todayDate = new Date();
        for (Order order : orderList) {

            long millisecond = order.getExecuteBeforeDate().getTime() - todayDate.getTime();

            if ((millisecond <= 86400000) && (millisecond > 0)) {
                pushNotificationMessege
                        .append(order.getSubdivisions().getTypeSubdivisions().getType())
                        .append("-")
                        .append(order.getSubdivisions().getName())
                        .append("-")
                        .append(order.getSubdivisions().getCities().getCity())
                        .append(", ");
            }
        }

        pushNotificationMessege.append("залишилось меньше доби\"");

        return pushNotificationMessege.toString();
    }
}
