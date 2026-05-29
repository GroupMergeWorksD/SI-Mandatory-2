package com.sirest.auth;

import com.sirest.config.ApiPaths;
import com.sirest.dto.LoginRequest;
import com.sirest.dto.LoginResponse;
import com.sirest.model.User;
import com.sirest.repository.UserRepository;
import com.sirest.security.JwtService;
import com.sirest.security.TokenRevocationService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(ApiPaths.API_V1 + "/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenRevocationService tokenRevocationService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserRepository userRepository,
                          TokenRevocationService tokenRevocationService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.tokenRevocationService = tokenRevocationService;
    }

    @PostMapping("/login")
    public EntityModel<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        LoginResponse response = new LoginResponse(token, "Bearer", user.getUsername());

        return EntityModel.of(response,
                linkTo(methodOn(AuthController.class).login(request)).withSelfRel(),
                linkTo(methodOn(AuthController.class).logout(null)).withRel("logout")
        );
    }

    @PostMapping("/logout")
    public EntityModel<Map<String, String>> logout(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Authorization header");
        }

        String token = authHeader.substring(7);
        tokenRevocationService.revokeToken(token);

        return EntityModel.of(
                Map.of("message", "Logged out successfully"),
                linkTo(methodOn(AuthController.class).logout(authHeader)).withSelfRel(),
                linkTo(methodOn(AuthController.class).login(null)).withRel("login")
        );
    }
}