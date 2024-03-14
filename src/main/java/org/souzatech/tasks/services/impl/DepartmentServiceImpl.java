package org.souzatech.tasks.services.impl;

import org.modelmapper.ModelMapper;
import org.souzatech.tasks.dto.request.DepartmentCreateRequest;
import org.souzatech.tasks.dto.response.DepartmentAllocateResponse;
import org.souzatech.tasks.dto.response.DepartmentResponse;
import org.souzatech.tasks.entities.Department;
import org.souzatech.tasks.infrastructure.exception.DataIntegrityViolationException;
import org.souzatech.tasks.infrastructure.exception.NotFoundException;
import org.souzatech.tasks.repositories.DepartmentRepository;
import org.souzatech.tasks.services.DepartmentService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    public static final String MSG_NAO_ENCONTRADO = "Não existe um cadastro com código %d";
    public static final String MSG_EM_USO = "O código %d não pode ser removida, pois está em uso";

    private final DepartmentRepository repository;
    private final ModelMapper modelMapper;

    public DepartmentServiceImpl(DepartmentRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DepartmentAllocateResponse> getDepartmentAllocationStatistics() {
        return repository.getDepartmentAllocationStatistics();
    }

    @Override
    public DepartmentResponse create(DepartmentCreateRequest request) {
        Department department = new Department(request);
        repository.save(department);
        return modelMapper.map(department, DepartmentResponse.class);
    }

    @Override
    public DepartmentResponse update(Long id, DepartmentCreateRequest request) {
        Department department = getByIdDepartment(id);
        department.setName(request.getName());
        department = repository.save(department);
        return modelMapper.map(department, DepartmentResponse.class);
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

    public Department getByIdDepartment(Long id) {
        Optional<Department> department = repository.findById(id);
        if(department.isEmpty()){
            throw new NotFoundException(
                    String.format(MSG_NAO_ENCONTRADO, id));
        }
        return department.get();
    }

}
