package org.souzatech.tasks.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.souzatech.tasks.dto.request.PersonCreateRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    public Person(PersonCreateRequest request) {
        this.name = request.getName();
        this.department = new Department(request.getDepartmentId());
    }
}
