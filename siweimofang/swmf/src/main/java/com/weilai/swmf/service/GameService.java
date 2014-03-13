package com.weilai.swmf.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.weilai.swmf.dao.GameBarrierDAO;
import com.weilai.swmf.dao.GameDAO;
import com.weilai.swmf.factory.GameManageFactory;
import com.weilai.swmf.hibernate.entity.Game;
import com.weilai.swmf.hibernate.entity.GameBarrier;
import com.weilai.swmf.web.domain.ParamsDelete;
import com.weilai.swmf.web.domain.ParamsGames;
import com.weilai.swmf.web.domain.SwmfSuccess;

public class GameService implements GameBaseService {

  private static final Log log4j = LogFactory.getLog(GameService.class);

  @SuppressWarnings("unchecked")
  public Object save(Object game) throws Exception {
    GameManageFactory factory = null;
    GameDAO<Game> dao = null;
    try {
      factory = GameManageFactory.newInstance();
      dao = factory.createDAO(GameDAO.class);
      dao.beginTransaction();
      dao.save((Game) game);
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
    GameDAO<Game> dao = null;
    Game game = null;
    try {
      factory = GameManageFactory.newInstance();
      dao = factory.createDAO(GameDAO.class);
      game = dao.findById(Game.class, id);
    } catch (Exception e) {
      log4j.error(e.getMessage(), e);
      throw e;
    }
    return game;
  }

  @SuppressWarnings("unchecked")
  public Object delete(Object obj) throws Exception {
    GameManageFactory factory = null;
    GameDAO<Game> dao = null;
    GameBarrierDAO<GameBarrier> bdao = null;
    ParamsDelete paramsDelete = (ParamsDelete) obj;
    try {
      factory = GameManageFactory.newInstance();
      dao = factory.createDAO(GameDAO.class);
      bdao = factory.createDAO(GameBarrierDAO.class);
      List<GameBarrier> barriers = bdao.findByGameId(paramsDelete.id);
      Game game = (Game) this.getById(paramsDelete.id);
      dao.beginTransaction();
      dao.delete(game);
      for(GameBarrier barrier : barriers) {
        bdao.delete(barrier);
      }
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
  public List<Game> getGameByParams(Object obj) throws Exception {
    GameManageFactory factory = null;
    GameDAO<Game> dao = null;
    List<Game> games = null;
    ParamsGames params = (ParamsGames) obj;
    String sql = "";
    try {
      factory = GameManageFactory.newInstance();
      dao = factory.createDAO(GameDAO.class);
      games = dao.findByPage(sql, params.current_page, params.page_size);
    } catch (Exception e) {
      log4j.error(e.getMessage(), e);
      throw e;
    }
    return games;
  }

}
