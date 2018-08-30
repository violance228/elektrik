package com.konex.elektrik.Controller;

import com.konex.elektrik.Config.SecurityConfig;
import com.konex.elektrik.Const.Year;
import com.konex.elektrik.Entity.Buttons;
import com.konex.elektrik.Entity.Counter;
import com.konex.elektrik.Entity.Indicators;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Service.Buttons.ButtonsService;
import com.konex.elektrik.Service.Counter.CounterService;
import com.konex.elektrik.Service.Indicators.IndicatorsService;
import com.konex.elektrik.Service.Subdivision.SubdivisionService;
import com.konex.elektrik.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/indicator")
public class IndicatorsController {
    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());
    @Autowired
    private IndicatorsService indicatorsService;
    @Autowired
    private ButtonsService buttonsService;
    @Autowired
    private CounterService counterService;
    @Autowired
    private SubdivisionService subdivisionService;
    @Autowired
    private UserService userService;

    private  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private Date docDate = null;

    @RequestMapping( value = "/add", method = RequestMethod.POST)
    public String addIndicatorsPost(@ModelAttribute("indicators") Indicators indicators, Model model,
                                    @RequestParam("counterId") Long counterId,
                                    @RequestParam("dat") String date,
                                    HttpSession session) {


        Counter counter = counterService.getById(counterId);
        try {
            indicators.setDate(docDate = format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        indicatorsService.addIndicators(indicators, counter);
        return "redirect:/indicator/edit/" + counterId;
    }

    @RequestMapping( value = "/edit/{counter.id}", method = RequestMethod.GET)
    public String editIndicatorsGet(Model model, Indicators indicator,
                                    @PathVariable("counter.id") Long counterId,
                                    HttpSession session) throws ParseException {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());

        String str = new String();
        String st = new String();
        Counter counter = counterService.getById(counterId);
        model.addAttribute("h1name", "Редагувати показники: "+ counter.getNumber()+ "-" + counter.getName() + ", " + counter.getSubdivisions().getTypeSubdivisions().getType() +
                "-" + counter.getSubdivisions().getName() + "-" + counter.getSubdivisions().getCities().getCity());
        List<Indicators> indicators = indicatorsService.getAllByCounter(counter, new Sort(Sort.Direction.DESC, "date"));
        model.addAttribute("indicators", indicators);
        model.addAttribute("counterId", counterId);


        Date date1 = new Date();
        Calendar thisMonth = Calendar.getInstance();
        thisMonth.setTime(date1);
        int thisMonthStr = thisMonth.get(Calendar.MONTH);
        thisMonthStr++;
        Calendar this_year = Calendar.getInstance();
        this_year.setTime(date1);
        int thisYear = this_year.get(Calendar.YEAR);
        model.addAttribute("dat", "01-" + thisMonthStr + "-" + thisYear);

        Calendar calendar2 = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        List<Indicators> chartIndicators = indicatorsService.getAllByCounterSortAsc(counter);

        for (Indicators indicators1 : chartIndicators) {
            calendar.setTime(indicators1.getDate());
            int year = calendar.get(Calendar.YEAR);
            calendar2.setTime(date1);
            int date = calendar2.get(Calendar.YEAR);
            if(year == date) {
                str += "'" + indicators1.getConsumption() + "',";
//            note = st.substring(5, str.length()-3);
//            st +=
                cal.setTime(indicators1.getDate());
                int month = cal.get(Calendar.MONTH) + 1;
                st += "'" + month + "',";
            }
        }
        if(st.length()>1 && str.length()>1) {
            st = "[" + st.substring(0, st.length() - 1) + "]";
            System.out.println(st);
            str = str.substring(0, str.length() - 1);

            model.addAttribute("labels", st);
            model.addAttribute("data", str);
        }
        return "/indicators/edit";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.POST)
    public String editIndicatorsPost(Indicators indicators, Model model,
                                     @RequestParam("dat") String date) {


        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        model.addAttribute("h1name", "Редагувати показники");
        model.addAttribute("active", "active");
        try {
            indicators.setDate(docDate = format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Indicators indicator = indicatorsService.editIndicators(indicators);
        return "redirect:/indicator/edit/" + indicator.getCounters().getId();
    }

    @RequestMapping( value = "/compareIndicators", method = RequestMethod.GET)
    public String trackAllIndicatorsGET(Model model, HttpSession session) {
        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Порівняти показники");
        model.addAttribute("active", "active");
        model.addAttribute("date", 2018);
        model.addAttribute("counter1", "Лічильник 1");
        model.addAttribute("counter2", "Лічильник 2");
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        model.addAttribute("counters", counterService.getAll(new Sort(Sort.Direction.ASC, "id")));

        return "/indicators/trackAll";
    }

    @RequestMapping( value = "/compareIndicators", method = RequestMethod.POST)
    public String trackAllIndicatorsPOST(Model model,
                                         @RequestParam("counterId1") Long counterId1,
                                         @RequestParam("counterId2") Long counterId2,
                                         @RequestParam("date") int year,
                                         HttpSession session) {
        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        model.addAttribute("h1name", "Порівняти показники");
        model.addAttribute("active", "active");
        model.addAttribute("counters", counterService.getAll(new Sort(Sort.Direction.ASC, "id")));
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        Date today = new Date();

        String strIndicatorsCounter1 = new String();
        String stMonthCounter1 = new String();

        int minimalMonth = 13;
        int maximalMonth = 0;

        String strIndicatorsCounter2 = new String();
        String stMonthCounter2 = new String();



//        Calendar calToday = Calendar.getInstance();
//        calToday.setTime(today);
//        int year = calToday.get(Calendar.YEAR);

        Calendar yearCounter1 = Calendar.getInstance();
        Calendar yearCounter2 = Calendar.getInstance();
        Calendar monthCounter1 = Calendar.getInstance();
        Calendar monthCounter2 = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar22 = Calendar.getInstance();

        try {

        Counter counter1 = counterService.getById(counterId1);
        List<Indicators> chartIndicatorsCounter1 = indicatorsService.getAllByCounterSortAsc(counter1);

        Counter counter2 = counterService.getById(counterId2);
        List<Indicators> chartIndicatorsCounter2 = indicatorsService.getAllByCounterSortAsc(counter2);

        model.addAttribute("date", year);
        model.addAttribute("counter1", counter1.getNumber());
        model.addAttribute("counter2", counter2.getNumber());
//****************************************************************************************************
//**************************DATE***************************************************************************
//*****************************************************************************************************



            for (Indicators indicatorMonth1 : chartIndicatorsCounter1) {
                yearCounter1.setTime(indicatorMonth1.getDate());
                int yearCounterInt1 = yearCounter1.get(Calendar.YEAR);
                if (yearCounterInt1 == year) {
                    monthCounter1.setTime(indicatorMonth1.getDate());
                    int month = monthCounter1.get(Calendar.MONTH) + 1;
                    if (month < minimalMonth) {
                        minimalMonth = month;
                    }
                }
            }

            for (Indicators indicatorMonth2 : chartIndicatorsCounter2) {
                yearCounter2.setTime(indicatorMonth2.getDate());
                int yearCounterInt2 = yearCounter2.get(Calendar.YEAR);
                if (yearCounterInt2 == year) {
                    monthCounter2.setTime(indicatorMonth2.getDate());
                    int month2 = monthCounter2.get(Calendar.MONTH) + 1;
                    if (month2 < minimalMonth) {
                        minimalMonth = month2;
                    }
                }
            }

            for (Indicators indicatorMonth1 : chartIndicatorsCounter1) {
                yearCounter1.setTime(indicatorMonth1.getDate());
                int yearCounterInt1 = yearCounter1.get(Calendar.YEAR);
                if (yearCounterInt1 == year) {
                    monthCounter1.setTime(indicatorMonth1.getDate());
                    int month = monthCounter1.get(Calendar.MONTH) + 1;
                    if (month > maximalMonth) {
                        maximalMonth = month;
                    }
                }
            }

            for (Indicators indicatorMonth2 : chartIndicatorsCounter2) {
                yearCounter2.setTime(indicatorMonth2.getDate());
                int yearCounterInt2 = yearCounter1.get(Calendar.YEAR);
                if (yearCounterInt2 == year) {
                    monthCounter2.setTime(indicatorMonth2.getDate());
                    int month2 = monthCounter2.get(Calendar.MONTH) + 1;
                    if (month2 > maximalMonth) {
                        maximalMonth = month2;
                    }
                }
            }

            for (int i = minimalMonth; i <= maximalMonth; i++) {
                stMonthCounter1 += "'" + i + "',";
            }

            if (stMonthCounter1.length() > 1) {
                stMonthCounter1 = "[" + stMonthCounter1.substring(0, stMonthCounter1.length() - 1) + "]";
                System.out.println(stMonthCounter1);
            }
            model.addAttribute("labels", stMonthCounter1);

//*****************************************************************************************************
//**************************************DATA*************************************************************
//***************************************************************************************************


            Indicators indicators1 = chartIndicatorsCounter1.get(0);

            calendar.setTime(indicators1.getDate());
            int calMonth1 = calendar.get(Calendar.MONTH) + 1;
            if (calMonth1 > minimalMonth) {
                for (int i = minimalMonth; i < calMonth1; i++) {
                    strIndicatorsCounter1 += "'" + 0 + "',";
                }
            }


            for (Indicators indicators12 : chartIndicatorsCounter1) {
                yearCounter1.setTime(indicators12.getDate());
                int yearCounterInt1 = yearCounter1.get(Calendar.YEAR);
                if (yearCounterInt1 == year) {

                    strIndicatorsCounter1 += "'" + indicators12.getConsumption() + "',";
                }
            }

            for (int i = 1; i < 13; i++) {
                Indicators indicators11 = chartIndicatorsCounter1.get(chartIndicatorsCounter1.size() - i);
                yearCounter1.setTime(indicators11.getDate());
                int yearCounterInt1 = yearCounter1.get(Calendar.YEAR);
                if (yearCounterInt1 == year) {
                    calendar1.setTime(indicators11.getDate());
                    int calMonth11 = calendar1.get(Calendar.MONTH) + 1;
                    if (calMonth11 < maximalMonth) {
                        for (int a = calMonth11; a < maximalMonth; a++) {
                            strIndicatorsCounter1 += "'" + 0 + "',";
                        }
                        break;
                    }
                }
            }

            if (strIndicatorsCounter1.length() > 1) {
//            stMonthCounter1 = "[" + stMonthCounter1.substring(0, stMonthCounter1.length() - 1) + "]";
                System.out.println(strIndicatorsCounter1);
                strIndicatorsCounter1 = strIndicatorsCounter1.substring(0, strIndicatorsCounter1.length() - 1);

                model.addAttribute("data1", strIndicatorsCounter1);
                model.addAttribute("label1", counter1.getNumber());
            }

            Indicators indicators2 = chartIndicatorsCounter2.get(0);

            calendar2.setTime(indicators2.getDate());
            int calMonth2 = calendar2.get(Calendar.MONTH) + 1;
            if (calMonth2 > minimalMonth) {
                for (int i = minimalMonth; i < calMonth2; i++) {
                    strIndicatorsCounter2 += "'" + 0 + "',";
                }
            }

            for (Indicators indicator2 : chartIndicatorsCounter2) {
                yearCounter2.setTime(indicator2.getDate());
                int yearCounterInt2 = yearCounter2.get(Calendar.YEAR);
                if (yearCounterInt2 == year) {
                    strIndicatorsCounter2 += "'" + indicator2.getConsumption() + "'" + ",";
                }
            }
            for (int i = 1; i < 13; i++) {
                Indicators indicators22 = chartIndicatorsCounter2.get(chartIndicatorsCounter2.size() - i);

                yearCounter1.setTime(indicators22.getDate());
                int yearCounterInt1 = yearCounter1.get(Calendar.YEAR);
                if (yearCounterInt1 == year) {
                    calendar22.setTime(indicators22.getDate());
                    int calMonth22 = calendar22.get(Calendar.MONTH) + 1;
                    if (calMonth22 < maximalMonth) {
                        for (int a = calMonth22; a < maximalMonth; a++) {
                            strIndicatorsCounter2 += "'" + 0 + "',";
                        }
                        break;
                    }
                }
            }

            if (strIndicatorsCounter2.length() > 1) {
//            stMonthCounter2 = "[" + stMonthCounter2.substring(0, stMonthCounter2.length() - 1) + "]";
                System.out.println(strIndicatorsCounter2);
                strIndicatorsCounter2 = strIndicatorsCounter2.substring(0, strIndicatorsCounter2.length() - 1);

                model.addAttribute("data2", strIndicatorsCounter2);
                model.addAttribute("label2", counter2.getNumber());
            }
        }
        catch(NullPointerException e) {
            System.err.println("One of the object is empty");
            model.addAttribute("err" ,"One of the object is empty");
        }
        catch (IndexOutOfBoundsException ex) {
            System.err.println("One of the object is empty");
            model.addAttribute("err" ,"One of the object is empty");
        }
        return "/indicators/trackAll";
    }

    @RequestMapping( value = "/track/{counter.id}", method = RequestMethod.GET)
    public String trackIndicatorsGet(Model model, HttpSession session,
                                     @PathVariable("counter.id") Long counterId) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        Year year = new Year();
        model.addAttribute("years", year.getArrayYear());
        Counter counter = counterService.getById(counterId);
        model.addAttribute("h1name", "Переглянути показники: "+ counter.getNumber());
        List<Indicators> indicators = indicatorsService.getAllByCounter(counter, new Sort(Sort.Direction.DESC, "date"));
        model.addAttribute("indicators", indicators);
        model.addAttribute("counter", counter);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int thisYear = calendar.get(Calendar.YEAR);


        model.addAttribute("dataFirstYear", getIndicatorsByCounterAndYear(thisYear, counterId));
        model.addAttribute("dataSecondYear", getIndicatorsByCounterAndYear(thisYear - 1, counterId));
        model.addAttribute("dataThirdYear", getIndicatorsByCounterAndYear(thisYear - 2, counterId));
        model.addAttribute("dataFourthYear", getIndicatorsByCounterAndYear(thisYear - 3, counterId));
        model.addAttribute("dateFirstYear", thisYear);
        model.addAttribute("dateSecondYear", thisYear - 1);
        model.addAttribute("dateThirdYear", thisYear - 2);
        model.addAttribute("dateFourthYear", thisYear - 3);
        return "/indicators/track";
    }

    public List<Integer> getIndicatorsByCounterAndYear(int year, long counterId) {

        Counter counter = counterService.getById(counterId);

        int month = 0;

        List<Indicators> IndicatorsListChart = indicatorsService.getListIndicatorSortByCounterByDate(counter, year);
        List<Integer> IndicatorsListArr = new ArrayList<>();

         for (Indicators firstIndicators : IndicatorsListChart) {
            Calendar calendarIndicator = Calendar.getInstance();
            calendarIndicator.setTime(firstIndicators.getDate());
            int indicatorMonth = calendarIndicator.get(Calendar.MONTH);
            if (indicatorMonth == month) {
                IndicatorsListArr.add(firstIndicators.getConsumption());
                month++;
            } else {
                for (int i = 0; i < indicatorMonth; i++ ) {
                    IndicatorsListArr.add(0);
                    month++;
                }
                IndicatorsListArr.add(firstIndicators.getConsumption());
                month++;
            }
        }
        if (IndicatorsListArr.size() != 12) {
            for (int i = IndicatorsListArr.size(); i <= 11; i++ ) {
                IndicatorsListArr.add(0);
            }
        }

        return IndicatorsListArr;
    }

    @RequestMapping( value = "/track", method = RequestMethod.POST)
    public String trackIndicatorsPOST(Model model, HttpSession session,
                                      @RequestParam(value = "counterId", required = false) Long counterId,
                                      @RequestParam(value = "firstYear", required = false) int firstYear,
                                      @RequestParam(value = "secondYear") int  secondYear,
                                      @RequestParam(value = "thirdYear") int thirdYear,
                                      @RequestParam(value = "fourthYear") int fourthYear ) {

        List<Buttons> buttons = buttonsService.getAllWhereParentIdIsNull(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("buttons", buttons);
        List<Buttons> button = buttonsService.getAllWhereParentIdIsNotNull();
        model.addAttribute("button", button);
        Long currUserId = (Long)session.getAttribute("currUserId");
        User user = userService.getById(currUserId);
        model.addAttribute("userLogo", user.getName());
        Year year = new Year();
        model.addAttribute("years", year.getArrayYear());
        Counter counter = counterService.getById(counterId);
        model.addAttribute("h1name", "Переглянути показники: "+ counter.getNumber());

        if (firstYear != 0) {
            model.addAttribute("dataFirstYear", getIndicatorsByCounterAndYear(firstYear, counterId));
            model.addAttribute("dateFirstYear", firstYear);
        } if (secondYear != 0) {
            model.addAttribute("dataSecondYear", getIndicatorsByCounterAndYear(secondYear, counterId));
            model.addAttribute("dateSecondYear", secondYear);
        } if (thirdYear != 0) {
            model.addAttribute("dataThirdYear", getIndicatorsByCounterAndYear(thirdYear, counterId));
            model.addAttribute("dateThirdYear", thirdYear);
        } if (fourthYear != 0) {
            model.addAttribute("dataFourthYear", getIndicatorsByCounterAndYear(fourthYear, counterId));
            model.addAttribute("dateFourthYear", fourthYear);
        }

        model.addAttribute("counter", counter);

        return "/indicators/track";
    }
}
