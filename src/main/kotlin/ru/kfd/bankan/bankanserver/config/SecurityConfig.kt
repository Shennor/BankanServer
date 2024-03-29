package ru.kfd.bankan.bankanserver.config

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.kfd.bankan.bankanserver.config.auth.AuthEntryPointJwt
import ru.kfd.bankan.bankanserver.config.auth.AuthTokenFilter
import ru.kfd.bankan.bankanserver.service.UserDetailsServiceImpl


@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsServiceImpl,
    private val unauthorizedHandler: AuthEntryPointJwt,
    private val authTokenFilter: AuthTokenFilter,
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.cors().and()
            .csrf().disable()
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

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
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

//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource {
//        val configuration = CorsConfiguration()
//        configuration.allowedOrigins = listOf("https://example.com")
//        configuration.allowedMethods = listOf("GET", "POST")
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", configuration)
//        return source
//    }
}

//@EnableWebMvc
//@Component
//class WebMvcConfig : WebMvcConfigurer {
//    override fun addCorsMappings(registry: CorsRegistry) {
//        registry.addMapping("/**")
//            .allowedOrigins("*")//"http://localhost:8080")
//            .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS")
//            .allowCredentials(true)
//    }
//
//    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(AccessControlInterceptor()).addPathPatterns("/**")
//    }
//}
