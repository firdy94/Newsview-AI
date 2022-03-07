package ibf2022.paf.newsserver2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

	// // @Override
	// // public void addViewControllers(ViewControllerRegistry registry) {
	// // registry.addRedirectViewController("/", "index.html");
	// // }

	// @Override
	// public void addResourceHandlers(ResourceHandlerRegistry registry) {
	// if (!registry.hasMappingForPattern("static/**")) {
	// registry.addResourceHandler("static/**")
	// .addResourceLocations("static/");
	// }
	// }

}
