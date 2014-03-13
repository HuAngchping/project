package com.weilai.swmf.web.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.weilai.swmf.factory.GameManageFactory;
import com.weilai.swmf.hibernate.entity.GameBarrier;
import com.weilai.swmf.service.GameBarrierService;
import com.weilai.swmf.web.domain.ParamsDelete;
import com.weilai.swmf.web.domain.SwmfSuccess;

@Path("/game/manage")
public class GameBarrierController {
  
  private static final Log log = LogFactory.getLog(GameBarrierController.class);

  @POST
  @Path("/barrier/update")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Object createGameBarrier(GameBarrier barrier) throws Exception {
    GameManageFactory factory = null;
    GameBarrierService service = null;
    SwmfSuccess ss = null;
    try {
      factory = GameManageFactory.newInstance();
      service = factory.createService(GameBarrierService.class);
      ss = (SwmfSuccess) service.save(barrier);
    } catch (Exception e) {
      log.error(e);
      throw e;
    }
    return ss;
  }

  @POST
  @Path("/barrier/delete")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Object deleteGame(ParamsDelete id) throws Exception {
    GameManageFactory factory = null;
    GameBarrierService service = null;
    SwmfSuccess ss = null;
    try {
      factory = GameManageFactory.newInstance();
      service = factory.createService(GameBarrierService.class);
      ss = (SwmfSuccess) service.delete(id);
    } catch (Exception e) {
      log.error(e);
      throw e;
    }
    return ss;
  }

}
