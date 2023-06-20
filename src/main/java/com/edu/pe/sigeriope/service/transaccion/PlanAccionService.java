package com.edu.pe.sigeriope.service.transaccion;

import com.edu.pe.sigeriope.bean.transaccion.ActividadesPlan;
import com.edu.pe.sigeriope.bean.transaccion.PlanAccion;
import com.edu.pe.sigeriope.bean.transaccion.PlanAccionRiesgo;
import com.edu.pe.sigeriope.service.BaseServices;

import java.util.List;

public interface PlanAccionService extends BaseServices<PlanAccion,Integer> {

    List<PlanAccionRiesgo> getPlanAccionRiesgoByPlanaccionId(int planaccionId);

    List<ActividadesPlan> getActividadesPlanByPlanaccionId(int planaccionId);

    int edit(PlanAccion planAccion);
}
