package pl.todoapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.todoapplication.entity.TodoEntity;
import pl.todoapplication.entity.UserEntity;
import pl.todoapplication.mapper.TodoMapper;
import pl.todoapplication.model.CreateTodoDto;
import pl.todoapplication.model.TodoDto;
import pl.todoapplication.repository.TodoRepository;
import pl.todoapplication.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TodoDto> findAllActive() {

        List<TodoEntity> todoEntities = todoRepository.findAllByCompleted(false);

        // jeśli jest konieczna jakaś dodatkowa logika biznesowa to dodajemy ją w tym miejscu

        List<TodoDto> todoDtos = TodoMapper.mapEntityListToDtoList(todoEntities);

        return todoDtos;
    }

    public List<TodoDto> findAllCompleted() {

        List<TodoEntity> todoEntities = todoRepository.findAllByCompleted(true);

        // jeśli jest konieczna jakaś dodatkowa logika biznesowa to dodajemy ją w tym miejscu

        List<TodoDto> todoDtos = TodoMapper.mapEntityListToDtoList(todoEntities);

        return todoDtos;
    }

    public TodoDto getById(Long id) {

        Optional<TodoEntity> entity = todoRepository.findById(id);
        if (entity.isPresent()) {
            return TodoMapper.mapeEntityToDto(entity.get());
        }

        throw new EntityNotFoundException();
    }

    public boolean create(CreateTodoDto todo) {

        // pobieramy użytkownika z bazy
        UserEntity userEntity = userRepository.findById(todo.getUserId());

        TodoEntity entity = new TodoEntity(todo.getText());

        // łączymy relacyjnie tabele Todo i User
        entity.setUser(userEntity);

        try {
            todoRepository.save(entity);
            return true;
        } catch (Exception exception) {
            // handle exception
            return false;
        }
    }

    public TodoDto complete(Long id) {
        Optional<TodoEntity> entity = todoRepository.findById(id);
        if (entity.isPresent()) {
            TodoEntity todoEntity = entity.get();
            todoEntity.setCompleted(true);
            todoEntity.setCompleteDate(new Date());

            todoEntity = todoRepository.save(todoEntity);
            return TodoMapper.mapeEntityToDto(todoEntity);
        }

        throw new EntityNotFoundException();
    }

    public TodoDto update(TodoDto dto) {
        Optional<TodoEntity> entity = todoRepository.findById(dto.getId());
        if (entity.isPresent()) {
            TodoEntity todoEntity = entity.get();
            todoEntity.setText(dto.getText());

            todoEntity = todoRepository.save(todoEntity);
            return TodoMapper.mapeEntityToDto(todoEntity);
        }

        throw new EntityNotFoundException();
    }
}
