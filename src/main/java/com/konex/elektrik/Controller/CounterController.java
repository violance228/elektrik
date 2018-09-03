package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Const.Year;
import com.konex.elektrik.Entity.*;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.Counter.CounterService;
import com.konex.elektrik.Service.Indicators.IndicatorsService;
import com.konex.elektrik.Service.Role.RoleService;
import com.konex.elektrik.Service.Subdivision.SubdivisionService;
import com.konex.elektrik.Service.User.UserService;
import com.konex.elektrik.filter.CounterFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.logging.Logger;

//import com.konex.elektrik.Service.Manufacturer.ManufacturerService;

@Controller
@RequestMapping("/counter")
public class CounterController {
    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());
    @Autowired
    private RoleService roleService;
    @Autowired
    private CounterService counterService;
    @Autowired
    private SubdivisionService subdivisionService;
    @Autowired
    private IndicatorsService indicatorsService;
    //    @Autowired
//    private ManufacturerService manufacturerService;
    @Autowired
    private ButtonsService buttonsService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String addCounterGet(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити лічильник");
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));

        return "/counter/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addCounterPost(@ModelAttribute("order") Counter counter, Model model,
                                 @RequestParam(value = "subdivision") Long subdivisionId,
                                 @RequestParam(value = "manufacturer") Long manufacturerId,
                                 HttpSession session) {



        Subdivision subdivision = subdivisionService.getById(subdivisionId);
        counterService.addCounter(counter, subdivision);

        Indicators indicators = new Indicators();
        indicators.setIndicator(0);
        indicators.setConsumption(0);
        indicatorsService.addIndicators(indicators, counter);

        return "redirect:/counter/create";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editCounterPost(Counter counter, Model model,
                                  @RequestParam(name = "subdivision") Long subdivisionId,
                                  @RequestParam(name = "manufacturer") Long manufacturerId) {

        Subdivision subdivision = subdivisionService.getById(subdivisionId);
        counterService.editCounter(counter, subdivision);

        return "redirect:/counter/trackAll";
    }

    @RequestMapping(value = "/edit/{counter.id}", method = RequestMethod.GET)
    public String editCounterGet(Model model, HttpSession session,
                                 @PathVariable("counter.id") Long counterId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        Counter counter = counterService.getById(counterId);
        model.addAttribute("h1name", "Редагувати лічильник: " + counter.getNumber());
        model.addAttribute("counters", counter);
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "id")));

        return "/counter/edit";
    }

    @RequestMapping(value = "/delete/{counter.id}", method = RequestMethod.GET)
    public String deleteCounterGet(Model model,
                                   @PathVariable("counter.id") Long counterId) {

        counterService.delete(counterId);

        return "redirect:/counter/trackAll";
    }

    @RequestMapping(value = "/trackAll", method = RequestMethod.GET)
    public String trackAllCounter(Counter counter, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");
        model.addAttribute("counters", counterService.getAll(new Sort(Sort.Direction.ASC, "subdivisions.name")));
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        
        return "/counter/trackAll";
    }

    @RequestMapping(value = "/trackAll", method = RequestMethod.POST)
    public String findCounterByParamPost(Model model, CounterFilter counterFilter) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");
        model.addAttribute("counters", counterService.findCounterByCriteria(counterFilter));
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));

        return "/counter/trackAll";
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public String statisticsGet(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");

        List<Counter> counter = counterService.getAll(new Sort(Sort.Direction.ASC, "subdivisions"));

        List<Counter> counterList = new ArrayList<Counter>();

        Calendar calendar = Calendar.getInstance();
        Subdivision subdivision = new Subdivision();
        subdivision.setName((long) subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")).size());
        Date date = new Date();
        Calendar calendarThisYear = Calendar.getInstance();
        calendarThisYear.setTime(date);
        int thisYear = calendarThisYear.get(Calendar.YEAR);
        calendarThisYear.setTime(date);
        int thisMounth = calendarThisYear.get(Calendar.MONTH);

        model.addAttribute("year", thisYear);
        Year years = new Year();
        model.addAttribute("years", years.getArrayYear());
        model.addAttribute("counters", counterService.counterListMap(thisYear));
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        model.addAttribute("typeIndicators", 0);

        return "/counter/statistics";
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    public String statisticsPOST(Model model, HttpSession session,
                                 @RequestParam(name = "dateOfWithdrawalOfIndicators", required = false) Integer year,
                                 @RequestParam(name = "subdivisionId", required = false) Long subdivisionId,
                                 @RequestParam(name = "indicators", required = false) Integer typeIndicators) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");

        if (subdivisionId != 0) {
            Subdivision subdivision = subdivisionService.getById(subdivisionId);
            model.addAttribute("selectedSubdv", subdivision);
            model.addAttribute("counters", counterService.counterBySubdivisionListMap(year, subdivisionId));
        } else {
            Subdivision subdivision = new Subdivision();
            model.addAttribute("selectedSubdv", subdivision);
            model.addAttribute("counters", counterService.counterListMap(year));
        }

        model.addAttribute("year", year);
        Year years = new Year();
        model.addAttribute("years", years.getArrayYear());
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        model.addAttribute("typeIndicators", typeIndicators);

        return "/counter/statistics";
    }

    @RequestMapping(value = "/statisticsBySubdivision", method = RequestMethod.GET)
    public String statisticsBySubdivisionGet(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");
        Long currUserId = (Long) session.getAttribute("currUserId");
        User user = userService.getById(currUserId);

        Date date = new Date();
        Calendar calendarThisYear = Calendar.getInstance();
        calendarThisYear.setTime(date);
        int thisYear = calendarThisYear.get(Calendar.YEAR);

        List<Counter> counterBySubdivList = counterService.getAllCountersBySubdivision(user.getSubdivisions());
        Map<Counter, List<Indicators>> counterListMap = new HashMap<>();

        for (Counter counter : counterBySubdivList) {
            try {
                List<Indicators> indicatorsArray = counterService.getIndicatorsBySubdivision(thisYear, counter.getId());
                counterListMap.put(counter, indicatorsArray);
            } catch (NullPointerException e) {

            } catch (IndexOutOfBoundsException e) {

            }
        }


        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        model.addAttribute("selectedSubdv", user.getSubdivisions());
        model.addAttribute("counters", counterListMap);
        model.addAttribute("year", thisYear);
        Year years = new Year();
        model.addAttribute("years", years.getArrayYear());
        return "/counter/statisticsBySubdivision";
    }

    @RequestMapping(value = "/statisticsBySubdivision", method = RequestMethod.POST)
    public String statisticsBySubdivisionPOST(Model model, HttpSession session,
                                              @RequestParam(name = "year") int year,
                                              @RequestParam(name = "subdivision") Long subdivisionId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");

        Subdivision subdivision = subdivisionService.getById(subdivisionId);
        List<Counter> counterBySubdivList = counterService.getAllCountersBySubdivision(subdivision);
        Map<Counter, List<Indicators>> counterListMap = new HashMap<>();
        IndicatorsController indicatorsController = new IndicatorsController();

        for (Counter counter : counterBySubdivList) {
            try {
                List<Indicators> indicatorsArray = counterService.getIndicatorsBySubdivision(year, counter.getId());
                counterListMap.put(counter, indicatorsArray);
            } catch (NullPointerException e) {

            } catch (IndexOutOfBoundsException e) {

            }
        }

        model.addAttribute("counters", counterListMap);
        model.addAttribute("year", year);
        Year years = new Year();
        model.addAttribute("years", years.getArrayYear());
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        model.addAttribute("selectedSubdv", subdivision);

        return "/counter/statisticsBySubdivision";
    }

    @RequestMapping(value = "/averageStatisticByYear", method = RequestMethod.GET)
    public String averageStatisticByYear(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");
        Year year = new Year();
        model.addAttribute("years", year.getArrayYear());

        List<Counter> counterList = new ArrayList<>();
        for (long i = 0; i < 100; i++) {
            long a = 20 + (long) (Math.random() * 400);
            if (counterService.getById(a) != null) {
                counterList.add(counterService.getById(a));
                if (counterList.size() == 4)
                    break;
            }
        }
        int sumFirst = 0, sumSecond = 0, sumThird = 0, sumFourth = 0;
        for (Counter counter : counterList) {
            List<Indicators> indicatorsList = indicatorsService.getListIndicatorSortByCounterByDate(counter, year.getArrayYear().get(5));
            for (Indicators indicators : indicatorsList) {
                sumFirst += indicators.getConsumption();
            }
        }
        for (Counter counter : counterList) {
            List<Indicators> indicatorsList = indicatorsService.getListIndicatorSortByCounterByDate(counter, year.getArrayYear().get(6));
            for (Indicators indicators : indicatorsList) {
                sumSecond += indicators.getConsumption();
            }
        }
        for (Counter counter : counterList) {
            List<Indicators> indicatorsList = indicatorsService.getListIndicatorSortByCounterByDate(counter, year.getArrayYear().get(7));
            for (Indicators indicators : indicatorsList) {
                sumThird += indicators.getConsumption();
            }
        }
        for (Counter counter : counterList) {
            List<Indicators> indicatorsList = indicatorsService.getListIndicatorSortByCounterByDate(counter, year.getArrayYear().get(8));
            for (Indicators indicators : indicatorsList) {
                sumFourth += indicators.getConsumption();
            }
        }

        model.addAttribute("data1", sumFirst);
        model.addAttribute("data2", sumSecond);
        model.addAttribute("data3", sumThird);
        model.addAttribute("data4", sumFourth);
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        return "/counter/averageStatistics";
    }
}
