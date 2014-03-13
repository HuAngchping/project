package com.weilai.swmf.service;

public interface GameBaseService {

  public Object save(Object obj) throws Exception;
  
  public Object getById(long id) throws Exception;
  
  public Object delete(Object obj) throws Exception;
}
