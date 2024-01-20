package me.liskoh.counter.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.liskoh.counter.services.JwtService;
import me.liskoh.counter.services.UserDetailsServiceImpl;
import me.liskoh.counter.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    /* Constants */
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String AUTH_PATH = "/api/v1/auth";

    /* Fields */
    private final JwtService jwtService;
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        log.info("Filtering request: {}", request.getServletPath());

        /* Allow authentication requests */
        if (request.getServletPath().contains(AUTH_PATH)) {
            log.info("Allowing authentication request");
            filterChain.doFilter(request, response);
            return;
        }

        /* Check if request has authorization header */
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!StringUtils.startsWith(authorizationHeader, BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        /* Check if token is expired, has a valid username and if user is already authenticated */
        final long now = System.currentTimeMillis();
        final String token = StringUtils.removeStart(authorizationHeader, BEARER);
        final Claims claims = jwtService.getClaims(token);
        final boolean expired = jwtService.isExpired(claims, token, now);
        final String username = jwtService.getUsername(claims, token);
        if (expired || StringUtils.isBlank(username) || SecurityContextHolder.getContext().getAuthentication() != null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        /* Check if user exists */
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        /* Set authentication token and context */
        authenticationToken.setDetails(userDetails);
        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(securityContext);
        filterChain.doFilter(request, response);

//        userDetails.getAuthorities().forEach(authority -> log.info("Authority: {}", authority.getAuthority()));
    }
}
