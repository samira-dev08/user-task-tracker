package com.company.entity;

import com.company.enums.PriorityStatus;
import com.company.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Lob
    private String description;
    @Enumerated(EnumType.STRING)
    private PriorityStatus priority;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @ManyToMany
    @JoinTable(
            name = "task_category",
            joinColumns = @JoinColumn(name = "task_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id",referencedColumnName = "id")
    )
    private Set<Category> category;
    private LocalDateTime deadline;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ColumnDefault(value = "1")
    private Integer isActive;
}
