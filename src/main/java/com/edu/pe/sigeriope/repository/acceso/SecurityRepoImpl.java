package com.edu.pe.sigeriope.repository.acceso;




import com.edu.pe.sigeriope.bean.acceso.Profile;
import com.edu.pe.sigeriope.bean.acceso.User;
import com.edu.pe.sigeriope.bean.datatables.DataTableParam;
import com.edu.pe.sigeriope.bean.datatables.DataTableResponse;
import com.edu.pe.sigeriope.repository.acceso.dto.MenuPerfilDTO;
import com.edu.pe.sigeriope.repository.acceso.dto.PerfilDTO;
import com.edu.pe.sigeriope.repository.acceso.dto.UsuarioDTO;
import com.edu.pe.sigeriope.repository.acceso.dto.UsuarioListaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SecurityRepoImpl implements ISecurityRepo {

	public static final Logger log = LoggerFactory.getLogger(SecurityRepoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public UsuarioDTO findUserByLogin(String username) {
		// TODO Auto-generated method stub
		String sql = "select  a.usuario_id, a.nombre as usuario_nombre, a.clave, a.estado_id as usuario_estado_id, a.fecha_reg as usuario_fecha_reg, a.perfiles_id," +
				"a.nro_sesion, a.sesion_activa,a.reset_password,b.nombre as perfil_nombre, b.estado_id as perfil_estado_id, b.fecha_reg as perfil_fecha_reg, b.tiempo_sesion," +
				"persona_nombre, persona_apellido, correo, foto " +
				"from administrativo.usuario a " +
				"inner join administrativo.perfiles b on a.perfiles_id=b.perfiles_id " +
				"WHERE a.nombre=?";
		UsuarioDTO usuarioDTO;
		try {
			usuarioDTO = (UsuarioDTO) jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper(UsuarioDTO.class),username.toLowerCase());
		} catch (EmptyResultDataAccessException e) {
			usuarioDTO = null;
			log.info(e.getMessage());
		}

		return usuarioDTO;
	}

	@Override
	public UsuarioDTO findUserById(int id) {
		String sql = "select  a.usuario_id, a.nombre as usuario_nombre, a.clave, a.estado_id as usuario_estado_id, a.fecha_reg as usuario_fecha_reg, a.perfiles_id," +
				"a.nro_sesion, a.sesion_activa,a.reset_password,b.nombre as perfil_nombre, b.estado_id as perfil_estado_id, b.fecha_reg as perfil_fecha_reg, b.tiempo_sesion," +
				"persona_nombre, persona_apellido, correo, foto " +
				"from administrativo.usuario a " +
				"inner join administrativo.perfiles b on a.perfiles_id=b.perfiles_id " +
				"where a.usuario_id=?";

		UsuarioDTO usuarioDTO;

		try {
			usuarioDTO = (UsuarioDTO) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(UsuarioDTO.class), id);

		} catch (EmptyResultDataAccessException e) {
			usuarioDTO = null;
			log.info(e.getMessage());
		}

		return usuarioDTO;
	}


	@Override public DataTableResponse findUserAllPAginate(DataTableParam dataTableParam) {
		String conditionQuery = " ";
//		if(!flg_rpt.equals("S")) {
//			conditionQuery = " AND FLD_USUARIO = '"+fld_usuario+"' ";
//		}

		String searchQuery = " ";
		if(dataTableParam.getSearch().getValue().trim().length()>0) {
			searchQuery = " WHERE "+dataTableParam.getSearchColumn()+" LIKE '%"+dataTableParam.getSearch().getValue().trim()+"%'";
		}

		String orderQuery = " ";
		if(dataTableParam.getOrder().size()>0) {
			orderQuery = " ORDER BY "+dataTableParam.getColumns().get(dataTableParam.getOrder().get(0).getColumn()).getName()+" "+dataTableParam.getOrder().get(0).getDir();
		}

		// Total number of records without filtering
		int totalRecords = jdbcTemplate.queryForObject("select count (*) as allcount from administrativo.usuario a " +
															   "INNER JOIN administrativo.perfiles b on a.perfiles_id=b.perfiles_id " +
															   "inner join administrativo.persona p on  p.persona_id=a.persona_id"+conditionQuery, Integer.class);
		//System.out.println("Total number of records without filtering :> "+totalRecords);
		//## Total number of records with filtering
		int totalRecordwithFilter = jdbcTemplate.queryForObject("select count (*) as allcount from administrativo.usuario a " +
																		"inner join administrativo.perfiles b on a.perfiles_id=b.perfiles_id " +
																		"inner join administrativo.persona p on  p.persona_id=a.persona_id "+conditionQuery+searchQuery, Integer.class);

		String sql ="select  a.usuario_id, a.nombre as usuario_nombre, a.clave, a.estado_id as usuario_estado_id, a.fecha_reg as usuario_fecha_reg, a.perfiles_id," +
				"a.nro_sesion, a.sesion_activa,a.reset_password,b.nombre as perfil_nombre, b.estado_id as perfil_estado_id, b.fecha_reg as perfil_fecha_reg, b.tiempo_sesion," +
				"p.nombre as persona_nombre, p.apellido as persona_apellido, p.correo,p.foto " +
				"from administrativo.usuario a " +
				"inner join administrativo.perfiles b on a.perfiles_id=b.perfiles_id " +
				"inner join administrativo.persona p on  p.persona_id=a.persona_id"+conditionQuery+searchQuery+orderQuery;

		String sqlPaginate=dataTableParam.paginate(sql);
		//System.out.println(sqlPaginate);
		List<UsuarioListaDTO> usuarioDTOList;
		try {
			//listGuiaCab = (List<GuiaCab>) jdbcTemplate.query(sqlPaginate, new BeanPropertyRowMapper(GuiaCab.class));
			usuarioDTOList = jdbcTemplate.query(sqlPaginate, new BeanPropertyRowMapper(UsuarioListaDTO.class));
		} catch (EmptyResultDataAccessException e) {
			usuarioDTOList = null;
			System.out.println(e.getMessage());
			// e.printStackTrace();

		}

		DataTableResponse dataTableResponse = new DataTableResponse();
		dataTableResponse.setData(usuarioDTOList);
		dataTableResponse.setDraw(dataTableParam.getDraw());
		dataTableResponse.setiTotalDisplayRecords(totalRecordwithFilter);
		dataTableResponse.setiTotalRecords(totalRecords);
		return dataTableResponse;
	}

	@Override
	public List<UsuarioListaDTO> findUserAll() {

		// TODO Auto-generated method stub
		String sql = "SELECT a.usuario_id, a.nombre as usuario_nombre, a.clave, a.estado_id as usuario_estado_id, a.fecha_reg as usuario_fecha_reg, a.perfiles_id, a.nro_sesion, a.sesion_activa,a.reset_password,b.nombre as perfil_nombre, b.estado_id as perfil_estado_id, b.fecha_reg as perfil_fecha_reg, b.tiempo_sesion " +
				"FROM administrativo.usuario a " +
				"INNER JOIN administrativo.perfiles b on a.perfiles_id=b.perfiles_id;";

		List<UsuarioListaDTO> usuarioDTOList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(UsuarioListaDTO.class));
		return usuarioDTOList;
	}

	@Override
	@Transactional
	public int saveUser(UsuarioDTO user) {

		String sqlu = "INSERT INTO administrativo.usuario(  nombre, clave, estado_id, fecha_reg, perfiles_id, nro_sesion, sesion_activa, persona_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?,?)";

		return jdbcTemplate.update(sqlu,
				new Object[] {
						user.getUsuarioNombre(),
						user.getClave(),
						user.getEstadoId(),
						user.getUsuarioFechaReg(),
						user.getPerfilesId(),
						user.getNroSesion(),
						user.getSesionActiva(),
						user.getPersonaId()
		});
	}

	@Override
	@Transactional
	public int editUser(User user) {

		if (user.getPassword().trim().length() == 0) {
			String sql = "UPDATE administrativo.usuario SET estado_id=?, perfiles_id=? WHERE usuario_id=? ";

			 jdbcTemplate.update(sql, new Object[] {
					user.getState(),
					user.getId_profile(),
					user.getId_user()
					 });

		} else {
			String sql = "UPDATE administrativo.usuario SET estado_id=?, perfiles_id=?, clave=?, reset_password=? "
					+ "WHERE usuario_id=?";

			jdbcTemplate.update(sql, new Object[] {
					user.getState(),
					user.getId_profile(),
					user.getPassword(),
					user.isReset_password(),
					user.getId_user()
			});
		}

		String sqlUpdate = "UPDATE administrativo.persona SET nombre = ?, apellido=?, correo=? WHERE  persona_id=?";
		return jdbcTemplate.update(sqlUpdate,
									new Object[] {
											user.getName(),
											user.getLast_name(),
											user.getMail(),
											user.getId_person()});
	}

	@Override
	@Transactional
	public int deleteUser(int id) {

		String sql = "DELETE FROM administrativo.usuario WHERE usuario_id = ?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int editUserPasswordById(int id, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public int updateNewPassword(int id_user, String newPassword, boolean reset_password) {
		String sql = "UPDATE administrativo.usuario " + "SET clave=?, reset_password=? " + "WHERE usuario_id=?";
		return jdbcTemplate.update(sql, new Object[] { newPassword, reset_password, id_user });
	}

	/*********** Profile ****************/
	@Override
	public PerfilDTO findProfileById(int perfil_id) {

		String sql = "SELECT perfiles_id, nombre, estado_id, fecha_reg, tiempo_sesion FROM administrativo.perfiles where perfiles_id= ?";

		PerfilDTO perfilDTO = (PerfilDTO) jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper(PerfilDTO.class), new Object[] { perfil_id });


		return perfilDTO;
	}

	@Override
	public List<PerfilDTO> findProfileAll() {

		String sql = "SELECT perfiles_id, nombre, estado_id, fecha_reg, tiempo_sesion FROM administrativo.perfiles";
		List<PerfilDTO> perfilDTOList;
		try {
			perfilDTOList = (List<PerfilDTO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(PerfilDTO.class));

		} catch (EmptyResultDataAccessException e) {
			perfilDTOList = null;
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
		return perfilDTOList;
	}

	@Override
	public PerfilDTO findProfileByName(String name) {
		// TODO Auto-generated method stub

		String sql = "SELECT perfiles_id,nombre,estado_id,fecha_reg,tiempo_sesion FROM administrativo.perfiles where nombre = ?";

		PerfilDTO perfilDTO;

		try {
			perfilDTO = (PerfilDTO) jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper(PerfilDTO.class),name);

		} catch (EmptyResultDataAccessException e) {
			perfilDTO = null;
			System.out.println(e.getMessage());
			// e.printStackTrace();

		}
		return perfilDTO;
	}

	@Override
	@Transactional // @Transactional(rollbackFor = Exception.class)
	public int saveProfile(Profile profile) {
		// TODO Auto-generated method stub

		String sql = "INSERT INTO administrativo.perfiles(nombre, estado_id, fecha_reg, tiempo_sesion) VALUES (?, ?, ?, ?)";

		return jdbcTemplate.update(sql, new Object[] { profile.getName_profile(), profile.getState(),
				profile.getRegistration_date(), profile.getSession_time() });
	}

	@Override
	@Transactional
	public int editProfile(Profile profile) {
		// TODO Auto-generated method stub
		String sql = "UPDATE administrativo.perfiles SET nombre=?, estado_id=? WHERE perfiles_id=?";

		return jdbcTemplate.update(sql,
				new Object[] { profile.getName_profile(), profile.getState(), profile.getId_profile() });
	}

	@Override
	public List<MenuPerfilDTO> findMenuProfile() {

		String sql = "select coalesce(a.perfiles_id,0) as perfiles_id,b.menu_id,b.padre,b.nivel ,b.nombre ,b.url,\n" +
				"b.estado_id ,cast((select count(m.*) from administrativo.menu m where m.padre=b.menu_id) as int) as nroh,\n" +
				"b.icono,b.orden  \n" +
				"from administrativo.permisos a \n" +
				"right join administrativo.menu b on b.menu_id =a.menu_id \n" +
				"order by b.orden";

		List<MenuPerfilDTO> menuProfileDtos;
		try {
			menuProfileDtos = (List<MenuPerfilDTO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(MenuPerfilDTO.class));

		} catch (EmptyResultDataAccessException e) {
			menuProfileDtos = null;
			System.out.println(e.getMessage());
			// e.printStackTrace();

		}
		return menuProfileDtos;
	}

	@Override
	public List<MenuPerfilDTO> findMenuProfileByIdProfile(int id) {
		// TODO Auto-generated method stub
		/*
		String sql = "select coalesce(a.id_profile,0) as id_profile,b.id_menu,b.father,b.level,b.menu_name,b.url,"
				+ "b.state,cast((select count(m.*) from security.menu m where m.father=b.id_menu) as int) as nroh,"
				+ "b.icon,b.order "
				+ "from security.permit a right join security.menu b on b.id_menu=a.id_menu and a.id_profile=? "
				+ "order by b.order";
		*/
		String sql = "select coalesce(a.perfiles_id,0) as perfiles_id,b.menu_id,b.padre,b.nivel ,b.nombre ,b.url,\n" +
				"b.estado_id ,cast((select count(m.*) from administrativo.menu m where m.padre=b.menu_id) as int) as nroh,\n" +
				"b.icono,b.orden  \n" +
				"from administrativo.permisos a \n" +
				"right join administrativo.menu b on b.menu_id =a.menu_id and a.perfiles_id=? \n" +
				"order by b.orden";
		System.out.println(sql);
		List<MenuPerfilDTO> menuProfileDtos;
		try {
			menuProfileDtos = (List<MenuPerfilDTO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(MenuPerfilDTO.class),id);

		} catch (EmptyResultDataAccessException e) {
			menuProfileDtos = null;
			System.out.println(e.getMessage());
			// e.printStackTrace();

		}
		return menuProfileDtos;
	}

	@Override
	@Transactional
	public int assignPermits(int id_profile, int id_menu) {
		String sql = "INSERT INTO administrativo.permisos(perfiles_id, menu_id, fecha_reg) VALUES (?, ?, ?)";

		return jdbcTemplate.update(sql, new Object[] { id_profile, id_menu, LocalDateTime.now() });
	}

	@Override
	@Transactional
	public int removePermits(int id_profile, int id_menu) {
		String sql = "DELETE FROM administrativo.permisos WHERE perfiles_id=? and menu_id=?";

		return jdbcTemplate.update(sql, new Object[] { id_profile, id_menu });
	}

	@Override
	public List<UsuarioDTO> findResponsables() {
		String sql = "select usuario_id, persona_nombre ,persona_apellido  from administrativo.usuario where perfiles_id not in (1)";
		List<UsuarioDTO> usuarioDTOList;
		try {
			usuarioDTOList = (List<UsuarioDTO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(UsuarioDTO.class));

		} catch (EmptyResultDataAccessException e) {
			usuarioDTOList = null;
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
		return usuarioDTOList;
	}

}
