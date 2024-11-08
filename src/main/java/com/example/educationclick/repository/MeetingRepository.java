package com.example.educationclick.repository;

import com.example.educationclick.domain.Meeting;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Meeting entity.
 */
@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    default Optional<Meeting> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Meeting> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Meeting> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select meeting from Meeting meeting left join fetch meeting.teacher left join fetch meeting.timeslot left join fetch meeting.student",
        countQuery = "select count(meeting) from Meeting meeting"
    )
    Page<Meeting> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select meeting from Meeting meeting left join fetch meeting.teacher left join fetch meeting.timeslot left join fetch meeting.student"
    )
    List<Meeting> findAllWithToOneRelationships();

    @Query(
        "select meeting from Meeting meeting left join fetch meeting.teacher left join fetch meeting.timeslot left join fetch meeting.student where meeting.id =:id"
    )
    Optional<Meeting> findOneWithToOneRelationships(@Param("id") Long id);
}
