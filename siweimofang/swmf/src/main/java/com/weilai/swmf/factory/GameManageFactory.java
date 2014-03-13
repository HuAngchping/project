package com.weilai.swmf.factory;

import com.weilai.swmf.dao.GameManageDAO;
import com.weilai.swmf.service.GameBaseService;

public class GameManageFactory {
  
  private GameManageFactory(){
  }

  public static GameManageFactory newInstance() {
    return new GameManageFactory();
  }
  
  @SuppressWarnings("unchecked")
  public <T extends GameManageDAO<T>> T createDAO(Class<T> c) {
    GameManageDAO<T> dao = null;
    try {
      dao = (GameManageDAO<T>) Class.forName(c.getName()).newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return (T) dao;
  }
  
  @SuppressWarnings("unchecked")
  public <T extends GameBaseService> T createService(Class<T> c) {
    GameBaseService service = null;
    try {
      service = (GameBaseService) Class.forName(c.getName()).newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return (T) service;
  }
  
}
