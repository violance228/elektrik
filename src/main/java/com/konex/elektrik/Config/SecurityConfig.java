package com.konex.elektrik.Config;

import com.konex.elektrik.Entity.ConnectionLog;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Service.ConnectionLog.ConnectionLogService;
import com.konex.elektrik.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final static Logger log = Logger.getLogger(SecurityConfig.class.getName());

    @Qualifier("userDetailsImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ConnectionLogService connectionLogService;
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(
                         "/user/login").permitAll()
                .antMatchers("/user/**", "/order/**","/subdivision/**", "/transportOrder/addToTravel/**", "/transportOrder/addToTravel")
                        .access("hasAnyRole('ROLE_USER', 'ROLE_CHEF', 'ROLE_REGULAR', 'ROLE_ADMIN')")
                .antMatchers( "/assignment/**", "/admin/order/**", "/admin/subdivision/track/**",
                        "/admin/user/registration", "/admin/user/trackAll", "/admin/assignment/**", "/admin/assignment/connectionLog",
                        "/counter/trackAll", "/indicator/track/**", "/indicator/track", "/orderComment/**",
                        "/stuff/track/**", "/stuff/trackAllStuffBySubdivision/**", "/stuff/trackAllStuff", "/stuff/trackAllStuffByStuffTypes/**",
                        "/stuffType/trackAll", "/transportOrder/trackCar/**", "/transportOrder/trackAllCar", "/transportOrder/trackTransportOrder/**",
                        "/transportOrder/trackAllTransportOrder", "/transportOrder/addToTravel/**", "/transportOrder/addToTravel")
                        .access("hasAnyRole('ROLE_REGULAR', 'ROLE_CHEF', 'ROLE_ADMIN')") //технічний працівник
                .antMatchers("/counter/statistics", "/counter/statisticsBySubdivision", "/counter/averageStatisticByYear",
                        "/indicator/compareIndicators")
                .access("hasAnyRole('ROLE_CHEF', 'ROLE_ADMIN')")
                .antMatchers("/**")
                        .access("hasRole('ROLE_ADMIN')")
//                .antMatchers("/manufacturer/**", "/indicators/**","/counter/**",
//                        "/assignment/**", "")
//                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .successHandler(successHandler)
//                .failureHandler(failureHandler)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/login?logout")
                .permitAll();
        http.exceptionHandling()
                .accessDeniedPage("/user/access-denied?error");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    private final AuthenticationSuccessHandler successHandler = new AuthenticationSuccessHandler() {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Authentication auth) throws IOException, ServletException {
            HttpSession session = request.getSession();
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername());
            request.getSession().setAttribute("currUserId", user.getId());
            request.getSession().setAttribute("currUsername", user.getUsername());
            request.getSession().setAttribute("currUserIp", request.getRemoteAddr());

            ConnectionLog connectionLog = new ConnectionLog();
            connectionLog.setIp(request.getRemoteAddr());
            try {
                connectionLogService.addConnectionLog(connectionLog, user);
            } catch (Exception e) {
                log.warning("can't add connection " + e + " for user " + user.getUsername());
            }

            response.setStatus(HttpServletResponse.SC_OK);
            log.info(response.getHeaderNames().toString());
            String url = request.getContextPath() + "/user/personalInform";
            response.sendRedirect(url);

    System.out.println("--------------------------------" + user.getId());
    System.out.println("--------------------------------" + user.getPassword());
    System.out.println("--------------------------------" + request.getRemoteAddr());
        }
    };

    /**
     * Returns authentication error in JSON format.
     */
    private final AuthenticationFailureHandler failureHandler = new AuthenticationFailureHandler() {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException exception) throws IOException, ServletException {
          //System.out.println("msg="+exception.getMessage());
            //exception.printStackTrace();

            //            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            //            response.setContentType(MediaType.APPLICATION_JSON.toString());
            //
            //            PrintWriter out = response.getWriter();
            //
            //            mapper.writeValue(out, new ErrorResponse("/login", "Authentication failed"));
            //            out.flush();

            HttpSession session = request.getSession(false);
//            ThreadContext.put("USER", request.getRemoteUser());
//            log.info("ACCESS DENIED");
//            ThreadContext.pop();
//            ThreadContext.clearStack();

            request.getSession().setAttribute("error", "Логін або пароль не вірні");
            String url = null;
            //            System.out.println("Session: " + session);
            if (session != null) {
                url = (String) request.getSession().getAttribute("url_prior_login");
                //                session.removeAttribute("url_prior_login");
            }
            System.out.println("Referrer URL: " + url);
            if (url != null) {
                response.sendRedirect("/login");
            }
        }
    };
}
