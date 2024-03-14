package org.souzatech.tasks.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskCreateRequest implements Serializable {

    private String title;
    private String description;
    private LocalDate deadline;
    private Long departmentId;
    private double duration;

}