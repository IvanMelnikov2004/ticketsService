package ivanMelnikov2004.ticketService.service;

import ivanMelnikov2004.ticketService.dto.request.UserFieldsRequest;
import ivanMelnikov2004.ticketService.dto.response.UserResponse;

import java.util.Optional;

public interface UserService {

    UserResponse createUser(UserFieldsRequest userFieldsRequest);
    Optional<UserResponse> findUser(String email);
    Optional<UserResponse> findUser(Long id);
}
