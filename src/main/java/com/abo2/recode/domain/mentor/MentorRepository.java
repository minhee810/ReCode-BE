package com.abo2.recode.domain.mentor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MentorRepository extends CrudRepository<Mentor, Long> {

    @Query("SELECT MAX(m.careerYear) FROM Mentor m")
    Integer findMaxCareerYear();

    @Query("SELECT MIN(m.careerYear) FROM Mentor m")
    Integer findMinCareerYear();

    @Query("SELECT MAX(m.rating) FROM Mentor m")
    Long findMaxRating();

    @Query("SELECT MIN(m.rating) FROM Mentor m")
    Long findMinRating();
}
