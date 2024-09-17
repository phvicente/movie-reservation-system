package org.wogoo.moviereservationsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wogoo.moviereservationsystem.controller.dto.LoginRequest;
import org.wogoo.moviereservationsystem.controller.dto.LoginResponse;
import org.wogoo.moviereservationsystem.controller.dto.SignUpRequest;
import org.wogoo.moviereservationsystem.domain.model.Role;
import org.wogoo.moviereservationsystem.domain.model.UserAccount;
import org.wogoo.moviereservationsystem.domain.repository.RoleRepository;
import org.wogoo.moviereservationsystem.domain.repository.UserAccountRepository;
import org.wogoo.moviereservationsystem.security.CustomUserDetails;
import org.wogoo.moviereservationsystem.security.JwtTokenUtil;
import org.wogoo.moviereservationsystem.service.impl.CustomUserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final UserAccountRepository userAccountRepository;

    private final RoleRepository roleRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final CustomUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, UserAccountRepository userAccountRepository, RoleRepository roleRepository, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userAccountRepository = userAccountRepository;
        this.roleRepository = roleRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken((CustomUserDetails) userDetails);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {

        if (userAccountRepository.existsByUsername(signUpRequest.username())) {
            return ResponseEntity
                    .badRequest()
                    .body("Erro: Nome de usuário já existe!");
        }

        if (userAccountRepository.existsByEmail(signUpRequest.email())) {
            return ResponseEntity
                    .badRequest()
                    .body("Erro: Email já está em uso!");
        }

        UserAccount user = new UserAccount();
        user.setUsername(signUpRequest.username());
        user.setEmail(signUpRequest.email());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Erro: Papel não encontrado."));
        user.setRole(userRole);

        userAccountRepository.save(user);

        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

}
