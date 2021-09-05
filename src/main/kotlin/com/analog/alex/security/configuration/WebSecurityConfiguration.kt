package com.analog.alex.security.configuration

import com.analog.alex.security.authentication.jwt.filter.JwtFilter
import com.analog.alex.security.authentication.provider.ApplicationAuthenticationProvider
import com.analog.alex.security.authentication.provider.RestAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    private val applicationAuthenticationProvider: ApplicationAuthenticationProvider,
    private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.addFilterAfter(JwtFilter(), BasicAuthenticationFilter::class.java)
        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET,    "/products").hasAnyRole("ADMIN", "BASIC")
                .antMatchers(HttpMethod.POST,   "/products").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint)

    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(applicationAuthenticationProvider)
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}