package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.zerock.myapp.listener.CommonEntityLifecyleListener;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


//@Log4j2
@Slf4j

@Data

@EntityListeners(CommonEntityLifecyleListener.class)

@Entity(name = "Locker3")
@Table(name="locker3")
public class Locker3 implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	// 1. Set PK
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "locker_id")
	private Long id;			// PK
	
	
	// 2. 일반속성들
	@Basic(optional = false)	// Not Null Constraint
	private String name;
	
	
	// 3. 연관관계 선언
	
	// ==========================================
	// OneToOne (1:1), BI, Right(FK), Main
	// ==========================================
	
	// Reference direction: Locker -> Student 
	
//	@OneToOne									// 1, OK
	@OneToOne(targetEntity = Student3.class)	// 2, OK
//	@OneToOne(cascade = CascadeType.ALL)		// 3, OK
		
	/**
	 * -------------------------------
	 * @JoinColumn
	 * -------------------------------
	 * (1) Set the FK column name of Many (Child) table. (***)
	 * (2) If @JoinColumn annotation abbreviated,
	 * 	   The default FK column name = 
	 * 			The name of the entity + "_" + The name of the referenced primary key column.
	 * (3) @JoinColumn(table): The name of the table that contains the FK column.
	 * 	   If `table` propery *Not specified,
	 * 	   The FK column is assumed to be in the primary table of the applicable entity. 
	 * -------------------------------
	 */
	
//	@JoinColumn																// 0, XX
//	@JoinColumn(name = "my_student")										// 1, OK
//	@JoinColumn(name = "my_student", referencedColumnName = "student_id")	// 2, OK
	@JoinColumn(
		// (1) FK 컬럼명 지정 (FK역할을 할 필드명을 지정하는게 아닙니다)
		name = "my_student",
		// (2) FK 가 참조하는 부모 테이블의 PK 컬럼명 지정 (역시 필드명지정이 아닙니다) 
		referencedColumnName = "student_id",
		// (3) 이 FK로 참조가능한 값을 반드시 고유해야 함 -> 
		//     즉, 반드시 하나의 사물함만 참조가능
		unique = true)													// 3, OK
	
	// 아래 속성도 콘솔로 출력하게 되면,
	// 무한루핑(Infinite Looping)에 빠지게됩니다.
	// 이유: Locker2 객체를 콘솔에 출력하면 -> 사물함 사용학생정보 출력 ->
	//       다시, 학생이 사용하는 사물함 출력 -> 무한 출력 오류발생!!!
	// 해결: Lombok 의 @ToString.Exclude 어노테이션을 양쪽 중에 한군데 설정
	
	@ToString.Exclude
	private Student3 student;	// FK, Target (1)
	
	
	public void setStudent(Student3 student) {
		log.trace("setStudent({}) invoked.", student);
		
		if(Objects.nonNull(student)) {
			// (1) 현재 학생이 사용하는 사물함 엔티티 설정
			this.student = student;
			
			// (2) 반대로, 현재 학생이 사용하는 사물함에, 학생 엔티티 설정
			this.student.setLocker(this);
		} // if
	} // setLocker
   
	
	
} // end class

