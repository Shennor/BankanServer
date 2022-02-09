package ru.kfd.bankan.bankanserver.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.kfd.bankan.bankanserver.AuthEntryPointJwt
import ru.kfd.bankan.bankanserver.AuthTokenFilter
import ru.kfd.bankan.bankanserver.JwtUtils
import ru.kfd.bankan.bankanserver.service.UserDetailsServiceImpl


@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userDetailsService: UserDetailsServiceImpl

    @Autowired
    private val unauthorizedHandler: AuthEntryPointJwt? = null

    @Bean
    fun authenticationJwtTokenFilter(): AuthTokenFilter? {
        var jwtUtils = JwtUtils()
        return AuthTokenFilter(jwtUtils, userDetailsService)
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests()
            .mvcMatchers(
                HttpMethod.GET,
                "/registration",
                "/login",
                "/about",
                "/board/*/public/**",
                "/"
            ).permitAll()
            .mvcMatchers(
                HttpMethod.POST,
                "/api/auth/signin",
                "/api/auth/signup",
            ).permitAll()
            .anyRequest().authenticated()

        http.formLogin()
            //.loginPage("/login")
            //.failureUrl("/login/fail")
            .and().logout()
            .logoutSuccessUrl("/home")

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)

    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }
}
