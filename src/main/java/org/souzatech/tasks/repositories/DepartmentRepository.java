package org.souzatech.tasks.repositories;

import org.souzatech.tasks.dto.response.DepartmentAllocateResponse;
import org.souzatech.tasks.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT NEW org.souzatech.tasks.dto.response.DepartmentAllocateResponse(d.id, d.name, COUNT(DISTINCT p.id), COUNT(DISTINCT t.id)) " +
            "FROM Department d " +
            "LEFT JOIN d.peoples p " +
            "LEFT JOIN d.tasks t " +
            "GROUP BY d.id, d.name")
    List<DepartmentAllocateResponse> getDepartmentAllocationStatistics();

}
