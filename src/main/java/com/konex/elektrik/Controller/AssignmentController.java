package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Entity.*;
import com.konex.elektrik.Service.Assignment.AssignmentService;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.CitiesTravel.CitiesTravelService;
import com.konex.elektrik.Service.City.CityService;
import com.konex.elektrik.Service.Subdivision.SubdivisionService;
import com.konex.elektrik.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private SubdivisionService subdivisionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ButtonsService buttonsService;
    @Autowired
    private CitiesTravelService citiesTravelService;
    @Autowired
    private CityService cityService;


    private SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
    private Date docDate = null;

    @RequestMapping( value = "/create", method = RequestMethod.GET)
    public String addAssignmentGet(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        model.addAttribute("cities", cityService.getAll(new Sort(Sort.Direction.ASC, "city")));
        model.addAttribute("minDate", formatForDateNow.format(date));
        return "/assignment/create";
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST)
    public String addAssignmentPost(@ModelAttribute("assignment") Assignment assignment, Model model,
                                    @RequestParam(value = "subdivision") Long subdivisionId,
                                    HttpSession session,
                                    @RequestParam("date") String date,
                                    @RequestParam("time") String time,
                                    @RequestParam(value = "cities") ArrayList<Long> citysId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити Відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        date += "T" + time + ":00.00";
        assignment.setDate$TimeOfDeparture(Timestamp.valueOf(date.replace("T"," ")));
        Date localDate = new Date();
        assignment.setDateOfCreating(localDate);
        model.addAttribute("userLogo", user.getName());
        Subdivision subdivision = subdivisionService.getById(subdivisionId);
        assignmentService.addAssignment(assignment, subdivision, user);

        for(int i = 0; i < citysId.size(); i++) {
            CitiesTravel citiesTravel = new CitiesTravel();
            citiesTravelService.addCitiesTravel(citiesTravel, assignment, cityService.getById(citysId.get(i)));
        }
        Assignment assignment1 = assignmentService.getById(4L);
        return "redirect:/assignment/create";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.GET)
    public String editAssignmentGet(Assignment assignment, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Редагувати відрядження");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("id", assignment.getId());
        model.addAttribute("comment", assignment.getComment());
        model.addAttribute("subdivision",assignment.getSubdivisions().getName());
        model.addAttribute("user",assignment.getUsers().getSurname());

        return "/assignment/edit";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.POST)
    public String editAssignmentPost(Assignment assignment, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Редагувати відрядження");
        model.addAttribute("active", "active");
        Status status = new Status();
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        assignmentService.editAssignment(assignment);
        return "/assignment/edit";
    }

    @RequestMapping( value = "/trackAll", method = RequestMethod.GET)
    public String trackAssignment(Assignment assignment, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі відрядженння");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("assignments",assignmentService.getAll(new Sort(Sort.Direction.DESC, "date")));
        return "/assignment/trackAll";
    }

    @RequestMapping( value = "/trackAssignmentsForMySubdivision", method = RequestMethod.GET)
    public String trackAssignmentsForMySubdivision(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі відрядженння");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        Set<Assignment> assignment = user.getSubdivisions().getAssignments();
        model.addAttribute("assignments", assignment);
        return "/assignment/trackAssignmentsSubmForMySubdivision";
    }


}
