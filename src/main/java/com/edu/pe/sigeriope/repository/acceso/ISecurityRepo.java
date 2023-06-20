package com.edu.pe.sigeriope.repository.acceso;

import com.edu.pe.sigeriope.bean.acceso.Profile;
import com.edu.pe.sigeriope.bean.acceso.User;
import com.edu.pe.sigeriope.bean.datatables.DataTableParam;
import com.edu.pe.sigeriope.bean.datatables.DataTableResponse;
import com.edu.pe.sigeriope.repository.acceso.dto.MenuPerfilDTO;
import com.edu.pe.sigeriope.repository.acceso.dto.PerfilDTO;
import com.edu.pe.sigeriope.repository.acceso.dto.UsuarioDTO;
import com.edu.pe.sigeriope.repository.acceso.dto.UsuarioListaDTO;

import java.util.List;

public interface ISecurityRepo {

	public UsuarioDTO findUserByLogin(String username);
	public UsuarioDTO findUserById(int id);
	public List<UsuarioListaDTO> findUserAll();
	public DataTableResponse findUserAllPAginate(DataTableParam dataTableParam);
	public int saveUser(UsuarioDTO user);
	public int editUser(User user);
	public int deleteUser(int id);
	public int editUserPasswordById(int id, String oldPassword,String newPassword);
	public int updateNewPassword(int id_user, String newPassword, boolean reset_password);
//	/*profile*/
	public PerfilDTO findProfileById(int perfil_id);
	public List<PerfilDTO> findProfileAll();
	public PerfilDTO findProfileByName(String name);
	public int saveProfile(Profile profile);
	public int editProfile(Profile profile);
//	/*menu*/
	public List<MenuPerfilDTO> findMenuProfile();
	
	public List<MenuPerfilDTO> findMenuProfileByIdProfile(int id);
	public int assignPermits(int id_profile, int id_menu);
	public int removePermits(int id_profile, int id_menu);
	public List<UsuarioDTO> findResponsables();
}
