package kz.alken1t15.backratinglogcollege.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import kz.alken1t15.backratinglogcollege.dto.LoginAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Date;


@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;
    @Value("${jwt.time}")
    private Integer jtwTime;

    public String generateToken(String login, String password) throws IllegalArgumentException, JWTCreationException {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(jtwTime);
        return JWT.create()
                .withSubject("User Details")
                .withClaim("login", login)
                .withClaim("password", password)
                .withClaim("date", localDate.toString())
                .withIssuedAt(new Date())
                .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
                .sign(Algorithm.HMAC256(secret));
    }

    public LoginAuth validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
                .build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            String temp = jwt.getClaim("date").toString().replace("\"", "");
            LocalDate localDate = LocalDate.parse(temp);

            return new LoginAuth(jwt.getClaim("login").asString(), jwt.getClaim("password").asString(), localDate);
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}