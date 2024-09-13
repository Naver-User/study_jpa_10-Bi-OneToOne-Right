package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.zerock.myapp.listener.CommonEntityLifecyleListener;

import lombok.Data;


@Data

@EntityListeners(CommonEntityLifecyleListener.class)

@Entity(name = "Student3")
@Table(name="student3")
@SequenceGenerator(name = "MySequence", sequenceName = "seq_student3")
public class Student3
	implements Serializable {	// LEFT, One(1)
	@Serial private static final long serialVersionUID = 1L;

	// 1. Set PK
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MySequence")
	@Column(name = "student_id")
	private Long id;			// PK
	
	
	// 2. 일반속성들
	@Basic(optional = false)	// Not Null Constraint
	private String name;
	
	
	// 3. 연관관계 선언
	
	// ==========================================
	// OneToOne (1:1), BI, Right(FK), Target
	// ==========================================
	
	// 연관관계의 주인만 아래 어노테이션으로 밝히면,
	// 실제 조회시, 아래 속성에 이 사물함을 사용하는 학생객체를
	// JPA 구현체가 알아서 채워넣겠다!!! 라는 의미가 됩니다.
	
//	@OneToOne(mappedBy = "student")									// 1
//	@OneToOne(mappedBy = "student", targetEntity = Locker3.class)	// 2
	@OneToOne(
		// (1) 이 연관관계의 주인인, FK 컬럼역할을 하는 자식엔티티의 "필드명" 지정
		mappedBy = "student",
		
		// (2) 위의 FK 필드명을 포함하는 자식 엔티티 타입정보를 Clazz로 지정
		targetEntity = Locker3.class,
		
		// (3) 이 연관관계가 필수인지/선택인지 지정
		//     아래와 같이 true로 선택으로 지정하면 -> 이 사물함을 사용하는 학생이
		//     없을 수도 있음을 지정(즉, FK는 중복을 허용하고, NULL도 허용)
		optional = true)											// 3
	
	private Locker3 locker;	// 참고로, 이 속성은 FK가 아닙니다!!
	
	
		
   
} // end class

