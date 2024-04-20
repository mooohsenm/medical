package ir.moradi.assignmentmedical.service;

import ir.moradi.assignmentmedical.entity.Exercise;
import ir.moradi.assignmentmedical.repo.ExerciseRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
	final ExerciseRepo exerciseRepo;
	
	public ExerciseService(ExerciseRepo exerciseRepo) {this.exerciseRepo = exerciseRepo;}
	
	public void saveAll(List<Exercise> exercises){
		exerciseRepo.saveAll(exercises);
	}
	
	public void save(Exercise exercise){
		exerciseRepo.save(exercise);
	}
	
	public List<Exercise> getAllExercise(){
		return exerciseRepo.findAll();
	}
	
	public Exercise getByCode(String code){
		return  exerciseRepo.findFirstByCode(code);
	}
	
	public String deleteAll(){
		exerciseRepo.deleteAll();
		return "Deleted Successfully!";
	}
	
	
	
}
