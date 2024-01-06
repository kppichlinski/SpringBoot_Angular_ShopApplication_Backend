package com.example.shop.security;

import com.example.shop.security.dto.ShopUserDetails;
import com.example.shop.security.model.User;
import com.example.shop.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public ShopUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow();
        return getUserDetails(user);
    }

    @Transactional
    public ShopUserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return getUserDetails(user);
    }

    private static ShopUserDetails getUserDetails(User user) {
        List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(userRole -> (GrantedAuthority) userRole::name)
                .toList();

        ShopUserDetails shopUserDetails = new ShopUserDetails(user.getUsername(), user.getPassword(), authorities);
        shopUserDetails.setId(user.getId());

        return shopUserDetails;
    }
}
