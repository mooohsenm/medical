package ir.moradi.assignmentmedical.controller;

import ir.moradi.assignmentmedical.entity.Exercise;
import ir.moradi.assignmentmedical.service.ExerciseService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ExerciseController {
	final ExerciseService exerciseService;
	
	public ExerciseController(ExerciseService exerciseService) {this.exerciseService = exerciseService;}
	
	@GetMapping(value = "/all")
	public List<Exercise> getAll(){
		return exerciseService.getAllExercise();
	}
	
	@DeleteMapping(value = "/all")
	public String deleteAll(){
		return exerciseService.deleteAll();
	}
	
	@GetMapping(value = "/exercise/{code}")
	public Exercise getExercise(@PathVariable String code){
		return exerciseService.getByCode(code);
	}
	
	@PostMapping(value = "/add-sample")
	public ResponseEntity<String> addSampleExercise() {
		try {
			int rnd = new Random().nextInt(99999999);
			String rndTxt = String.valueOf(rnd);
			Exercise ex = new Exercise(
					"Source"+rndTxt,
					"codeListCode"+rndTxt,
					"code"+rndTxt,
					"displayValue"+rndTxt,
					"longDescription"+rndTxt,
					new Date(),
					new Date(new Date().getTime() + (1000 * 60 * 60 * 24)),
					rnd
			);
			exerciseService.save(ex);
			
			return new ResponseEntity<>("Exercises added successfully!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to upload exercises: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/upload")
	public ResponseEntity<String> uploadExercise(@RequestParam("file") MultipartFile file) {
		try {
			if (file.isEmpty()) {
				return new ResponseEntity<>("Please upload a CSV file", HttpStatus.BAD_REQUEST);
			}

			List<Exercise> exercises = parseCsvToExercise(file);
			exerciseService.saveAll(exercises);

			return new ResponseEntity<>("Exercises uploaded successfully!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to upload exercises: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private List<Exercise> parseCsvToExercise(MultipartFile file) throws Exception {
		Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
		CSVParser parser = CSVFormat.EXCEL.withHeader().parse(reader);
		CSVParser.parse(reader.toString(), CSVFormat.RFC4180.withSkipHeaderRecord());
		List<Exercise> exercises = new ArrayList<>();
		
		try {
			for (CSVRecord record : parser) {
				
				Date fromDate = null;
				try { fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(record.get("fromDate")); }
				catch (Throwable ignored){  }
				
				Date toDate = null;
				try { toDate = new SimpleDateFormat("dd-MM-yyyy").parse(record.get("toDate"));}
				catch (Throwable ignored){  }
				
				int sortingPriority = 0;
				try{ sortingPriority = Integer.parseInt(record.get("sortingPriority")); }
				catch (Throwable ignored){  }
				
				Exercise exercise = new Exercise(
						record.get("source"),
						record.get("codeListCode"),
						record.get("code"),
						record.get("displayValue"),
						record.get("longDescription"),
						fromDate,
						toDate,
						sortingPriority
				);
				exercises.add(exercise);
			}
		} finally {
			parser.close();
		}
		
		return exercises;
	}
	
}
