package com.cardinal.tech.bzfx.config.web;

import com.cardinal.tech.bzfx.util.UserPasswordEncoder;
import com.cardinal.tech.bzfx.exceptions.JwtAuthenticationEntryPoint;
import com.cardinal.tech.bzfx.exceptions.UserAccessDeniedHandler;
import com.cardinal.tech.bzfx.service.impl.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final UserAccessDeniedHandler amsAccessDeniedHandler;
    private final FilterIgnoreUrlConfig filterIgnoreUrlConfig;
    private final JwtAuthorizationTokenFilter authenticationTokenFilter;

    public SecurityConfig(JwtUserDetailsService jwtUserDetailsService, JwtAuthenticationEntryPoint unauthorizedHandler, UserAccessDeniedHandler amsAccessDeniedHandler, FilterIgnoreUrlConfig filterIgnoreUrlConfig, JwtAuthorizationTokenFilter authenticationTokenFilter) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.amsAccessDeniedHandler = amsAccessDeniedHandler;
        this.filterIgnoreUrlConfig = filterIgnoreUrlConfig;
        this.authenticationTokenFilter = authenticationTokenFilter;
    }

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new UserPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // ??????????????????????????????
        http.exceptionHandling().accessDeniedHandler(amsAccessDeniedHandler).authenticationEntryPoint(unauthorizedHandler).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // ??????????????????????????????
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        // ??????options ????????????????????????
        registry.antMatchers(HttpMethod.OPTIONS, "/**").anonymous();
        // ???????????????????????????????????????????????????url
        filterIgnoreUrlConfig.getUrls().parallelStream().forEach(url -> registry.antMatchers(url).permitAll());
        // ????????????????????????????????????????????????url
        filterIgnoreUrlConfig.getAuthenticates().parallelStream().forEach(url -> registry.antMatchers(url).authenticated());
        // ????????????????????????????????????????????????????????????api_url ???????????????
//        registry.anyRequest().access("@PermissionService.hasPermission(request,authentication)");
        registry.anyRequest().authenticated();
        // token ???????????????security ?????????????????????????????????
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * ??????token filter ???????????????
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                );
    }
}
