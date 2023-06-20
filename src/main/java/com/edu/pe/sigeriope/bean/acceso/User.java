package com.edu.pe.sigeriope.bean.acceso;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author dasamo
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	//id_user, username, password, state, registration_date, id_profile, session_number, name, last_name, photo, gender, dni, mail
	  private int id_user; 
	  

	  private String username; 
	  
	  private String password;
	  
	  private int state;

	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT-5")
	  private LocalDateTime registration_date;

	  private int session_number;
	  
	  private int active_session;
	  
	  private String name;
	  
	  private String last_name;

	  private String photo;
	  
	  private String gender;
	  
	  private String dni;
	  
	  private String mail;
	  
	  private int id_profile;
	  
	  private boolean reset_password;
	  
	  private Profile profile;

	  private int id_person;
}
