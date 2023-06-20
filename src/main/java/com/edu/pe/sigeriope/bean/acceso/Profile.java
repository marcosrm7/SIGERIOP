package com.edu.pe.sigeriope.bean.acceso;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author dasamo
 *
 */

public class Profile implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id_profile;

	private String name_profile;

	private int state;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT-5")
	private LocalDateTime registration_date;

	private int session_time;

	public Profile() {
		// TODO Auto-generated constructor stub
	}

	public int getId_profile() {
		return id_profile;
	}

	public void setId_profile(int id_profile) {
		this.id_profile = id_profile;
	}

	public String getName_profile() {
		return name_profile;
	}

	public void setName_profile(String name_profile) {
		this.name_profile = name_profile;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public LocalDateTime getRegistration_date() {
		return registration_date;
	}

	public void setRegistration_date(LocalDateTime registration_date) {
		this.registration_date = registration_date;
	}

	public int getSession_time() {
		return session_time;
	}

	public void setSession_time(int session_time) {
		this.session_time = session_time;
	}

	@Override
	public String toString() {
		return "Profile [id_profile=" + id_profile + ", name_profile=" + name_profile + ", state=" + state
				+ ", registration_date=" + registration_date + ", session_time=" + session_time + "]";
	}
}
