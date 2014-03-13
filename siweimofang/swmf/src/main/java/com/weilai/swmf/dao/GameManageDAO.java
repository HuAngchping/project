package com.weilai.swmf.dao;

import java.util.List;


public interface GameManageDAO<T> {
  
  public List<T> findByPage(String sql, int current_page, int page_size) throws Exception;

}
