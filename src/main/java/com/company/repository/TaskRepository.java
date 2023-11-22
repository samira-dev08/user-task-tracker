package com.company.repository;

import com.company.dto.response.TaskResponse;
import com.company.entity.Category;
import com.company.entity.Task;
import com.company.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    Optional<List<Category>> findCategoryById(Integer taskId);

    Optional<List<Task>> findTasksByCategoryId(Integer categoryId);
    @Query(value = "SELECT j FROM Task j WHERE  j.status=?1 ")
    Optional<List<Task>> findTaskByStatus(TaskStatus taskStatus);

    Optional<Task> findByIdAndIsActive(Integer taskId, Integer isActive);
}
