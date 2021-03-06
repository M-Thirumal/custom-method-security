/**
 * 
 */
package in.co.thirumal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

/**
 * @author Thirumal
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/login").permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().and().logout().permitAll();
	}*/

	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	    DefaultHttpFirewall firewall = new DefaultHttpFirewall();
	    firewall.setAllowUrlEncodedSlash(true);
	    return firewall;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.httpFirewall(allowUrlEncodedSlashHttpFirewall());	  
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  http
			.csrf().disable()
	    	.httpBasic().and()
	    	.authorizeRequests().antMatchers("/actuator/**").hasAnyRole("ACTUATOR")
	    	.anyRequest().authenticated();
	}
	
	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
	                .withUser("thirumal").password("$2a$11$WWZlUCd4XndWGpriAx7Pv.HpZ042awTnlAKr9VDiN9xEdPNS1Xy1q").roles("LEAD")//.roles("ACTUATOR")
	                .and().withUser("s").password("$2a$11$WWZlUCd4XndWGpriAx7Pv.HpZ042awTnlAKr9VDiN9xEdPNS1Xy1q").roles("S")//.roles("ACTUATOR")
	                .and().withUser("v").password("$2a$11$WWZlUCd4XndWGpriAx7Pv.HpZ042awTnlAKr9VDiN9xEdPNS1Xy1q").roles("VIEWER")//.roles("ACTUATOR")
	                .and().withUser("t").password("$2a$11$WWZlUCd4XndWGpriAx7Pv.HpZ042awTnlAKr9VDiN9xEdPNS1Xy1q").roles("ADMIN");///.roles("ACTUATOR");
	 }
	 
	 @Bean 
	 public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder();
	}
	
}
