package org.souzatech.tasks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.souzatech.tasks.dto.request.TaskCreateRequest;
import org.souzatech.tasks.dto.response.TaskAllocateResponse;
import org.souzatech.tasks.dto.response.TaskResponse;
import org.souzatech.tasks.entities.Department;
import org.souzatech.tasks.entities.Person;
import org.souzatech.tasks.entities.Task;
import org.souzatech.tasks.enums.TaskStatus;
import org.souzatech.tasks.repositories.PersonRepository;
import org.souzatech.tasks.repositories.TaskRepository;
import org.souzatech.tasks.services.impl.TaskServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTask() {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Teste");
        request.setDescription("Descrição do teste");
        request.setDeadline(LocalDate.from(LocalDateTime.now()));
        request.setDuration(60);
        request.setDepartmentId(1L);

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setDuration(request.getDuration());
        task.setDepartment(new Department(request.getDepartmentId()));

        TaskResponse expectedResponse = new TaskResponse();
        expectedResponse.setTitle(request.getTitle());
        expectedResponse.setDescription(request.getDescription());

        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(modelMapper.map(task, TaskResponse.class)).thenReturn(expectedResponse);

        var result = taskService.create(request);

        assertEquals(expectedResponse.getTitle(), result.getTitle());
        assertEquals(expectedResponse.getDescription(), result.getDescription());
    }

    @Test
    public void testAllocatePerson() {
        Long taskId = 1L;
        Long personId = 1L;
        Long departmentId = 1L;

        Department department = new Department(departmentId);

        Task task = new Task();
        task.setId(taskId);
        task.setDepartment(department);

        Person person = new Person();
        person.setId(personId);
        person.setDepartment(department);

        TaskAllocateResponse expectedResponse = new TaskAllocateResponse();
        expectedResponse.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(modelMapper.map(task, TaskAllocateResponse.class)).thenReturn(expectedResponse);

        var result = taskService.allocatePerson(taskId, personId);

        assertEquals(expectedResponse.getId(), result.getId());
    }

    @Test
    public void testFinishTask() {
        Long taskId = 1L;

        Task task = new Task();
        task.setId(taskId);
        task.setTaskStatus(TaskStatus.OPEN);

        TaskAllocateResponse expectedResponse = new TaskAllocateResponse();
        expectedResponse.setId(taskId);
        expectedResponse.setTaskStatus(TaskStatus.FINISHED);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(modelMapper.map(task, TaskAllocateResponse.class)).thenReturn(expectedResponse);

        var result = taskService.finish(taskId);

        assertEquals(expectedResponse.getId(), result.getId());
        assertEquals(expectedResponse.getTaskStatus(), result.getTaskStatus());
    }

    @Test
    public void testFindOldestUnallocatedTasks() {

        Task task1 = new Task();
        task1.setId(1L);
        task1.setDeadline(LocalDate.from(LocalDateTime.now().minusDays(5)));

        Task task2 = new Task();
        task2.setId(2L);
        task2.setDeadline(LocalDate.from(LocalDateTime.now().minusDays(3)));

        Task task3 = new Task();
        task3.setId(3L);
        task3.setDeadline(LocalDate.from(LocalDateTime.now().minusDays(1)));

        List<Task> tasks = Arrays.asList(task1, task2, task3);

        TaskResponse response1 = new TaskResponse();
        response1.setId(task1.getId());
        response1.setDeadline(task1.getDeadline());

        TaskResponse response2 = new TaskResponse();
        response2.setId(task2.getId());
        response2.setDeadline(task2.getDeadline());

        TaskResponse response3 = new TaskResponse();
        response3.setId(task3.getId());
        response3.setDeadline(task3.getDeadline());

        List<TaskResponse> responses = Arrays.asList(response1, response2, response3);

        when(taskRepository.findOldestUnallocatedTasks()).thenReturn(responses);
        when(modelMapper.map(task1, TaskResponse.class)).thenReturn(response1);
        when(modelMapper.map(task2, TaskResponse.class)).thenReturn(response2);
        when(modelMapper.map(task3, TaskResponse.class)).thenReturn(response3);

        List<TaskResponse> result = taskService.findOldestUnallocatedTasks();

        assertEquals(responses.size(), result.size());
        for (int i = 0; i < responses.size(); i++) {
            assertEquals(responses.get(i).getId(), result.get(i).getId());
            assertEquals(responses.get(i).getDeadline(), result.get(i).getDeadline());
        }

    }

}


