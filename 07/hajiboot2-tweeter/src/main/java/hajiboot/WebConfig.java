package hajiboot;

import hajiboot.auth.BasicAuthenticationInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private final BasicAuthenticationInterceptor basicAuthenticationInterceptor;

	public WebConfig(BasicAuthenticationInterceptor basicAuthenticationInterceptor) {
		this.basicAuthenticationInterceptor = basicAuthenticationInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(basicAuthenticationInterceptor)
				.excludePathPatterns("/error", "/tweeters");
	}
}
