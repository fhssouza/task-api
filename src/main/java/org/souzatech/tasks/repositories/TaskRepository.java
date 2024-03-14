package org.souzatech.tasks.repositories;

import org.souzatech.tasks.dto.response.TaskResponse;
import org.souzatech.tasks.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT NEW org.souzatech.tasks.dto.response.TaskResponse(t.id, t.title, t.description, t.deadline, t.duration, t.taskStatus) " +
            "FROM Task t " +
            "WHERE t.person IS NULL " +  // Seleciona tarefas sem pessoa alocada
            "AND t.deadline = (SELECT MIN(t2.deadline) FROM Task t2 WHERE t2.person IS NULL) " +  // Próxima tarefa com prazo mais antigo
            "ORDER BY t.deadline ASC")  // Ordena por prazo mais antigo
    List<TaskResponse> findOldestUnallocatedTasks();

}
