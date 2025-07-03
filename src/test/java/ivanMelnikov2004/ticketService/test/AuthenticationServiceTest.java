package ivanMelnikov2004.ticketService.test;

import ivanMelnikov2004.ticketService.configuration.JwtProperties;
import ivanMelnikov2004.ticketService.dto.request.AuthenticationRequest;
import ivanMelnikov2004.ticketService.dto.request.UserFieldsRequest;
import ivanMelnikov2004.ticketService.exception.BadCredentialsException;
import ivanMelnikov2004.ticketService.provider.JwtTokenProvider;
import ivanMelnikov2004.ticketService.service.AuthenticationService;
import ivanMelnikov2004.ticketService.service.UserService;
import ivanMelnikov2004.ticketService.service.impl.AuthenticationServiceImpl;
import ivanMelnikov2004.ticketService.service.impl.UserDetailsService;
import ivanMelnikov2004.ticketService.service.impl.UserServiceImpl;
import ivanMelnikov2004.ticketService.util.JwtUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import xyz.fiwka.ptmplace.mapper.UserMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@EnableConfigurationProperties(JwtProperties.class)
@Import({AuthenticationServiceImpl.class, UserServiceImpl.class, UserMapperImpl.class, JwtTokenProvider.class, JwtUtil.class, UserDetailsService.class})
public class AuthenticationServiceTest {

    private static final String JWT_SECRET_KEY = "IvqBPC8wpaxNM/aMmy8pntg0ERBlbXgUpOWvl4U2Jcc=";

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtUtil jwtUtil;

    @DynamicPropertySource
    public static void jwtProperties(DynamicPropertyRegistry registry) {
        registry.add("jwt.secret-key", () -> JWT_SECRET_KEY);
    }

    @BeforeAll
    public static void initializeTest(@Autowired UserService userService) {
        var userFields = new UserFieldsRequest(
                "Ivanov",
                "Ivan",
                "79000000000",
                "ivanov@example.com",
                "12345678"
        );

        userService.createUser(userFields);
    }

    @Test
    public void whenValidAuthenticate_thenReturnUserToken() {
        var request = new AuthenticationRequest("ivanov@example.com", "12345678");

        var result = authenticationService.authenticate(request);

        var email = jwtUtil.getEmail(result.accessToken());

        assertThat(email)
                .isEqualTo("ivanov@example.com");
    }

    @Test
    public void whenInvalidAuthenticate_thenThrowsBadCredentialsException() {
        var request = new AuthenticationRequest("ivanov2@example.com", "12345678");

        assertThatExceptionOfType(BadCredentialsException.class)
                .isThrownBy(() -> authenticationService.authenticate(request));
    }
}
