package org.souzatech.tasks.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.souzatech.tasks.dto.request.DepartmentCreateRequest;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<Person> peoples;

    @OneToMany(mappedBy = "department")
    private List<Task> tasks;

    public Department(DepartmentCreateRequest request) {
        this.name = request.getName();
    }

    public Department(Long departmentId) {
        this.id = departmentId;
    }
}
