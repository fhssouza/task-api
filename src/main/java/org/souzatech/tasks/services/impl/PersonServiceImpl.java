package org.souzatech.tasks.services.impl;

import org.modelmapper.ModelMapper;
import org.souzatech.tasks.dto.request.PersonCreateRequest;
import org.souzatech.tasks.dto.response.PersonDurationResponse;
import org.souzatech.tasks.dto.response.PersonResponse;
import org.souzatech.tasks.entities.Department;
import org.souzatech.tasks.entities.Person;
import org.souzatech.tasks.infrastructure.exception.DataIntegrityViolationException;
import org.souzatech.tasks.infrastructure.exception.NotFoundException;
import org.souzatech.tasks.repositories.PersonRepository;
import org.souzatech.tasks.services.PersonService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    public static final String MSG_NAO_ENCONTRADO = "Não existe um cadastro com este codigo código %d";
    public static final String MSG_EM_USO = "O código %d não pode ser removida, pois está em uso";
    private final PersonRepository repository;
    private final DepartmentServiceImpl departmentRepository;
    private final ModelMapper modelMapper;

    public PersonServiceImpl(PersonRepository repository, DepartmentServiceImpl departmentRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PersonResponse create(PersonCreateRequest request) {
        Person person = getPerson(request);
        findByIdDepartment(person);
        repository.save(person);
        return modelMapper.map(person, PersonResponse.class);
    }

    @Override
    public PersonResponse update(Long id, PersonCreateRequest request) {
        var person = getByIdPerson(id);

        person.setName(request.getName());
        person.setDepartment(new Department(request.getDepartmentId()));

        findByIdDepartment(person);

        person = repository.save(person);

        return modelMapper.map(person, PersonResponse.class);
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new NotFoundException(
                    String.format(MSG_NAO_ENCONTRADO, id));

        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    String.format(MSG_EM_USO, id));
        }
    }

    @Override
    public List<PersonDurationResponse> findPersonTaskDurations() {
        return repository.findPersonTaskDurations();
    }

    @Override
    public List<PersonDurationResponse> findAverageTaskHoursByNameAndPeriod(String name, LocalDate startDate, LocalDate endDate) {
        return repository.findAverageTaskHoursByNameAndPeriod(name, startDate, endDate);
    }

    private Person getByIdPerson(Long id) {
        Optional<Person> person = repository.findById(id);
        if(person.isEmpty()){
            throw new NotFoundException(
                    String.format(MSG_NAO_ENCONTRADO, id));
        }
        return person.get();
    }

    private static Person getPerson(PersonCreateRequest request){
        Person person = new Person();
        person.setName(request.getName());
        person.setDepartment(new Department(request.getDepartmentId()));
        return person;
    }

    private void findByIdDepartment(Person person) {
        Long departamentId = person.getDepartment().getId();
        Department department = departmentRepository.getByIdDepartment(departamentId);
        person.setDepartment(department);
    }

}
