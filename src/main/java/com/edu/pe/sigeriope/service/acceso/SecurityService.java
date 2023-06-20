/**
 * 
 */
package com.edu.pe.sigeriope.service.acceso;



import com.edu.pe.sigeriope.bean.acceso.Profile;
import com.edu.pe.sigeriope.bean.acceso.Responsable;
import com.edu.pe.sigeriope.bean.acceso.User;
import com.edu.pe.sigeriope.bean.datatables.DataTableParam;
import com.edu.pe.sigeriope.bean.datatables.DataTableResponse;
import com.edu.pe.sigeriope.bean.menu.MenuProfile;
import com.edu.pe.sigeriope.repository.acceso.ISecurityRepo;
import com.edu.pe.sigeriope.repository.acceso.dto.MenuPerfilDTO;
import com.edu.pe.sigeriope.repository.acceso.dto.PerfilDTO;
import com.edu.pe.sigeriope.repository.acceso.dto.UsuarioDTO;
import com.edu.pe.sigeriope.repository.acceso.dto.UsuarioListaDTO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dasamo
 *
 */

@Service
public class SecurityService {
	
	@Autowired
	ISecurityRepo securityRepo;
	
	public User findUserByLogin(String login) {
		UsuarioDTO usuarioDTO = securityRepo.findUserByLogin(login);

		if(usuarioDTO == null)
			return null;

		User user = new User();
		user.setId_user(usuarioDTO.getUsuarioId());
		user.setActive_session(usuarioDTO.getSesionActiva());
		user.setUsername(usuarioDTO.getUsuarioNombre());
		user.setPassword(usuarioDTO.getClave());
		user.setState(usuarioDTO.getEstadoId());
		user.setRegistration_date(usuarioDTO.getUsuarioFechaReg());
		user.setId_profile(usuarioDTO.getPerfilesId());
		user.setSession_number(usuarioDTO.getNroSesion());
		user.setReset_password(usuarioDTO.isResetPassword());
		user.setName(usuarioDTO.getPersonaNombre());
		user.setLast_name(usuarioDTO.getPersonaApellido());
		user.setMail(usuarioDTO.getCorreo());
		user.setPhoto(usuarioDTO.getFoto());
		return user;
	}
	
	public Profile findProfileById(int id) {
		PerfilDTO perfilDTO = securityRepo.findProfileById(id);
		Profile profile = new Profile();
		profile.setId_profile(perfilDTO.getPerfilesId());
		profile.setName_profile(perfilDTO.getNombre());
		profile.setState(perfilDTO.getEstadoId());
		profile.setSession_time(perfilDTO.getTiempoSesion());
		return profile;
	}
	
	
	public User findUserById(int id) {

		UsuarioDTO usuarioDTO = securityRepo.findUserById(id);
		if(usuarioDTO == null)
			return null;

		User user = new User();
		user.setId_user(usuarioDTO.getUsuarioId());
		user.setActive_session(usuarioDTO.getSesionActiva());
		user.setUsername(usuarioDTO.getUsuarioNombre());
		user.setPassword(usuarioDTO.getClave());
		user.setState(usuarioDTO.getEstadoId());
		user.setRegistration_date(usuarioDTO.getUsuarioFechaReg());
		user.setId_profile(usuarioDTO.getPerfilesId());
		user.setSession_number(usuarioDTO.getNroSesion());
		user.setReset_password(usuarioDTO.isResetPassword());
		user.setName(usuarioDTO.getPersonaNombre());
		user.setLast_name(usuarioDTO.getPersonaApellido());
		user.setMail(usuarioDTO.getCorreo());
		user.setPhoto(usuarioDTO.getFoto());
		user.setId_person(usuarioDTO.getPersonaId());

		return user;
	 }
	public DataTableResponse findUserProfileAllPaginate(DataTableParam dataTableParam) {

		DataTableResponse response = securityRepo.findUserAllPAginate(dataTableParam);
		List<UsuarioListaDTO> usuarioDTOList  = response.getData();
		List<User> userList =  usuarioDTOList.stream().filter(useDTO -> !useDTO.getUsuarioNombre().equals("admin")).map(usuarioListaDTO ->{
			User user = new User();
			user.setId_user(usuarioListaDTO.getUsuarioId());
			user.setActive_session(usuarioListaDTO.getSesionActiva());
			user.setUsername(usuarioListaDTO.getUsuarioNombre());
			user.setPassword(usuarioListaDTO.getClave());
			user.setState(usuarioListaDTO.getUsuarioEstadoId());
			user.setRegistration_date(usuarioListaDTO.getUsuarioFechaReg());
			user.setId_profile(usuarioListaDTO.getPerfilesId());
			user.setSession_number(usuarioListaDTO.getNroSesion());
			user.setReset_password(usuarioListaDTO.isResetPassword());
			user.setProfile(new Profile());
			user.getProfile().setId_profile(usuarioListaDTO.getPerfilesId());
			user.getProfile().setName_profile(usuarioListaDTO.getPerfilNombre());
			user.getProfile().setState(usuarioListaDTO.getPerfilEstadoId());
			user.getProfile().setSession_time(usuarioListaDTO.getTiempoSesion());
			user.getProfile().setRegistration_date(usuarioListaDTO.getPerfilFechaReg());
			user.setName(usuarioListaDTO.getPersonaNombre());
			user.setLast_name(usuarioListaDTO.getPersonaApellido());
			user.setMail(usuarioListaDTO.getCorreo());
			user.setPhoto(usuarioListaDTO.getFoto());
			return user;
		}).collect(Collectors.toList());

		response.setData(userList);
		return response;
	}
	 public List<User> findUserProfileAll() {

		 List<User>  userList = securityRepo.findUserAll().stream().map( usuarioListaDTO ->{
			 User user = new User();
			 user.setId_user(usuarioListaDTO.getUsuarioId());
			 user.setActive_session(usuarioListaDTO.getSesionActiva());
			 user.setUsername(usuarioListaDTO.getUsuarioNombre());
			 user.setPassword(usuarioListaDTO.getClave());
			 user.setState(usuarioListaDTO.getUsuarioEstadoId());
			 user.setRegistration_date(usuarioListaDTO.getUsuarioFechaReg());
			 user.setId_profile(usuarioListaDTO.getPerfilesId());
			 user.setSession_number(usuarioListaDTO.getNroSesion());
			 user.setReset_password(usuarioListaDTO.isResetPassword());
			 user.setProfile(new Profile());
			 user.getProfile().setId_profile(usuarioListaDTO.getPerfilesId());
			 user.getProfile().setName_profile(usuarioListaDTO.getPerfilNombre());
			 user.getProfile().setState(usuarioListaDTO.getPerfilEstadoId());
			 user.getProfile().setSession_time(usuarioListaDTO.getTiempoSesion());
			 user.getProfile().setRegistration_date(usuarioListaDTO.getPerfilFechaReg());
			 return user;
		 }).collect(Collectors.toList());
		 return userList;
	 }
	
	 public int saveUser(User user) {

		 user.setRegistration_date(LocalDateTime.now());
		 user.setState(2);
		 user.setSession_number(1);
		 user.setActive_session(1);
		 user.setPhoto("user.png");
		 user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

		 UsuarioDTO usuarioDTO = new UsuarioDTO();
		 usuarioDTO.setUsuarioNombre(user.getUsername());
		 usuarioDTO.setClave(user.getPassword());
		 usuarioDTO.setPerfilesId(user.getId_profile());
		 usuarioDTO.setEstadoId(user.getState());
		 usuarioDTO.setUsuarioFechaReg(user.getRegistration_date());
		 usuarioDTO.setSesionActiva(user.getActive_session());
		 usuarioDTO.setNroSesion(user.getSession_number());
		 usuarioDTO.setCorreo(user.getMail());
		 usuarioDTO.setPersonaNombre(user.getName());
		 usuarioDTO.setPersonaApellido(user.getLast_name());
	 	return securityRepo.saveUser(usuarioDTO);

	 }
	 
	 public int editUser(User user) {
		 return securityRepo.editUser(user);
	 }
	 
	 public int deleteUser(int id) {
		 return securityRepo.deleteUser(id);
	 }
	 
	 public int editUserPasswordById(int id, String oldPassword,String newPassword) {
		 return securityRepo.editUserPasswordById(id, oldPassword, newPassword);
	 }
	 
	 public int updateNewPassword(int id_user, String newPassword, boolean reset_password) {
		 return securityRepo.updateNewPassword(id_user, newPassword, reset_password);
	 }
	
	 /*profile*/
	 public List<Profile> findProfileAll(){
		 
		 return securityRepo.findProfileAll().stream().map( perfilDTO -> {
			 Profile profile = new Profile();
			 profile.setId_profile(perfilDTO.getPerfilesId());
			 profile.setName_profile(perfilDTO.getNombre());
			 profile.setState(perfilDTO.getEstadoId());
			 profile.setSession_time(perfilDTO.getTiempoSesion());
			 profile.setRegistration_date(perfilDTO.getFechaReg());
			 return profile;
		 }).collect(Collectors.toList());
	 }
	 
	 public Profile findProfileByName(String name){
		 PerfilDTO perfilDTO =securityRepo.findProfileByName(name);
		 if(perfilDTO == null)
			 return null;
		 Profile profile = new Profile();
		 profile.setId_profile(perfilDTO.getPerfilesId());
		 profile.setName_profile(perfilDTO.getNombre());
		 profile.setState(perfilDTO.getEstadoId());
		 profile.setSession_time(perfilDTO.getTiempoSesion());
		 profile.setRegistration_date(perfilDTO.getFechaReg());
		 return profile;
	 }
	 
	 public int saveProfile(Profile profile){
		 return securityRepo.saveProfile(profile);
	 }
	 
	 public int editProfile(Profile profile){
		 return securityRepo.editProfile(profile);
	 }
	 
//	 /*menu*/
	 public List<MenuProfile> findMenuProfile() {

		 List<MenuPerfilDTO> menuPerfilDTOList = securityRepo.findMenuProfile();

		 List<MenuProfile> menuProfiles = menuPerfilDTOList.stream().map( menu -> {
			 MenuProfile menuProfile = new MenuProfile();
			 menuProfile.setId_profile(menu.getPerfilesId());
			 menuProfile.setId_menu(menu.getMenuId());
			 menuProfile.setFather(menu.getPadre());
			 menuProfile.setLevel(menu.getNivel());
			 menuProfile.setMenu_name(menu.getNombre());
			 menuProfile.setUrl(menu.getUrl());
			 menuProfile.setState(menu.getEstadoId());
			 menuProfile.setNroh(menu.getNroh());
			 menuProfile.setIcon(menu.getIcono());
			 menuProfile.setOrder(menu.getOrden());
			 return menuProfile;
		 }).collect(Collectors.toList());
		 return menuProfiles;
	 }
	 
	 public List<MenuProfile> findMenuProfileByIdProfile(int id){

		 List<MenuPerfilDTO> menuPerfilDTOList = securityRepo.findMenuProfileByIdProfile(id);

		 List<MenuProfile> menuProfiles = menuPerfilDTOList.stream().map( menu -> {
			 MenuProfile menuProfile = new MenuProfile();
			 menuProfile.setId_profile(menu.getPerfilesId());
			 menuProfile.setId_menu(menu.getMenuId());
			 menuProfile.setFather(menu.getPadre());
			 menuProfile.setLevel(menu.getNivel());
			 menuProfile.setMenu_name(menu.getNombre());
			 menuProfile.setUrl(menu.getUrl());
			 menuProfile.setState(menu.getEstadoId());
			 menuProfile.setNroh(menu.getNroh());
			 menuProfile.setIcon(menu.getIcono());
			 menuProfile.setOrder(menu.getOrden());
			 return menuProfile;
		 }).collect(Collectors.toList());
		 return menuProfiles;
	 }
	 
	 public int assignPermits(int id_profile, int id_menu){
		 return securityRepo.assignPermits(id_profile, id_menu);
	 }
	 
	 public int removePermits(int id_profile, int id_menu){
		 return securityRepo.removePermits(id_profile, id_menu);
	 }

	 public List<Responsable> getResponsables(){
		return securityRepo.findResponsables().stream().map( us -> {
			return new Responsable(us.getUsuarioId(),new StringBuilder(us.getPersonaNombre())
					.append(" ").append(us.getPersonaApellido()).toString());
		}).collect(Collectors.toList());
	 }
}
