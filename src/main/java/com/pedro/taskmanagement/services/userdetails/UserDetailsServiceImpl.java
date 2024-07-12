package com.pedro.taskmanagement.services.userdetails;

import com.pedro.taskmanagement.domain.user.User;
import com.pedro.taskmanagement.domain.user.UserDetailsImpl;
import com.pedro.taskmanagement.exception.ObjectNotFoundException;
import com.pedro.taskmanagement.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username).orElseThrow(() -> new ObjectNotFoundException("User Not Found!"));
        return new UserDetailsImpl(user);
    }
}
