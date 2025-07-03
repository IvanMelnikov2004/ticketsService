package ivanMelnikov2004.ticketService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ivanMelnikov2004.ticketService.dto.request.UserFieldsRequest;
import ivanMelnikov2004.ticketService.dto.response.UserResponse;
import ivanMelnikov2004.ticketService.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(UserFieldsRequest userFieldsRequest);
    User toUser(UserResponse userResponse);
    UserResponse toUserResponseDto(User user);
}
