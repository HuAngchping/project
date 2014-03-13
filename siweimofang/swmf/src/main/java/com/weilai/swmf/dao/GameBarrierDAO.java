package com.weilai.swmf.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.weilai.swmf.hibernate.entity.GameBarrier;


public class GameBarrierDAO<T> extends GameBaseDAO<T> implements GameManageDAO<T> {
  
  private static final Log log = LogFactory.getLog(GameDAO.class);

  public List<T> findByPage(String sql, int current_page, int page_size) {
    return null;
  }
  
  @SuppressWarnings("unchecked")
  public List<GameBarrier> findByGameId(long game_id) throws Exception {
    log.debug("finding GameBarrier instance with GAME_ID: " + game_id);
    Criteria criteria = null;
    try {
      criteria = super.getCriteria(GameBarrier.class.getName());
      Restrictions.eq("game_id", game_id);
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
    return criteria.list();
  }

}
