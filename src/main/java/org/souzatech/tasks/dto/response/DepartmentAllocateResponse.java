package org.souzatech.tasks.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentAllocateResponse implements Serializable {

    private Long id;
    private String name;
    private Long amountPeople;
    private Long amountTask;

}