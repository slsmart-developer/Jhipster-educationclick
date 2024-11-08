package com.example.educationclick.repository;

import com.example.educationclick.domain.Teacher;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Teacher entity.
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    default Optional<Teacher> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Teacher> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Teacher> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select teacher from Teacher teacher left join fetch teacher.subject",
        countQuery = "select count(teacher) from Teacher teacher"
    )
    Page<Teacher> findAllWithToOneRelationships(Pageable pageable);

    @Query("select teacher from Teacher teacher left join fetch teacher.subject")
    List<Teacher> findAllWithToOneRelationships();

    @Query("select teacher from Teacher teacher left join fetch teacher.subject where teacher.id =:id")
    Optional<Teacher> findOneWithToOneRelationships(@Param("id") Long id);
}
