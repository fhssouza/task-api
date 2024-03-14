package org.souzatech.tasks.services.impl;

import org.modelmapper.ModelMapper;
import org.souzatech.tasks.dto.request.TaskCreateRequest;
import org.souzatech.tasks.dto.response.TaskAllocateResponse;
import org.souzatech.tasks.dto.response.TaskResponse;
import org.souzatech.tasks.entities.Department;
import org.souzatech.tasks.entities.Person;
import org.souzatech.tasks.entities.Task;
import org.souzatech.tasks.enums.TaskStatus;
import org.souzatech.tasks.infrastructure.exception.NotFoundException;
import org.souzatech.tasks.infrastructure.exception.StatusTaskException;
import org.souzatech.tasks.infrastructure.exception.TaskDepartmentException;
import org.souzatech.tasks.repositories.PersonRepository;
import org.souzatech.tasks.repositories.TaskRepository;
import org.souzatech.tasks.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private static final String MSG_CANCELADA = "O código %d não pode ser finalizado, pois já está cancelada";
    private final TaskRepository repository;

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    public TaskServiceImpl(TaskRepository repository, PersonRepository personRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskResponse create(TaskCreateRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setDuration(request.getDuration());
        task.setDepartment(new Department(request.getDepartmentId()));

        task = repository.save(task);

        return modelMapper.map(task, TaskResponse.class);
    }

    @Override
    public TaskAllocateResponse allocatePerson(Long id, Long personId) {
        Task task = repository.findById(id).orElseThrow(() -> new NotFoundException("Tarefa não encontrada"));
        Person person = personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));


        if (!person.getDepartment().equals(task.getDepartment())) {
            throw new TaskDepartmentException("Departamentos não correspondem");
        }

        task.setPerson(person);
        task = repository.save(task);

        return modelMapper.map(task, TaskAllocateResponse.class);
    }

    @Override
    public TaskAllocateResponse finish(Long id) {
        Task task = repository.findById(id).orElseThrow(() -> new NotFoundException("Tarefa não encontrada"));

        if(TaskStatus.CANCELED.equals(task.getTaskStatus())){
            throw new StatusTaskException(
                    String.format(MSG_CANCELADA, id)
            );
        }

        task.setTaskStatus(TaskStatus.FINISHED);

        repository.save(task);

        return modelMapper.map(task, TaskAllocateResponse.class);
    }

    @Override
    public List<TaskResponse> findOldestUnallocatedTasks() {
        return repository.findOldestUnallocatedTasks();
    }

}
