package com.weilai.swmf.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.weilai.swmf.dao.GameBarrierDAO;
import com.weilai.swmf.factory.GameManageFactory;
import com.weilai.swmf.hibernate.entity.GameBarrier;
import com.weilai.swmf.web.domain.SwmfSuccess;

public class GameBarrierService implements GameBaseService {

  private static final Log log4j = LogFactory.getLog(GameBarrierService.class);

  @SuppressWarnings("unchecked")
  public Object save(Object obj) throws Exception {
    GameManageFactory factory = null;
    GameBarrierDAO<GameBarrier> dao = null;
    try {
      factory = GameManageFactory.newInstance();
      dao = factory.createDAO(GameBarrierDAO.class);
      dao.beginTransaction();
      dao.save((GameBarrier) obj);
      dao.commit();
    } catch (Exception e) {
      log4j.error(e.getMessage(), e);
      try {
        dao.rollback();
      } catch (Exception e1) {
        log4j.error(e1.getMessage(), e1);
      }
      throw e;
    }
    return new SwmfSuccess();
  }

  @SuppressWarnings("unchecked")
  public Object getById(long id) throws Exception {
    GameManageFactory factory = null;
    GameBarrierDAO<GameBarrier> dao = null;
    GameBarrier gameBarrier = null;
    try {
      factory = GameManageFactory.newInstance();
      dao = factory.createDAO(GameBarrierDAO.class);
      gameBarrier = dao.findById(GameBarrier.class, id);
    } catch (Exception e) {
      log4j.error(e.getMessage(), e);
      throw e;
    }
    return gameBarrier;
  }

  @SuppressWarnings("unchecked")
  public Object delete(Object obj) throws Exception {
    GameManageFactory factory = null;
    GameBarrierDAO<GameBarrier> dao = null;
    try {
      factory = GameManageFactory.newInstance();
      dao = factory.createDAO(GameBarrierDAO.class);
      dao.beginTransaction();
      dao.delete((GameBarrier) obj);
      dao.commit();
    } catch (Exception e) {
      log4j.error(e.getMessage(), e);
      try {
        dao.rollback();
      } catch (Exception e1) {
        log4j.error(e1.getMessage(), e1);
      }
      throw e;
    }
    return new SwmfSuccess();
  }

}
