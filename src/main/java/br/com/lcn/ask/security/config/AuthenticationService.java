package br.com.lcn.ask.security.config;

import br.com.lcn.ask.model.user.User;
import br.com.lcn.ask.model.user.UserRepository;
import br.com.lcn.ask.dto.request.SignUpRequest;
import br.com.lcn.ask.dto.request.SigninRequest;
import br.com.lcn.ask.dto.response.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = org.springframework.security.core.userdetails.User.builder().username(request.getFirstName()).username(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        //userRepository.save(user);
        var jwt = jwtService.generateToken((User) user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        var user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
