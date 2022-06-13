package com.blog.server.blog.security;

import com.blog.server.blog.excpetion.BlogException;
import com.blog.server.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.blog.server.blog.excpetion.ErrorCode.USER_NOT_EXIST;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // 체크
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findByEmail(id)
                .orElseThrow(() -> new BlogException(USER_NOT_EXIST));
    }
}