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
import java.util.stream.Collectors;

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

    @RequestMapping( value = "/create", method = RequestMethod.GET)
    public String addCounterGet(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("h1name", "Створити лічильник");
        model.addAttribute("active", "active");
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "id")));
//        model.addAttribute("manufacturers", manufacturerService.getAll(new Sort(Sort.Direction.DESC, "date")));
        return "/counter/create";
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST)
    public String addCounterPost(@ModelAttribute("order") Counter counter, Model model,
                                 @RequestParam(value = "subdivision") Long subdivisionId,
                                 @RequestParam(value = "manufacturer") Long manufacturerId,
                                 HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Створити лічильник");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        Subdivision subdivision = subdivisionService.getById(subdivisionId);
//        Manufacturer manufacturer = manufacturerService.getById(manufacturerId);
        counterService.addCounter(counter, subdivision
//                , manufacturer
        );

        Indicators indicators = new Indicators();
        indicators.setIndicator(0);
        indicators.setConsumption(0);
        indicatorsService.addIndicators(indicators, counter);
        return "/counter/create";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.POST)
    public String editCounterPost(Counter counter, Model model,
                                  @RequestParam(name = "subdivision") Long subdivisionId,
                                  @RequestParam(name = "manufacturer")Long manufacturerId) {

        Subdivision subdivision = subdivisionService.getById(subdivisionId);
//        Manufacturer manufacturer = manufacturerService.getById(manufacturerId);
        counterService.editCounter(counter, subdivision
//                , manufacturer
        );
        return "redirect:/counter/trackAll";
    }

    @RequestMapping( value = "/edit/{counter.id}", method = RequestMethod.GET)
    public String editCounterGet(Model model, HttpSession session,
                                 @PathVariable("counter.id") Long counterId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        Counter counter = counterService.getById(counterId);
        model.addAttribute("h1name", "Редагувати лічильник: " + counter.getNumber());
        model.addAttribute("counters", counter);
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "id")));
//        model.addAttribute("manufacturers", manufacturerService.getAll(new Sort(Sort.Direction.DESC, "date")));

        return "/counter/edit";
    }

    @RequestMapping( value = "/delete/{counter.id}", method = RequestMethod.GET)
    public String deleteCounterGet(Model model,
                                 @PathVariable("counter.id") Long counterId) {

        counterService.delete(counterId);

        return "redirect:/counter/trackAll";
    }

    @RequestMapping( value = "/trackAll", method = RequestMethod.GET)
    public String trackAllCounter(Counter counter, Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("counters", counterService.getAll(new Sort(Sort.Direction.ASC, "subdivisions.name")));
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
//        model.addAttribute("manufacturers", manufacturerService.getAll(new Sort(Sort.Direction.DESC, "date")));
        return "/counter/trackAll";
    }

    @RequestMapping( value = "/trackAll", method = RequestMethod.POST)
    public String findCounterByParamPost(Model model, CounterFilter counterFilter, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("counters", counterService.findCounterByCriteria(counterFilter));
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
//        model.addAttribute("manufacturers", manufacturerService.getAll(new Sort(Sort.Direction.DESC, "date")));

        return "/counter/trackAll";
    }

    @RequestMapping( value = "/statistics", method = RequestMethod.GET)
    public String statisticsGet(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        List<Counter> counter = counterService.getAll(new Sort(Sort.Direction.ASC, "subdivisions"));

        List<Counter> counterList = new ArrayList<Counter>();

        Calendar calendar = Calendar.getInstance();
        Subdivision subdivision = new Subdivision();
        Date date = new Date();
        Calendar calendarThisYear = Calendar.getInstance();
        calendarThisYear.setTime(date);
        int thisYear = calendarThisYear.get(Calendar.YEAR);
        calendarThisYear.setTime(date);
        int thisMounth = calendarThisYear.get(Calendar.MONTH);

        model.addAttribute("year", thisYear);
        Year years = new Year();
        model.addAttribute("years", years.getArrayYear());
        model.addAttribute("counters", counterListMap(thisYear));
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        model.addAttribute("selectedSubdv", subdivision);
        model.addAttribute("typeIndicators", 0);

        return "/counter/statistics";
    }

    @RequestMapping( value = "/statistics", method = RequestMethod.POST)
    public String statisticsPOST(Model model, HttpSession session,
                                 @RequestParam(name = "year", required = false) int year,
                                 @RequestParam(name = "subdivisionId", required = false) Long subdivisionId,
                                 @RequestParam(name = "indicators", required = false) int typeIndicators) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
//        model.addAttribute("counters", counterService.getStatisticsBySpec(counterFilter));


        if (subdivisionId != null) {
            Subdivision subdivision = subdivisionService.getById(subdivisionId);
            model.addAttribute("selectedSubdv", subdivision);
            model.addAttribute("counters", counterBySubdivisionListMap(year, subdivisionId));
        }
        else {
            Subdivision subdivision = new Subdivision();
            model.addAttribute("selectedSubdv", subdivision);
            model.addAttribute("counters", counterListMap(year));
        }

        model.addAttribute("year", year);
        Year years = new Year();
        model.addAttribute("years", years.getArrayYear());
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        model.addAttribute("typeIndicators", typeIndicators);

        return "/counter/statistics";
    }

    public Map<Counter, List<Indicators>> counterListMap(int year) {

        List<Counter> countersList = counterService.getAll(new Sort(Sort.Direction.ASC, "subdivisions.name"));

        Map<Counter, List<Indicators>> countersListMap = new LinkedHashMap<>();

        for (Counter counter : countersList) {

            countersListMap.put(counter,
                    indicatorsService.getListIndicatorSortByCounterByDate(counter, year));
        }

        return countersListMap;

//        List<Indicators> indicatorsList = indicatorsService.getListIndicatorByDate(year);
//
//        long idBefore = 5   ;
//        int month = 0;
//
//        Calendar calendar = Calendar.getInstance();
//        Date date = new Date();
//        calendar.setTime(date);
//        int thisMonth = calendar.get(Calendar.YEAR);
//        Counter counterIndicators = new Counter();
//
//        List<Indicators> indicatorsArrayList = new ArrayList<>();
////        indicatorsList.sort(Comparator.comparing(Indicators::getDate));
//        TreeMap<Counter, List<Indicators>> counterListMap = new TreeMap<Counter, List<Indicators>>();
////        SortedMap<Counter, List<Indicators>> counterListMap = new TreeMap<>(Comparator.comparing(c -> c.getSubdivisions().getId()));
//        for (Indicators indicators : indicatorsList) {
//
//            calendar.setTime(indicators.getDate());
//            int indicatorMonth = calendar.get(Calendar.MONTH);
//
//            if (indicators.getCounters().getId() == idBefore) {
//
//                if(indicatorMonth == month) {
//                    indicatorsArrayList.add(indicators);
//                    month++;
//                } else {
//                    for (int i = month; i <= indicatorMonth; i++){
//                        Indicators indicatorNull = new Indicators();
//                        indicatorNull = indicators;
//                        indicatorNull.setDate(new Date(year-i-01));
//                        indicatorsArrayList.add(indicatorNull);
//                        month++;
//                    }
//                }
//            }
//            else {
//                counterIndicators = counterService.getById(idBefore);
//                counterListMap.put(counterIndicators, indicatorsArrayList);
//                indicatorsArrayList = new ArrayList<>();
//                month = 0;
//                if(indicatorMonth == 0) {
//                    indicatorsArrayList.add(indicators);
//                    month++;
//                } else {
//                    for (int i = 0; i <= indicatorMonth; i++){
//                        Indicators indicatorNull = new Indicators();
//                        indicatorNull = indicators;
//                        indicatorNull.setDate(new Date(year-i-01));
//                        indicatorsArrayList.add(indicatorNull);
//                        month++;
//                    }
//                }
//                idBefore = indicators.getCounters().getId();
//            }
//        }
//
////        SortedMap<Counter, List<Indicators>> counterListSortedMap = new TreeMap<>(Comparator.comparing(c -> c.getSubdivisions().getName()));
////        counterListSortedMap.putAll(counterListMap);
//        Comparator<Counter> c = Comparator.comparingLong(Counter::getId);
//        Map<Counter, List<Indicators>> map = indicatorsArrayList.stream()
//                .collect(Collectors.groupingBy(
//                        Counter::getName, () -> c, Collectors.toSet()));
//
//        return counterListMap;
    }

    public Map<Counter, List<Indicators>> counterBySubdivisionListMap(int year, long idSubdv) {


        List<Counter> counters = counterService.getAllCountersBySubdivision(subdivisionService.getById(idSubdv));

        Map<Counter, List<Indicators>> countersListMap = new LinkedHashMap<>();
        for (Counter counter : counters)
                    countersListMap.put(counter,
                    indicatorsService.getListIndicatorSortByCounterByDate(counter, year));

        return countersListMap;

//        Subdivision subdivision = subdivisionService.getById(idSubdv);
//        List<Indicators> indicatorsList = indicatorsService.getAllBySubdivision(subdivision, year);
//
//        long idBefore = 0;
//        int month = 0;
//
//        Calendar calendar = Calendar.getInstance();
//        Counter counterIndicators = new Counter();
//
//        List<Indicators> indicatorsArrayList = new ArrayList<>();
//        Map<Counter, List<Indicators>> counterListMap = new HashMap<>();
//
//        for (Indicators indicators : indicatorsList) {
//
//            calendar.setTime(indicators.getDate());
//            int indicatorMonth = calendar.get(Calendar.MONTH);
//
//            if (indicators.getCounters().getId() == idBefore) {
//
//                if(indicatorMonth == month) {
//                    indicatorsArrayList.add(indicators);
//                    month++;
//                } else {
//                    for (int i = month; i <= indicatorMonth; i++){
//                        Indicators indicatorNull = new Indicators();
//                        indicatorNull = indicators;
//                        indicatorNull.setDate(new Date(year-i-01));
//                        indicatorsArrayList.add(indicatorNull);
//                        month++;
//                    }
//                }
//            }
//            else {
//                counterIndicators = counterService.getById(indicators.getCounters().getId());
//                indicatorsArrayList.add(indicators);
//                counterListMap.put(counterIndicators, indicatorsArrayList);
//                indicatorsArrayList = new ArrayList<>();
//                month = 0;
//                if(indicatorMonth == 0) {
//                    indicatorsArrayList.add(indicators);
//                    month++;
//                } else {
//                    for (int i = 0; i <= indicatorMonth; i++){
//                        Indicators indicatorNull = new Indicators();
//                        indicatorNull = indicators;
//                        indicatorNull.setDate(new Date(year-i-01));
//                        indicatorsArrayList.add(indicatorNull);
//                        month++;
//                    }
//                }
//                idBefore = indicators.getCounters().getId();
//            }
//        }
//        return counterListMap;
    }

    @RequestMapping( value = "/statisticsBySubdivision", method = RequestMethod.GET)
    public String statisticsBySubdivisionGet(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());

        Date date = new Date();
        Calendar calendarThisYear = Calendar.getInstance();
        calendarThisYear.setTime(date);
        int thisYear = calendarThisYear.get(Calendar.YEAR);

        List<Counter> counterBySubdivList = counterService.getAllCountersBySubdivision(user.getSubdivisions());
        Map<Counter, List<Indicators>> counterListMap = new HashMap<>();

        for(Counter counter : counterBySubdivList) {
            try {
                List<Indicators> indicatorsArray = getIndicatorsBySubdivision(thisYear, counter.getId());
                counterListMap.put(counter, indicatorsArray);
            } catch (NullPointerException e) {

            } catch (IndexOutOfBoundsException e) {

            }
        }

//        try {
//
//            IndicatorsController controller = new IndicatorsController();
//
////            if (counterBySubdivList.get(0) != null) {
//                model.addAttribute("data1", controller.getIndicatorsByCounterAndYear(thisYear, counterBySubdivList.get(0).getId()));
//                model.addAttribute("counter1", counterBySubdivList.get(0).getNumber());
////            }
////            if (counterBySubdivList.get(1) != null) {
//                model.addAttribute("data2", controller.getIndicatorsByCounterAndYear(thisYear, counterBySubdivList.get(1).getId()));
//                model.addAttribute("counter2", counterBySubdivList.get(1).getNumber());
////            }
////            if (counterBySubdivList.get(2) != null) {
//                model.addAttribute("data3", controller.getIndicatorsByCounterAndYear(thisYear, counterBySubdivList.get(2).getId()));
//                model.addAttribute("counter3", counterBySubdivList.get(2).getNumber());
////            }
////            if (counterBySubdivList.get(3) != null) {
//                model.addAttribute("data4", controller.getIndicatorsByCounterAndYear(thisYear, counterBySubdivList.get(3).getId()));
//                model.addAttribute("counter4", counterBySubdivList.get(3).getNumber());
////            }
////            if (counterBySubdivList.get(4) != null) {
//                model.addAttribute("data5", controller.getIndicatorsByCounterAndYear(thisYear, counterBySubdivList.get(4).getId()));
//                model.addAttribute("counter5", counterBySubdivList.get(4).getNumber());
////            }
//        } catch (NullPointerException e) {
//
//        } catch (IndexOutOfBoundsException e) {
//
//        }
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        model.addAttribute("selectedSubdv", user.getSubdivisions());
        model.addAttribute("counters", counterListMap);
        model.addAttribute("year", thisYear);
        Year years = new Year();
        model.addAttribute("years", years.getArrayYear());
        return "/counter/statisticsBySubdivision";
    }

    @RequestMapping( value = "/statisticsBySubdivision", method = RequestMethod.POST)
    public String statisticsBySubdivisionPOST(Model model, HttpSession session,
                                              @RequestParam(name = "year") int year,
                                              @RequestParam(name = "subdivision") Long subdivisionId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");
        model.addAttribute("active", "active");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());

        Subdivision subdivision = subdivisionService.getById(subdivisionId);
        List<Counter> counterBySubdivList = counterService.getAllCountersBySubdivision(subdivision);
        Map<Counter, List<Indicators>> counterListMap = new HashMap<>();
        IndicatorsController indicatorsController = new IndicatorsController();

        for(Counter counter : counterBySubdivList) {
            try {
                List<Indicators> indicatorsArray = getIndicatorsBySubdivision(year, counter.getId());
                counterListMap.put(counter, indicatorsArray);
            } catch (NullPointerException e) {

            } catch (IndexOutOfBoundsException e) {

            }
        }
//IndicatorsController controller = new IndicatorsController();
//
//        if (counterBySubdivList.get(0) != null) {
//            model.addAttribute("data1", controller.getIndicatorsByCounterAndYear(year, counterBySubdivList.get(0).getId()));
//            model.addAttribute("counter1", counterBySubdivList.get(0).getNumber());
//        } if (counterBySubdivList.get(1) != null) {
//            model.addAttribute("data2", controller.getIndicatorsByCounterAndYear(year, counterBySubdivList.get(1).getId()));
//            model.addAttribute("counter2", counterBySubdivList.get(1).getNumber());
//        } if (counterBySubdivList.get(2) != null) {
//            model.addAttribute("data3", controller.getIndicatorsByCounterAndYear(year, counterBySubdivList.get(2).getId()));
//            model.addAttribute("counter3", counterBySubdivList.get(2).getNumber());
//        } if (counterBySubdivList.get(3) != null) {
//            model.addAttribute("data4", controller.getIndicatorsByCounterAndYear(year, counterBySubdivList.get(3).getId()));
//            model.addAttribute("counter4", counterBySubdivList.get(3).getNumber());
//        } if (counterBySubdivList.get(4) != null) {
//            model.addAttribute("data5", controller.getIndicatorsByCounterAndYear(year, counterBySubdivList.get(4).getId()));
//            model.addAttribute("counter5", counterBySubdivList.get(4).getNumber());
//        }

        model.addAttribute("counters", counterListMap);
        model.addAttribute("year", year);
        Year years = new Year();
        model.addAttribute("years", years.getArrayYear());
        model.addAttribute("subdivisions", subdivisionService.getAll(new Sort(Sort.Direction.ASC, "name")));
        model.addAttribute("selectedSubdv", subdivision);

        return "/counter/statisticsBySubdivision";
    }

    public List<Indicators> getIndicatorsBySubdivision(int year, long counterId) {

        Counter counter = counterService.getById(counterId);

        int month = 0;

        List<Indicators> IndicatorsListChart = indicatorsService.getListIndicatorSortByCounterByDate(counter, year);
        List<Indicators> IndicatorsListArr = new ArrayList<>();

        for (Indicators firstIndicators : IndicatorsListChart) {
            Calendar calendarIndicator = Calendar.getInstance();
            calendarIndicator.setTime(firstIndicators.getDate());
            int indicatorMonth = calendarIndicator.get(Calendar.MONTH);
            if (indicatorMonth == month) {
                IndicatorsListArr.add(firstIndicators);
                month++;
            } else {
                for (int i = 0; i < indicatorMonth; i++ ) {
                    Indicators indicators = new Indicators();
                    indicators.setConsumption(0);
                    indicators.setIndicator(0);
                    IndicatorsListArr.add(indicators);
                    month++;
                }
                IndicatorsListArr.add(firstIndicators);
                month++;
            }
        }
        Indicators indicators = new Indicators();
        if (IndicatorsListArr.size() != 12) {
            for (int i = IndicatorsListArr.size(); i <= 11; i++ ) {
                indicators.setConsumption(0);
                indicators.setIndicator(0);
                IndicatorsListArr.add(indicators);
            }
        }

        return IndicatorsListArr;
    }

    @RequestMapping( value = "/averageStatisticByYear", method = RequestMethod.GET)
    public String averageStatisticByYear(Model model, HttpSession session) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Переглянути всі лічильники");
        model.addAttribute("active", "active");
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
