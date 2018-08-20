package com.konex.elektrik.Controller;//package com.konex.elektrik.Controller;
//
//import com.konex.elektrik.Entity.Buttons;
//import com.konex.elektrik.Entity.Manufacturer;
//import com.konex.elektrik.Entity.User;
//import com.konex.elektrik.Service.Buttons.ButtonsService;
//import com.konex.elektrik.Service.Manufacturer.ManufacturerService;
//import com.konex.elektrik.Service.User.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpSession;
//import java.util.List;
//
//@Controller
//@RequestMapping("/manufacturer")
//public class ManufacturerController {
//
//    @Autowired
//    private ManufacturerService manufacturerService;
//    @Autowired
//    private ButtonsService buttonsService;
//    @Autowired
//    private UserService userService;
//
//    @RequestMapping(value = "/trackAll", method = RequestMethod.GET)
//    public String trackAllManufacturer(Manufacturer manufacturer, HttpSession session,
//                                       Model model) {
//
//        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
//        model.addAttribute("buttons", buttons);
//        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
//        model.addAttribute("button", button);
//        model.addAttribute("h1name", "Переглянути всіх виробників");
//        model.addAttribute("active", "active");
//        model.addAttribute("manufacturers", manufacturerService.getAll(new Sort(Sort.Direction.DESC, "id")));
//        Long currUserId = (Long)session.getAttribute("currUserId");
//        User user = userService.getById(currUserId);
//        model.addAttribute("userLogo", user.getName());
//
//        return "/manufacturer/trackAll";
//    }
//
//    @RequestMapping(value = "/create", method = RequestMethod.GET)
//    public String createManufacturer(Manufacturer manufacturer,HttpSession session,
//                                     Model model) {
//
//        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
//        model.addAttribute("buttons", buttons);
//        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
//        model.addAttribute("button", button);
//        model.addAttribute("h1name", "Створити виробника");
//        model.addAttribute("active", "active");
//        Long currUserId = (Long)session.getAttribute("currUserId");
//        User user = userService.getById(currUserId);
//        model.addAttribute("userLogo", user.getName());
//        return "/manufacturer/create";
//    }
//
//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    public String createManufacturerPost(Manufacturer manufacturer, Model model) {
//
//        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
//        model.addAttribute("buttons", buttons);
//        model.addAttribute("h1name", "Створити виробника");
//        model.addAttribute("active", "active");
//        manufacturerService.addManufacturer(manufacturer);
//        return "redirect:/manufacturer/create";
//    }
//
//    @RequestMapping(value = "/edit", method = RequestMethod.GET)
//    public String editManufacturerGet(Long id, Model model, HttpSession session) {
//        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
//        model.addAttribute("buttons", buttons);
//        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
//        model.addAttribute("button", button);
//        model.addAttribute("h1name", "Редагувати Виробників");
//        model.addAttribute("active", "active");
//        Long currUserId = (Long)session.getAttribute("currUserId");
//        User user = userService.getById(currUserId);
//        model.addAttribute("userLogo", user.getName());
//        model.addAttribute("manufacturers", manufacturerService.getById(id));
//        return "/manufacturer/edit";
//    }
//
//    @RequestMapping(value = "/edit", method = RequestMethod.POST)
//    public String editManufacturerPost(Manufacturer manufacturer, Model model) {
//
//        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
//        model.addAttribute("buttons", buttons);
//        model.addAttribute("h1name", "Редагувати Виробників");
//        model.addAttribute("active", "active");
//        manufacturerService.editManufacturer(manufacturer);
//        return "/manufacturer/edit";
//    }
//}
