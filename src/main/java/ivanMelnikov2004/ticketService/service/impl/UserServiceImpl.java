package ivanMelnikov2004.ticketService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ivanMelnikov2004.ticketService.dto.request.UserFieldsRequest;
import ivanMelnikov2004.ticketService.dto.response.UserResponse;
import ivanMelnikov2004.ticketService.exception.UserAlreadyExistsException;
import ivanMelnikov2004.ticketService.mapper.UserMapper;
import ivanMelnikov2004.ticketService.repository.UserRepository;
import ivanMelnikov2004.ticketService.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserFieldsRequest userFieldsRequest) {
        if (userRepository.existsByEmail(userFieldsRequest.email()))
            throw new UserAlreadyExistsException("User with email " + userFieldsRequest.email() + " is already exists");

        var user = userMapper.toUser(userFieldsRequest);
        user.setPassword(passwordEncoder.encode(userFieldsRequest.password()));
        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    @Override
    public Optional<UserResponse> findUser(String email) {
        return userRepository.findByEmail(email).map(userMapper::toUserResponseDto);
    }

    @Override
    public Optional<UserResponse> findUser(Long id) {
        return userRepository.findById(id).map(userMapper::toUserResponseDto);
    }
}
