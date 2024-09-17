package org.wogoo.moviereservationsystem.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.wogoo.moviereservationsystem.controller.dto.UpdatePasswordDto;
import org.wogoo.moviereservationsystem.controller.dto.UserProfileDto;
import org.wogoo.moviereservationsystem.domain.model.UserAccount;
import org.wogoo.moviereservationsystem.domain.repository.UserAccountRepository;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    private final UserAccountRepository userAccountRepository;

    private final PasswordEncoder passwordEncoder;

    public UserProfileController(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {

        String username = authentication.getName();

        UserAccount user = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + username));

        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(user.getId());
        userProfileDto.setUsername(user.getUsername());
        userProfileDto.setEmail(user.getEmail());
        userProfileDto.setRole(user.getRole().getName());

        return ResponseEntity.ok(userProfileDto);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(
            @Valid @RequestBody UserProfileDto userProfileDto,
            Authentication authentication
    ) {

        String username = authentication.getName();

        UserAccount user = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + username));

        user.setEmail(userProfileDto.getEmail());

        userAccountRepository.save(user);

        return ResponseEntity.ok("Perfil atualizado com sucesso!");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody UpdatePasswordDto updatePasswordDto,
            Authentication authentication
    ) {

        String username = authentication.getName();

        UserAccount user = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + username));

        if (!passwordEncoder.matches(updatePasswordDto.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Senha atual incorreta");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));

        userAccountRepository.save(user);

        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }
}