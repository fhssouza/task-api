package org.souzatech.tasks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.souzatech.tasks.dto.request.PersonCreateRequest;
import org.souzatech.tasks.dto.response.PersonDurationResponse;
import org.souzatech.tasks.dto.response.PersonResponse;
import org.souzatech.tasks.entities.Department;
import org.souzatech.tasks.entities.Person;
import org.souzatech.tasks.infrastructure.exception.NotFoundException;
import org.souzatech.tasks.repositories.PersonRepository;
import org.souzatech.tasks.services.impl.DepartmentServiceImpl;
import org.souzatech.tasks.services.impl.PersonServiceImpl;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private DepartmentServiceImpl departmentRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePerson() {
        PersonCreateRequest request = new PersonCreateRequest();
        request.setName("Teste");
        request.setDepartmentId(1L);

        Person person = new Person();
        person.setName(request.getName());
        person.setDepartment(new Department(request.getDepartmentId()));

        PersonResponse personResponse = new PersonResponse();
        personResponse.setName(request.getName());

        when(personRepository.save(any(Person.class))).thenReturn(person);
        when(departmentRepository.getByIdDepartment(any(Long.class))).thenReturn(new Department());
        when(modelMapper.map(any(Person.class), any())).thenReturn(personResponse);

        PersonResponse response = personService.create(request);

        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
    }

    @Test
    public void testUpdatePerson() {
        Long id = 1L;
        PersonCreateRequest request = new PersonCreateRequest();
        request.setName("Teste");
        request.setDepartmentId(1L);

        Person person = new Person();
        person.setId(id);
        person.setName(request.getName());
        person.setDepartment(new Department(request.getDepartmentId()));

        PersonResponse personResponse = new PersonResponse();
        personResponse.setName(request.getName());

        when(personRepository.findById(id)).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);
        when(departmentRepository.getByIdDepartment(any(Long.class))).thenReturn(new Department());
        when(modelMapper.map(any(Person.class), any())).thenReturn(personResponse);

        PersonResponse response = personService.update(id, request);

        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
    }

    @Test
    public void testDeletePerson() {
        Long id = 1L;

        doNothing().when(personRepository).deleteById(id);

        personService.delete(id);

        verify(personRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeletePersonNotFound() {
        Long id = 1L;

        doThrow(EmptyResultDataAccessException.class).when(personRepository).deleteById(id);

        assertThrows(NotFoundException.class, () -> personService.delete(id));
    }

    @Test
    public void testFindPersonTaskDurations() {
        List<PersonDurationResponse> personDurationResponses = Arrays.asList(new PersonDurationResponse());
        when(personRepository.findPersonTaskDurations()).thenReturn(personDurationResponses);

        List<PersonDurationResponse> response = personService.findPersonTaskDurations();

        assertNotNull(response);
        assertEquals(personDurationResponses.size(), response.size());
    }

    @Test
    public void testFindAverageTaskHoursByNameAndPeriod() {
        String name = "Teste";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();

        List<PersonDurationResponse> personDurationResponses = Arrays.asList(new PersonDurationResponse());
        when(personRepository.findAverageTaskHoursByNameAndPeriod(name, startDate, endDate)).thenReturn(personDurationResponses);

        List<PersonDurationResponse> response = personService.findAverageTaskHoursByNameAndPeriod(name, startDate, endDate);

        assertNotNull(response);
        assertEquals(personDurationResponses.size(), response.size());
    }

}


