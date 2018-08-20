package com.konex.elektrik.Const;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Year {

    public List<Integer> getArrayYear() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int thisYear = calendar.get(Calendar.YEAR);
        List<Integer> yearList = new ArrayList<>();

        for (int i = 0; i <= thisYear - 2010; i++) {
            yearList.add(2010 + i);
        }
        return yearList;
    }
}
