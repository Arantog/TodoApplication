package pl.todoapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.todoapplication.entity.UserEntity;
import pl.todoapplication.mapper.UserMapper;
import pl.todoapplication.model.RegisterUserDto;
import pl.todoapplication.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(RegisterUserDto userDto) {

        UserEntity entity = UserMapper.mapRegisterUserDtoToUserEntity(userDto);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(entity);
    }
}
