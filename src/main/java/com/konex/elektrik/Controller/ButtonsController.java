package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Entity.Buttons;
import com.konex.elektrik.Entity.Counter;
import com.konex.elektrik.Entity.Indicators;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.Counter.CounterService;
import com.konex.elektrik.Service.Indicators.IndicatorsService;
import com.konex.elektrik.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/index")
public class ButtonsController {
    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());
    @Autowired
    private ButtonsService buttonsService;
    @Autowired
    private IndicatorsService indicatorsService;
    @Autowired
    private CounterService counterService;
    @Autowired
    private UserService userService;

//    @RequestMapping( value = "/", method = RequestMethod.GET)
//    public String addCommentToOrder(Model model,
//                                    HttpSession session,
//                                    @PathVariable("user") Long ordersId) {
//
//        model.addAttribute("orderId", ordersId);
//        return "/orderComment/create";
//    }

    @RequestMapping( value = "/", method = RequestMethod.GET)
    public String navTopMenu(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        String str = new String();
        String st = new String();
        String note = new String();
        Calendar cal = Calendar.getInstance();
        Counter counter = counterService.getById(7L);
        List<Indicators> indicators = indicatorsService.getAllByCounterSortAsc(counter);

        for (Indicators indicators1 : indicators) {
//            str += "'" + indicators1.getConsumption()+ "'" + ",";
//            note = st.substring(5, str.length()-3);
//            st +=
            cal.setTime(indicators1.getDate());
            int month = cal.get(Calendar.MONTH);
            st += "'" + month + "',";
        }
            st = "[" + st.substring(0, st.length()-1) + "]";
        System.out.println(st);
//        str = str.substring(0 ,str.length()-1);
//        st = st.substring(0 ,str.length()-1);
//        st = "[" + st + "]";
//        System.out.println(str);
//        System.out.println(st);


//        model.addAttribute("json", "['1', '2', '3', '4', '5', '5', '6', '7', '8']");
        model.addAttribute("labels", st);
        model.addAttribute("data", str);
        return "/test tabl";
    }

    @RequestMapping( value = "/class/{button.engName}", method = RequestMethod.GET)
    public String navLeftMenu(Model model, @PathVariable("button.engName") String buttonName,
                              HttpSession session) {

        List<Buttons> button = buttonsService.getAllByParentId(buttonsService.getByEngName(buttonName).getId());
        model.addAttribute("button", button);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
//        if(buttonName == "user") {
//            model.addAttribute("h1name", "Користувач");
//        }
//        else if(buttonName == "order") {
//            model.addAttribute("h1name", "Замовлення");
//        }
//        else if(buttonName == "assignment") {
//            model.addAttribute("h1name", "Відрядження");
//        }
//        else if(buttonName == "admin") {
//            model.addAttribute("h1name", "Адміністування");
//        }
//        else if(buttonName == "counter") {
//            model.addAttribute("h1name", "Лічильники");
//        }
        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        model.addAttribute("active", "active");
        return "/index";
    }
}
