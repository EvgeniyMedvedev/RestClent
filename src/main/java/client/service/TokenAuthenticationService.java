package client.service;

import client.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;


public class TokenAuthenticationService {

    static final long EXPIRATIONTIME = 864_000_000; // 10 days

    static final String SECRET = "ThisIsASecret";

    static final String TOKEN_PREFIX = "Bearer";

    static final String HEADER_STRING = "Authorization";

    static SpringRestClient client = SpringRestClient.getInstance();

    public static Authentication getAuthentication(String token) {
        System.out.println("TokenAuthenticationService.getAuthentication " + token);
        if (token != null) {
            // parse the token.
            String login = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
                    .getSubject();
            User user = client.getUserByLogin(login);
            System.out.println("TokenAuthenticationService.getAuthentication " + user);

            return user != null ? new UsernamePasswordAuthenticationToken(user, "", user.getRoles()) : null;
        }
        return null;
    }

}
