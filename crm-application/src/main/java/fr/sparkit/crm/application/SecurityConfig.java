package fr.sparkit.crm.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final long MAX_AGE = 3600L;

    // SecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler,
    // JwtAccessDeniedHandler accessDeniedHandler,
    // UserDetailsService userDetailsService, JwtAuthenticationTokenFilter
    // authenticationTokenFilter) {
    // this.userDetailsService = userDetailsService;
    // }

    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //
    // CorsConfiguration configuration = new CorsConfiguration();
    // configuration.setAllowedOrigins(Arrays.asList("*"));
    // configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
    // "DELETE", "OPTIONS"));
    // configuration.setAllowedHeaders(Arrays.asList("authorization",
    // "content-type"));
    // configuration.setMaxAge(MAX_AGE);
    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // source.registerCorsConfiguration("/**", configuration);
    // return source;
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception
    // {
    //
    // ReflectionSaltSource rss = new ReflectionSaltSource();
    // rss.setUserPropertyToUse("salt");
    // DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    // provider.setSaltSource(rss);
    // provider.setUserDetailsService(userDetailsService);
    // provider.setPasswordEncoder(passwordEncoder());
    // auth.authenticationProvider(provider);
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        // .accessDeniedHandler(accessDeniedHandler).and().sessionManagement()
        //
        // .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        // .antMatchers(HttpMethod.GET, "/", "/*.html", "/favicon.png", "/favicon.ico",
        // "/**/*.html", "/**/*.woff",
        // "/**/*.woff2", "/**/*.ttf", "/**/*.png", "/**/*.css", "/**/*.js",
        // "/**/*.jpg")
        // .permitAll().antMatchers("/authentication-manager/**").permitAll().antMatchers("/version").permitAll()
        // .antMatchers("/updatePassword/**").permitAll().antMatchers("/opportinity-management/reset-password**")
        // .permitAll().antMatchers("/line**").permitAll().antMatchers("/api/opportinity/**").permitAll()
        // .antMatchers("/**").authenticated().anyRequest().authenticated();
        // http.cors().and().addFilterBefore(authenticationTokenFilter,
        // UsernamePasswordAuthenticationFilter.class)
        // .headers().cacheControl();
        http.csrf().disable().authorizeRequests().anyRequest().permitAll();

    }

}
