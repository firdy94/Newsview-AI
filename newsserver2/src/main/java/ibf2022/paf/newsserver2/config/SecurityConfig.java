package ibf2022.paf.newsserver2.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${auth0.audience}")
	private String audience;

	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuer;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(corsConfigurationSource()).and().csrf().and().authorizeRequests()
				// .mvcMatchers(HttpMethod.GET, "/api/*").permitAll() // GET requests
				// // don't need auth
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/assets/manifest.json").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/favicon/**").permitAll()
				.antMatchers("/js/**").permitAll()
				// // .antMatchers("/assets/**").permitAll()

				.mvcMatchers(HttpMethod.GET, "/").permitAll()

				.mvcMatchers(HttpMethod.GET, "/api/private").authenticated()
				// secure api with admin-scope
				.mvcMatchers(HttpMethod.GET, "/api/private-scoped").hasAuthority("SCOPE_admin")
				.anyRequest()
				.permitAll()
				.and()
				.oauth2ResourceServer()
				.jwt();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration
				.setAllowedOrigins(Arrays.asList("https://newsview-ai-app.herokuapp.com",
						"http://localhost:8080"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	JwtDecoder jwtDecoder() {
		OAuth2TokenValidator<Jwt> withAudience = new AudienceValidator(audience);
		OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
		OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(withAudience, withIssuer);

		NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuer);
		jwtDecoder.setJwtValidator(validator);
		return jwtDecoder;
	}
}
