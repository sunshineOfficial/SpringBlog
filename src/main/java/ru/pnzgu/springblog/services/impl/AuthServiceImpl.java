package ru.pnzgu.springblog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pnzgu.springblog.dto.auth.LoginRequest;
import ru.pnzgu.springblog.dto.auth.LoginResponse;
import ru.pnzgu.springblog.dto.auth.RegisterRequest;
import ru.pnzgu.springblog.exceptions.EntityAlreadyExistsException;
import ru.pnzgu.springblog.exceptions.EntityNotFoundException;
import ru.pnzgu.springblog.models.UserEntity;
import ru.pnzgu.springblog.repositories.RoleRepository;
import ru.pnzgu.springblog.repositories.UserRepository;
import ru.pnzgu.springblog.helpers.AuthFacade;
import ru.pnzgu.springblog.security.JwtGenerator;
import ru.pnzgu.springblog.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthFacade authFacade;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                           AuthenticationManager authenticationManager, AuthFacade authFacade, JwtGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.authFacade = authFacade;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public int register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new EntityAlreadyExistsException("Username is taken");

        var role = roleRepository.findByName("USER").orElseThrow(() -> new EntityNotFoundException("Role not found"));
        var user = new UserEntity(role, request.getUsername(), passwordEncoder.encode(request.getPassword()),
                request.getFirstName(), request.getLastName());
        
        var newUser = userRepository.save(user);
        
        return newUser.getId();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        authFacade.setAuth(auth);
        var token = jwtGenerator.generateToken(auth);
        
        return new LoginResponse(token);
    }
}
