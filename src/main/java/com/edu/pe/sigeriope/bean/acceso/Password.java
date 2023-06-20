/**
 * 
 */
package com.edu.pe.sigeriope.bean.acceso;

/**
 * @author dasamo
 *
 */
public class Password {
	private int id_user;
	private String currentPassword;
	private String newPassword;
	private String confirNewPassword;
	
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirNewPassword() {
		return confirNewPassword;
	}
	public void setConfirNewPassword(String confirNewPassword) {
		this.confirNewPassword = confirNewPassword;
	}
	@Override
	public String toString() {
		return "Password [id_user=" + id_user + ", currentPassword=" + currentPassword + ", newPassword=" + newPassword
				+ ", confirNewPassword=" + confirNewPassword + "]";
	}	
}
