package com.exampleSecurity.demo.service;


import com.exampleSecurity.demo.dto.AuthResponseDto;
import com.exampleSecurity.demo.dto.LoginRequestDto;
import com.exampleSecurity.demo.dto.RegisterRequestDto;
import com.exampleSecurity.demo.model.User;
import com.exampleSecurity.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDto login(LoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserDetails user = userDetailService.loadUserByUsername(authentication.getName());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var jwt = jwtService.generateToken(authentication);
            return AuthResponseDto.builder()
                    .token(jwt)
                    .user(user)
                    .build();
        } catch (AuthenticationException e) {

            return AuthResponseDto.builder()
                    .token("Không hợp lệ")
                    .build();
        }
    }

    public User register(RegisterRequestDto request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        return user;
    }
}
