package com.example.educationclick.repository;

import com.example.educationclick.domain.StudyMaterial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StudyMaterial entity.
 */
@Repository
public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {
    default Optional<StudyMaterial> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<StudyMaterial> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<StudyMaterial> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select studyMaterial from StudyMaterial studyMaterial left join fetch studyMaterial.teacher",
        countQuery = "select count(studyMaterial) from StudyMaterial studyMaterial"
    )
    Page<StudyMaterial> findAllWithToOneRelationships(Pageable pageable);

    @Query("select studyMaterial from StudyMaterial studyMaterial left join fetch studyMaterial.teacher")
    List<StudyMaterial> findAllWithToOneRelationships();

    @Query("select studyMaterial from StudyMaterial studyMaterial left join fetch studyMaterial.teacher where studyMaterial.id =:id")
    Optional<StudyMaterial> findOneWithToOneRelationships(@Param("id") Long id);
}
