package com.edu.pe.sigeriope.service.transaccion;

import com.edu.pe.sigeriope.bean.transaccion.ActividadesPlan;
import com.edu.pe.sigeriope.bean.transaccion.PlanAccion;

import com.edu.pe.sigeriope.bean.transaccion.PlanAccionRiesgo;
import com.edu.pe.sigeriope.repository.transaccion.PlanAccionRepoImpl;
import com.edu.pe.sigeriope.service.BaseSvc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanAccionServiceImpl extends BaseSvc<PlanAccion,Integer, PlanAccionRepoImpl> implements PlanAccionService{

    @Override
    public int save(PlanAccion planAccion){

        try {
            int id = repository.save(planAccion);
            planAccion.setId(id);
            repository.insertPlanAccionRiesgo(planAccion);
            if(planAccion.getActividades()!=null && planAccion.getActividades().size()>0) {
            	repository.insertActividadesPlan(planAccion);
            }
            return 1;
        }catch (Exception e){
            return 2;
        }

    }

    public List<PlanAccionRiesgo> getPlanAccionRiesgoByPlanaccionId(int planaccionId){
        return repository.getPlanAccionRiesgoByPlanaccionId(planaccionId);
    }

    public List<ActividadesPlan> getActividadesPlanByPlanaccionId(int planaccionId){
        return repository.getActividadesPlanByPlanaccionId(planaccionId);
    }

    @Override
    @Transactional
    public int edit(PlanAccion planAccion){
        try {
            repository.deletePlanAccionRiesgoById(planAccion.getId());
            repository.deleteActividadesPlanById(planAccion.getId());
            repository.edit(planAccion);
            repository.insertPlanAccionRiesgo(planAccion);
            if(planAccion.getActividades()!=null && planAccion.getActividades().size()>0) {
                repository.insertActividadesPlan(planAccion);
            }
            return 1;
        }catch (Exception e){
            return 2;
        }
    }
}
