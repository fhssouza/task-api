package org.souzatech.tasks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.souzatech.tasks.dto.request.DepartmentCreateRequest;
import org.souzatech.tasks.dto.response.DepartmentResponse;
import org.souzatech.tasks.entities.Department;
import org.souzatech.tasks.repositories.DepartmentRepository;
import org.souzatech.tasks.services.impl.DepartmentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DepartmentServiceImplTest {

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {

        DepartmentCreateRequest request = new DepartmentCreateRequest();
        request.setName("Test Department");

        Department department = new Department(request);

        DepartmentResponse expectedResponse = new DepartmentResponse();
        expectedResponse.setName(request.getName());

        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        when(modelMapper.map(any(Department.class), eq(DepartmentResponse.class))).thenReturn(expectedResponse);

        DepartmentResponse response = departmentService.create(request);


        assertEquals(expectedResponse.getName(), response.getName());

        verify(departmentRepository, times(1)).save(any(Department.class));
        verify(modelMapper, times(1)).map(any(Department.class), eq(DepartmentResponse.class));
    }

    @Test
    public void testUpdate() {
        DepartmentCreateRequest request = new DepartmentCreateRequest();
        request.setName("Updated Department");

        Department department = new Department(request);

        DepartmentResponse expectedResponse = new DepartmentResponse();
        expectedResponse.setName(request.getName());

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        when(modelMapper.map(any(Department.class), eq(DepartmentResponse.class))).thenReturn(expectedResponse);

        DepartmentResponse response = departmentService.update(1L, request);

        assertEquals(expectedResponse.getName(), response.getName());

        verify(departmentRepository, times(1)).findById(anyLong());
        verify(departmentRepository, times(1)).save(any(Department.class));
        verify(modelMapper, times(1)).map(any(Department.class), eq(DepartmentResponse.class));
    }

    @Test
    public void testDelete() {
        doNothing().when(departmentRepository).deleteById(anyLong());
        departmentService.delete(1L);
        verify(departmentRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testGetByIdDepartment() {
        Department department = new Department();
        department.setName("Test Department");

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));

        Department result = departmentService.getByIdDepartment(1L);

        assertEquals(result.getName(), department.getName());
        verify(departmentRepository, times(1)).findById(anyLong());
    }
}


