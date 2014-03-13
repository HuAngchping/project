package com.weilai.swmf.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

public class GameDAO<T> extends GameBaseDAO<T> implements GameManageDAO<T> {
  
  private static final Log log = LogFactory.getLog(GameDAO.class);

  @SuppressWarnings("unchecked")
  public List<T> findByPage(String sql, int current_page, int page_size) throws Exception {
    log.debug("finding Game instance with params: ");
    Query query = null;
    try {
      query = super.getQuery(sql);
      query.setFirstResult((current_page - 1) * page_size);
      query.setMaxResults(page_size);
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
    return query.list();
  }

}
