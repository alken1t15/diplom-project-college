package kz.alken1t15.backratinglogcollege.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilter implements Filter {

    //extends OncePerRequestFilter

  //  private UserDetailsServiceImpl userDetailsService;
    private JWTUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        JwtParser jwtParser = Jwts.parser().setSigningKey("alex0410");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String bearerToken = request.getHeader("TOKEN_HEADER");
        if (bearerToken != null){
            Claims claims = jwtParser.parseClaimsJws(bearerToken).getBody();
        System.out.println(claims.getSubject());
        System.out.println("filter");
    }
        System.out.println(request.getHeader("Authorization"));
        filterChain.doFilter(request, response);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        System.out.println(request);
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
//            String jwt = authHeader.substring(7);
//            if (jwt == null || jwt.isBlank()) {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
//            } else {
//                try {
//                    String login = jwtUtil.validateTokenAndRetrieveSubject(jwt);
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(login);
//                    UsernamePasswordAuthenticationToken authToken =
//                            new UsernamePasswordAuthenticationToken(login, userDetails.getPassword(), userDetails.getAuthorities());
//                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
//                        SecurityContextHolder.getContext().setAuthentication(authToken);
//                    }
//                } catch (JWTVerificationException exc) {
//                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
//                }
//            }
//        }

       // filterChain.doFilter(request, response);

}