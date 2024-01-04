package me.liskoh.counter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.liskoh.counter.entities.UserEntity;
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
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    /* Constants */
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    /* Fields */
    private final JwtService jwtService;
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        log.info("Filtering request: {}", request.getRequestURI());
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!StringUtils.startsWith(authorizationHeader, BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        final long now = System.currentTimeMillis();
        final String token = StringUtils.removeStart(authorizationHeader, BEARER);
        log.info("Token: {}", token);
        try {
            if (jwtService.isExpired(token, now)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                return;
            }

            final String username = jwtService.getUsername(token);
            if (StringUtils.isBlank(username) || SecurityContextHolder.getContext().getAuthentication() != null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }

            final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authenticationToken.setDetails(userDetails);
            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);
            filterChain.doFilter(request, response);

            userDetails.getAuthorities().forEach(authority -> log.info("Authority: {}", authority.getAuthority()));
            log.info("Filtering request: {}", request.getRequestURI());
        } catch (Exception e) {
            log.error("Error while authenticating token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }
    }
}
