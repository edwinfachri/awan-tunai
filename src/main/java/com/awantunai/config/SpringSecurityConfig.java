// package com.awantunai.config;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.builders.WebSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
// @Configuration
// @EnableWebSecurity
// public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
// 	@Autowired
// 	private AuthenticationEntryPoint authEntryPoint;
//
// 	@Override
// 	protected void configure(HttpSecurity http) throws Exception {
// 		http.csrf().disable().authorizeRequests()
// 				.anyRequest().authenticated()
// 				.and().httpBasic()
// 				.authenticationEntryPoint(authEntryPoint);
// 	}
//
//   // @Override
//   // protected void configure(HttpSecurity http) throws Exception {
//   //   http.authorizeRequests()
//   //   .antMatchers("/admin/**").hasRole("ADMIN")
//   //   .anyRequest().fullyAuthenticated()
//   //   .and()
//   //   .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
//   //   .and()
//   //   .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
//   // }
//
// 	@Autowired
// 	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// 		auth.inMemoryAuthentication().withUser("john123").password("password").roles("USER");
// 	}
//
// }
