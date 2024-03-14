package org.souzatech.tasks.services;

import org.souzatech.tasks.dto.request.PersonCreateRequest;
import org.souzatech.tasks.dto.response.PersonDurationResponse;
import org.souzatech.tasks.dto.response.PersonResponse;

import java.time.LocalDate;
import java.util.List;

public interface PersonService {

    PersonResponse create(PersonCreateRequest request);

    PersonResponse update(Long id, PersonCreateRequest request);

    void delete(Long id);

    List<PersonDurationResponse> findPersonTaskDurations();

    List<PersonDurationResponse> findAverageTaskHoursByNameAndPeriod(String name, LocalDate startDate, LocalDate endDate);

}
