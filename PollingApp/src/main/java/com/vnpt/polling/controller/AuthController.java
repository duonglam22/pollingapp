package com.vnpt.polling.controller;

import com.vnpt.polling.exception.AppException;
import com.vnpt.polling.model.Role;
import com.vnpt.polling.model.RoleName;
import com.vnpt.polling.model.User;
import com.vnpt.polling.payload.ApiResponse;
import com.vnpt.polling.payload.JwtAuthenticationResponse;
import com.vnpt.polling.payload.LoginRequest;
import com.vnpt.polling.payload.SignUpRequest;
import com.vnpt.polling.repository.RoleRepository;
import com.vnpt.polling.repository.UserRepository;
import com.vnpt.polling.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("have sign in session with data: " + loginRequest.toString());
//        System.out.println("have sign in session with data: " + loginRequest.toString());
        Authentication au = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        logger.info("create authentication: " + loginRequest.toString());
        SecurityContextHolder.getContext().setAuthentication(au);
        logger.info("set authentication: " + loginRequest.toString());

        String jwt = tokenProvider.generateToken(au);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    /**
     *  create new user account
     * @param signUpRequest
     * @return result of signup
     */

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        logger.info("have sign up session with data: " + signUpRequest.toString());
        System.out.println("have sign up session with data: " + signUpRequest.toString());
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already takend"), HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address is already taken"), HttpStatus.BAD_REQUEST);
        }

        //create user account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set"));
        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
