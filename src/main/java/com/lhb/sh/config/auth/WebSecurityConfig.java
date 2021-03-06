package com.lhb.sh.config.auth;

import com.lhb.sh.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.Resource;

/*
please see https://blog.csdn.net/neweastsun/article/details/79824019
 */
@Slf4j
@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private CustomerAuthenticationProvider authProvider;

    @Resource
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("config springsecurity");
        http.authorizeRequests()
                .antMatchers("/css/*", "/js/*", "/fonts/*", "/images/*").permitAll()
                .antMatchers("/register", "/login","/", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    response.sendRedirect("/home");
                })
                // .defaultSuccessUrl("/home");
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("begin configure the authProvider");
        auth.authenticationProvider(authProvider);
    }

    //    @Override
    //    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    //        //基于内存来存储用户信息
    //        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
    //                .withUser("user").password(new BCryptPasswordEncoder().encode("123")).roles("USER").and()
    //                .withUser("admin").password(new BCryptPasswordEncoder().encode("456")).roles("USER","ADMIN");
    //    }
    //

    //    @Bean
    //    public UserDetailsService userDetailsService() {    //用户登录实现
    //        return new UserDetailsService() {
    //            @Autowired
    //            private UserService userService;
    //
    //            @Override
    //            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    //                User user = userService.getUserByUsername(s);
    //                if (user == null) throw new UsernameNotFoundException("Username " + s + " not found");
    //                return new SecurityUser(user);
    //            }
    //        };
    //    }
}

