package org.souzatech.tasks.services;

import org.souzatech.tasks.dto.request.DepartmentCreateRequest;
import org.souzatech.tasks.dto.response.DepartmentAllocateResponse;
import org.souzatech.tasks.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    List<DepartmentAllocateResponse> getDepartmentAllocationStatistics();

    DepartmentResponse create(DepartmentCreateRequest request);

    DepartmentResponse update(Long id, DepartmentCreateRequest request);

    void delete(Long id);

}
