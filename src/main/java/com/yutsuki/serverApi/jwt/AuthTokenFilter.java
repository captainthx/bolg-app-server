package com.yutsuki.serverApi.jwt;

import com.yutsuki.serverApi.service.TokenService;
import com.yutsuki.serverApi.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Resource
    private TokenService tokenService;

    private final UserDetailService userDetailsService;

    public AuthTokenFilter(UserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headToken = getJwtFromRequest(request);
        if (StringUtils.hasText(headToken) && tokenService.validates(headToken)) {
            String username = tokenService.getUsername(headToken);
            UserDetailsImp userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        logger.debug("AuthTokenFilter-[doFilterInternal]-[filterChain.doFilter]");
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authToken) && authToken.startsWith("Bearer")) {
            return authToken.substring(7);
        }
        return null;
    }
}

