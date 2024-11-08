package com.example.educationclick.repository;

import com.example.educationclick.domain.Booking;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Booking entity.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    default Optional<Booking> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Booking> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Booking> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select booking from Booking booking left join fetch booking.timeslot left join fetch booking.teacher left join fetch booking.student",
        countQuery = "select count(booking) from Booking booking"
    )
    Page<Booking> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select booking from Booking booking left join fetch booking.timeslot left join fetch booking.teacher left join fetch booking.student"
    )
    List<Booking> findAllWithToOneRelationships();

    @Query(
        "select booking from Booking booking left join fetch booking.timeslot left join fetch booking.teacher left join fetch booking.student where booking.id =:id"
    )
    Optional<Booking> findOneWithToOneRelationships(@Param("id") Long id);
}
