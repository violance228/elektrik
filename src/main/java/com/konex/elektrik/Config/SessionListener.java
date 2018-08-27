package com.konex.elektrik.Config;

import com.konex.elektrik.Const.ProjectVersion;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {

    private static int numberOfUsersOnline = 0;

    ProjectVersion projectVersion = new ProjectVersion();

    public static int getNumberOfUsersOnline() {
        return numberOfUsersOnline;
    }

//    public SessionListener() {
//        numberOfUsersOnline = 0;
//    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {

        System.out.println("Session created by Id : " + event.getSession().getId());
        synchronized (this) {
            numberOfUsersOnline++;

        }
    }

    @Override
    public void sessionDestroyed (HttpSessionEvent event){
        System.out.println("Session destroyed by Id : " + event.getSession().getId());
        synchronized (this) {
            numberOfUsersOnline--;
//            if (numberOfUsersOnline == 0 && projectVersion != )
        }
    }
}
