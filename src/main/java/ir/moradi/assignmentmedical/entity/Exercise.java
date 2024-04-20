package ir.moradi.assignmentmedical.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Exercise {
	
	@Id
	@GeneratedValue
	long id;
	
	String source;
	String codeListCode;
	
	@Column(unique=true)
	String code;
	
	String displayValue;
	String longDescription;
	Date fromDate;
	Date toDate;
	int sortingPriority;
	
	public Exercise(String source,String codeListCode,String code,String displayValue,
					String longDescription,	Date fromDate,	Date toDate,int sortingPriority){
		this.source = source;
		this.codeListCode = codeListCode;
		this.code = code;
		this.displayValue = displayValue;
		this.longDescription = longDescription;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.sortingPriority = sortingPriority;
	}
	
}
