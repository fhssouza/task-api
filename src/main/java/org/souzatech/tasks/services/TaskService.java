package org.souzatech.tasks.services;

import org.souzatech.tasks.dto.request.TaskCreateRequest;
import org.souzatech.tasks.dto.response.TaskAllocateResponse;
import org.souzatech.tasks.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse create(TaskCreateRequest request);
    TaskAllocateResponse allocatePerson(Long id, Long personId);
    TaskAllocateResponse finish(Long id);
    List<TaskResponse> findOldestUnallocatedTasks();

}
