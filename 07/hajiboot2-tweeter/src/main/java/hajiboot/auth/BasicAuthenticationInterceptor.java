package hajiboot.auth;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hajiboot.tweeter.Tweeter;
import hajiboot.tweeter.TweeterMapper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BasicAuthenticationInterceptor implements HandlerInterceptor {
	private final TweeterMapper tweeterMapper;

	public BasicAuthenticationInterceptor(TweeterMapper tweeterMapper) {
		this.tweeterMapper = tweeterMapper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if ("GET".equals(request.getMethod())) {
			return true;
		}
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (!StringUtils.hasText(authorization)) {
			response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Tweeter API\", charset=\"UTF-8\"");
			response.sendError(HttpStatus.FORBIDDEN.value());
			return false;
		}
		Tweeter authenticated = this.authenticate(authorization);
		if (authenticated == null) {
			response.sendError(HttpStatus.UNAUTHORIZED.value());
			return false;
		}
		request.setAttribute("tweeter", authenticated);
		return true;
	}

	Tweeter authenticate(String authorization) {
		String usernameAndPasswordStringBase64 = authorization.replace("Basic ", "");
		String usernameAndPasswordString = new String(Base64Utils.decodeFromString(usernameAndPasswordStringBase64));
		String[] usernameAndPassword = usernameAndPasswordString.split(":", 2);
		try {
			Tweeter tweeter = this.tweeterMapper.findByUsername(usernameAndPassword[0]);
			String encodedPassword = "{noop}" + usernameAndPassword[1]; // TODO Use PasswordEncoder
			if (Objects.equals(tweeter.getPassword(), encodedPassword)) {
				return tweeter;
			}
			else {
				return null;
			}
		}
		catch (EmptyResultDataAccessException ignored) {
			return null;
		}
	}
}
