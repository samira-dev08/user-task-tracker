package com.company.repository;

import com.company.entity.Category;
import com.company.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
            Boolean existsCategoryByName(String name);
    Optional<List<Category>> findCategoryByTasksId(Integer taskId);

}
