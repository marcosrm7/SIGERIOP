package com.edu.pe.sigeriope.repository.transaccion;

import com.edu.pe.sigeriope.bean.transaccion.ActividadesPlan;
import com.edu.pe.sigeriope.bean.transaccion.Evento;
import com.edu.pe.sigeriope.bean.transaccion.PlanAccion;
import com.edu.pe.sigeriope.bean.transaccion.PlanAccionRiesgo;
import com.edu.pe.sigeriope.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlanAccionRepoImpl implements BaseRepository<PlanAccion, Integer> {
    public static final Logger log = LoggerFactory.getLogger(PlanAccionRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<PlanAccion> findAll() {
        String  sql = "SELECT pa.nombre, pa.descripcion, pa.fecha_inicio, pa.id, pa.fecha_fin, pa.usuario_id, " +
                " pa.estado_id, e.nombre as estado," +
                " CONCAT(u.persona_nombre, ' ', u.persona_apellido) as responsable " +
                " FROM transaccion.plan_accion pa " +
                " inner join maestro.estado e on e.id=pa.estado_id " +
                " inner join administrativo.usuario u on u.usuario_id=pa.usuario_id "+
                " where pa.delete_id=2 order by pa.id ASC";
        List<PlanAccion> planAccionList;
        try {
            planAccionList = (List<PlanAccion>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(PlanAccion.class));
        } catch (EmptyResultDataAccessException e) {
            planAccionList = null;
            //log.info(String.format("proceso %s",e.getMessage()));
            // e.printStackTrace();
        }
        return planAccionList;
    }

    @Override
    public PlanAccion findBy(Integer id) {

        String  sql = "SELECT pa.nombre, pa.descripcion, pa.fecha_inicio, pa.id, pa.fecha_fin, pa.usuario_id, pa" +
                ".estado_id, e.nombre as estado," +
                "CONCAT(u.persona_nombre, ' ', u.persona_apellido) as responsable " +
                "FROM transaccion.plan_accion pa " +
                "inner join maestro.estado e on e.id=pa.estado_id " +
                "inner join administrativo.usuario u on u.usuario_id=pa.usuario_id " +
                "where pa.id=?";

        return  (PlanAccion) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(PlanAccion.class),id);
    }

    @Override
    public int save(PlanAccion planAccion) {

        String sql = "INSERT INTO transaccion.plan_accion " +
                "(nombre, descripcion, fecha_inicio, fecha_fin, usuario_id, estado_id) " +
                "VALUES(?, ?, ?, ?, ?, ?) returning id ";


        int id = jdbcTemplate.queryForObject(sql, Integer.class,
                new Object[]{
                        planAccion.getNombre(),
                        planAccion.getDescripcion(),
                        planAccion.getFechaInicio(),
                        planAccion.getFechaFin(),
                        planAccion.getUsuarioId(),
                        planAccion.getEstadoId()
                });
        log.info("returning id={}", id);
        return id;
    }

    public void insertPlanAccionRiesgo(PlanAccion pa) {

        String sql = "INSERT INTO transaccion.plan_accion_riesgo " +
                "(planaccion_id, riesgo_id) " +
                "VALUES(?, ?) ";

        List<Object[]> batchArgsList = new ArrayList<Object[]>();
        for (int riesgo: pa.getRiesgos()) {
            Object[] objectArray = {
                    pa.getId(),
                    riesgo
            };
            batchArgsList.add(objectArray);
        }
        jdbcTemplate.batchUpdate(sql, batchArgsList);
    }

    public List<PlanAccionRiesgo> getPlanAccionRiesgoByPlanaccionId(int planaccionId) {

        String sql = "SELECT id, planaccion_id, riesgo_id FROM transaccion.plan_accion_riesgo where planaccion_id=?";

        List<PlanAccionRiesgo> planAccionRiesgoList;
        try {
            planAccionRiesgoList = (List<PlanAccionRiesgo>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(PlanAccionRiesgo.class),planaccionId);
        } catch (EmptyResultDataAccessException e) {
            planAccionRiesgoList = new ArrayList<>();
            //log.info(String.format("proceso %s",e.getMessage()));
            // e.printStackTrace();
        }
        return planAccionRiesgoList;
    }

    public int deletePlanAccionRiesgoById(int planaccionId) {
        String sql = "DELETE FROM transaccion.plan_accion_riesgo WHERE planaccion_id = ?";
        return jdbcTemplate.update(sql, planaccionId);
    }

    public void insertActividadesPlan(PlanAccion pa) {

        String sql = "INSERT INTO transaccion.actividades_plan " +
                "(descripcion, planaccion_id, estado_id) " +
                "VALUES(?, ?, ?)";

        List<Object[]> batchArgsList = new ArrayList<Object[]>();
        for (ActividadesPlan ap: pa.getActividades()) {
            Object[] objectArray = {
                    ap.getDescripcion(),
                    pa.getId(),
                    ap.getEstadoId()
            };
            batchArgsList.add(objectArray);
        }
        jdbcTemplate.batchUpdate(sql, batchArgsList);
    }

    public List<ActividadesPlan> getActividadesPlanByPlanaccionId(int planaccionId) {

        String sql = "SELECT id, descripcion, planaccion_id, estado_id " +
                "FROM transaccion.actividades_plan where planaccion_id=?";

        List<ActividadesPlan> actividadesPlanList;
        try {
            actividadesPlanList = (List<ActividadesPlan>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(ActividadesPlan.class),planaccionId);
        } catch (EmptyResultDataAccessException e) {
            actividadesPlanList = new ArrayList<>();
        }
        return actividadesPlanList;
    }

    public int deleteActividadesPlanById(int planaccionId) {
        String sql = "DELETE FROM transaccion.actividades_plan WHERE planaccion_id = ?";
        return jdbcTemplate.update(sql, planaccionId);
    }

    @Override
    public int edit(PlanAccion planAccion) {

        String sql = "UPDATE transaccion.plan_accion " +
                "SET nombre=?, descripcion=?, fecha_inicio=?, fecha_fin=?, usuario_id=?,estado_id=? " +
                "WHERE id=?";

        return jdbcTemplate.update(sql, new Object[] {
                planAccion.getNombre(),
                planAccion.getDescripcion(),
                planAccion.getFechaInicio(),
                planAccion.getFechaFin(),
                planAccion.getUsuarioId(),
                planAccion.getEstadoId(),
                planAccion.getId()
        });

    }
    
    @Transactional
    @Override
    public int deleteById(Integer id) {
    	String sql = "UPDATE transaccion.plan_accion SET delete_id=1 WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }
}
