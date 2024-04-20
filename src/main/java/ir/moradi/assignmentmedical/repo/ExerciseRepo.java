package ir.moradi.assignmentmedical.repo;

import ir.moradi.assignmentmedical.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepo extends JpaRepository<Exercise, Integer> {
	Exercise findFirstByCode(String code);
	
}
