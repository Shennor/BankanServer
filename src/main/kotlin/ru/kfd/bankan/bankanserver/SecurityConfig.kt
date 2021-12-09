package ru.kfd.bankan.bankanserver

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.JdbcUserDetailsManager
import ru.kfd.bankan.bankanserver.service.UserDetailsServiceImpl
import javax.sql.DataSource

@EnableWebSecurity
class SecurityConfig(
    val dataSource: DataSource,
    val userDetailsService: UserDetailsServiceImpl
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .anyRequest().authenticated()
            .mvcMatchers(
                HttpMethod.GET,
                "/registration",
                "/login",
                "/about",
                "/board/*/public/**"
            ).permitAll()
            .and().formLogin()
            .loginPage("/login")
            .failureUrl("/login/fail")
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(userDetailsService)
            .passwordEncoder(encoder())
    }

    private fun encoder(): PasswordEncoder? {
        TODO("Not yet implemented")
    }
}
