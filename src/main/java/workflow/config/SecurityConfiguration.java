package workflow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

    }
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("admin").password("admin").roles("USER");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/web/ticket/view").hasRole("USER")
                .antMatchers("/web/ticket/search").hasRole("USER")
                .antMatchers("/web/ticket/bulkupload").hasRole("ADMIN")
                .antMatchers("/web/ticket/assignticket").hasRole("ADMIN")
                .antMatchers("/ticket/create").hasRole("ADMIN")
                .antMatchers("/ticket/upload").hasRole("ADMIN")
                .antMatchers("/ticket/getnextticket").hasRole("USER")
                .antMatchers("/ticket/bystatus").hasRole("ADMIN")
                .antMatchers("/ticket/byassignedto").hasRole("ADMIN")
                .antMatchers("/tickets").hasRole("ADMIN")
                .antMatchers("/ticket/update").hasRole("USER")
                .antMatchers("/ticket/delete").hasRole("ADMIN")
                .antMatchers("/ticket/complete").hasRole("ADMIN")
                .antMatchers("/admintasks").hasRole("ADMIN")
                .antMatchers("/createtickets").hasRole("ADMIN")
                .antMatchers("/createusers").hasRole("ADMIN")
                .antMatchers("/viewallusers").hasRole("ADMIN")
                .antMatchers("/viewalltickets").hasRole("ADMIN")
                .antMatchers("/web/user/userhomepage").hasRole("ADMIN")
                .antMatchers("/web/user/userassignticket").hasRole("USER")
                .antMatchers("/web/user/userviewtickets").hasRole("USER")
                .antMatchers("/web/user/bulkuploadtest").hasRole("ADMIN")
                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/user/create").hasRole("ADMIN")
                .antMatchers("/user/upload").hasRole("ADMIN")
                .antMatchers("/user/roleassignment").hasRole("ADMIN")
                .antMatchers("/user/byuserid").hasRole("ADMIN")
                .antMatchers("/user/update").hasRole("ADMIN")
                .antMatchers("/user/delete").hasRole("ADMIN")
                .antMatchers("/user/assignticket").hasRole("ADMIN")
                .antMatchers("/web/user/view").hasRole("ADMIN")
                .antMatchers("/web/user/bulkupload").hasRole("ADMIN")
                //.antMatchers( "/users").hasRole("USER")
                //.antMatchers( "/secondpage").hasAnyRole("ADMIN","USER")
                //.antMatchers( "/secure").hasAnyRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/mylogin").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/default",true)
                .and()
                .csrf()
                .disable()
                .logout()
                .permitAll();

    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();

}
