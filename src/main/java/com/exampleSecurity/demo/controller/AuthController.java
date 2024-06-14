package com.exampleSecurity.demo.controller;


import com.exampleSecurity.demo.dto.AuthResponseDto;
import com.exampleSecurity.demo.dto.LoginRequestDto;
import com.exampleSecurity.demo.dto.RegisterRequestDto;
import com.exampleSecurity.demo.model.User;
import com.exampleSecurity.demo.service.AuthService;
import com.exampleSecurity.demo.service.BlackList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private BlackList list;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.ok(authService.register(registerRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            final String authHeader = request.getHeader("Authorization");
            var jwt = authHeader.substring(7);
            list.addToken(jwt);
            logoutHandler.logout(request, response, authentication);
        }
        return ResponseEntity.ok("Logout successfully");
    }
}
