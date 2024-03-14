package org.souzatech.tasks.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.souzatech.tasks.enums.TaskStatus;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskCreateResponse implements Serializable {

    private String title;
    private String description;
    private LocalDate deadline;
    private Long departmentId;
    private double duration;
    private TaskStatus status;

}