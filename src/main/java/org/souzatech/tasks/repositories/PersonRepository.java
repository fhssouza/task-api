package org.souzatech.tasks.repositories;

import org.souzatech.tasks.dto.response.PersonDurationResponse;
import org.souzatech.tasks.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT new org.souzatech.tasks.dto.response.PersonDurationResponse(p.name, p.department.name, SUM(t.duration)) " +
            "FROM Person p JOIN p.tasks t GROUP BY p.name, p.department.name")
    List<PersonDurationResponse> findPersonTaskDurations();

    @Query("SELECT NEW org.souzatech.tasks.dto.response.PersonDurationResponse(p.name, p.department.name, AVG(t.duration)) " +
            "FROM Person p JOIN p.tasks t " +
            "WHERE LOWER(p.name) LIKE LOWER(concat('%', :name, '%')) AND t.deadline BETWEEN :startDate AND :endDate " +
            "GROUP BY p.name, p.department.name")
    List<PersonDurationResponse> findAverageTaskHoursByNameAndPeriod(@Param("name") String name, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
