package org.wogoo.moviereservationsystem.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wogoo.moviereservationsystem.domain.model.UserAccount;
import org.wogoo.moviereservationsystem.domain.repository.UserAccountRepository;
import org.wogoo.moviereservationsystem.security.CustomUserDetails;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    public CustomUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));


        return new CustomUserDetails(user);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserAccount user) {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName()));
    }

}