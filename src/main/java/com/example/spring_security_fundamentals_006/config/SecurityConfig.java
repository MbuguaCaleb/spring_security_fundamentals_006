package com.example.spring_security_fundamentals_006.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

   //very important because Spring Security Starts from the Filter
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic()
                .and()
                //.authorizeRequests(c-> c.anyRequest().authenticated()) (Alternative Approach)
                .authorizeRequests()
                //.anyRequest().authenticated() //any request means that all requests will take on the rule, which is not the case in many real world applications
                //.requestMatchers("mvcMatchers","/test1","/test3").authenticated() // i am going a step further to configure the authorization filter
                //.requestMatchers("mvcMatchers","/test2").hasAuthority("read") //any user who accesses any endpoint after authentication must have this authority
                //.requestMatchers("mvcMatchers","/demo/**").hasAuthority("read") //all endpoints with the prefix need to have the authority read (/demo/anything/*/test)
                //.requestMatchers(HttpMethod.GET, "mvcMatchers", "/demo/**").hasAuthority("read") // I can get more specific with ant matchers and the HTTP Verb
              //  .requestMatchers(HttpMethod.GET).hasAuthority("read")
                //.requestMatchers("antMatchers","/demo/demo1").authenticated() //antMatchers are a security vulnerability hence not recommended (They pose a threat if you are not specific
                .requestMatchers("regexMatchers","regexPattern").authenticated()
                .anyRequest().permitAll()
                .and().csrf().disable()//We Should never disable CSRF for real world applications
                .build();

        //*****ANT Expressions***/
        //demo/anything/*/something ---> /demo/anything/abc/something or /demo/anything/xyx/something (Must have the authority applied)
    }
    
    @Bean
    public UserDetailsService userDetailsService() {

        //Remember authorities must be stored in the server side
        //When a user is created they must have the authority
        InMemoryUserDetailsManager inMemoryUserDB = new InMemoryUserDetailsManager();
        UserDetails userOne = User.withUsername("Caleb")
                .password(passwordEncoder().encode("12345"))
                .authorities("read","write").build();
        UserDetails userTwo = User.withUsername("Mercy")
                .password(passwordEncoder().encode("12345"))
                .authorities("write").build();

        inMemoryUserDB.createUser(userOne);
        inMemoryUserDB.createUser(userTwo);

        return inMemoryUserDB;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
