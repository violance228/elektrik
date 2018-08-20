package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Entity.*;
import com.konex.elektrik.Service.Assignment.AssignmentService;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.Cars.CarsService;
import com.konex.elektrik.Service.City.CityService;
import com.konex.elektrik.Service.Passenger.PassengerService;
import com.konex.elektrik.Service.TransportOrder.TransportOrderService;
import com.konex.elektrik.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Controller
@RequestMapping("/transportOrder")
public class TransportOrderController {

    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private TransportOrderService transportOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ButtonsService buttonsService;
    @Autowired
    private CarsService carsService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private CityService cityService;

    @RequestMapping( value = "/addCar", method = RequestMethod.GET)
    public String addCarGet(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());

        return "/cars/create";
    }

    @RequestMapping( value = "/addCar", method = RequestMethod.POST)
    public String addCarPost(@ModelAttribute("cars") Cars cars, Model model,
                             HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        try {
            carsService.addCars(cars);
            log.info("addCars");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/transportOrder/addCar";
    }

    @RequestMapping( value = "/trackCar/{car.id}", method = RequestMethod.GET)
    public String trackCarGet(Model model, HttpSession session, @PathVariable(name = "car.id") Long carId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("car", carsService.getById(carId));
        log.info("trackCar");

        return "/cars/track";
    }

    @RequestMapping( value = "/trackAllCar", method = RequestMethod.GET)
    public String trackAllCarsGet(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("cars", carsService.getAll());
        log.info("trackAllCar");

        return "/cars/trackAll";
    }

    @RequestMapping( value = "/addTransportOrder/{assignment.id}", method = RequestMethod.GET)
    public String addTransportOrderGet(Model model, HttpSession session,
                                       @PathVariable("assignment.id") Long assignmentId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("cars", carsService.getAll());
        model.addAttribute("assignments", assignmentService.getById(assignmentId));
        log.info("addTransportOrder");
        return "/transportOrder/create";
    }

    @RequestMapping( value = "/addTransportOrder", method = RequestMethod.POST)
    public String addTransportOrderGet(@ModelAttribute("transportOrder") TransportOrder transportOrder, Model model, HttpSession session,
                                       @RequestParam(name = "car") Long carId,
                                       @RequestParam(name = "assignmentId") Long assignmentId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        Cars cars = carsService.getById(carId);
        Assignment assignment = assignmentService.getById(assignmentId);
        Date localDate = new Date();
        transportOrder.setDateOfCreation(localDate);
        TransportOrder transportOrderFinnaly = new TransportOrder();
        try {
            transportOrderFinnaly = transportOrderService.addTransportOrder(transportOrder, cars, assignment, user);
            log.info("addTransportOrder");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("addTransportOrderFail");
        }
        return "redirect:/transportOrder/track/" + transportOrderFinnaly.getId();
    }

    @RequestMapping( value = "/trackTransportOrder/{transportOrder.id}", method = RequestMethod.GET)
    public String trackTransportOrderGet(Model model, HttpSession session,
                                         @PathVariable("transportOrder.id") Long transportOrderId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("transportOrder", transportOrderService.getById(transportOrderId));
        log.info("trackTransportOrder");

        return "/transportOrder/track";
    }

    @RequestMapping( value = "/trackAllTransportOrder", method = RequestMethod.GET)
    public String trackAllTransportOrderGet(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        List<TransportOrder> transportOrder = transportOrderService.getAllWhereAssignmentIsNotNull();
        model.addAttribute("transportOrder", transportOrder);
        Set<Passenger> passengerSet;
        log.info("trackTransportOrder");

        return "/transportOrder/trackAll";
    }

    @RequestMapping( value = "/addToTravel/{trOrder.id}", method = RequestMethod.GET)
    public String addToTravelGet(Model model, HttpSession session,
                                 @PathVariable("trOrder.id") Long transportOrderId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("transportOrderId", transportOrderId);
        model.addAttribute("city", transportOrderService.getById(transportOrderId).getAssignments().getCitiesTravels());
        log.info("addToTravel");
        return "/transportOrder/addToTravel";
    }

    @RequestMapping( value = "/addToTravel", method = RequestMethod.POST)
    public String addToTravelPost(@ModelAttribute("passenger") Passenger passenger,
                                  Model model, HttpSession session,
                                  @RequestParam(value="transportOrderId", required = false) Long transportOrderId,
                                  @RequestParam(value="cityId", required = false) Long cityId) {

        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        City city = cityService.getById(cityId);
        TransportOrder transportOrder = transportOrderService.getById(transportOrderId);

        passenger.setUsers(user);
        passenger.setCities(city);
        passenger.setTransportOrders(transportOrder);
        try {
            passengerService.addPassenger(passenger);
            log.info("addToTravelOk");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("addToTravelFail");
        }
        return "redirect:/transportOrder/trackAllTransportOrder";
    }
}