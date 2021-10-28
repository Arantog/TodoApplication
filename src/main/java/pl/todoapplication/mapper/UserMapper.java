package pl.todoapplication.mapper;

import pl.todoapplication.entity.UserEntity;
import pl.todoapplication.model.RegisterUserDto;

public class UserMapper {

    public static UserEntity mapRegisterUserDtoToUserEntity(RegisterUserDto dto) {

        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPassword(dto.getPassword());

        return entity;
    }
}
