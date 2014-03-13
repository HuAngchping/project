package com.weilai.swmf.dao;


public interface GameManageBaseDAO<T> {

  /**
   * 保存或更新
   * @param instance
   */
  public void save(T instance);
  
  /**
   * 保存或更新
   * @param instance
   * @return Object
   */
  public T merge(T instance);
  
  /**
   * 通过ID查询
   * @param entityClass
   * @param id
   * @return Object
   */
  public T findById(Class<T> entityClass, long id);
  
  /**
   * 删除
   * @param instance
   */
  public void delete(T instance);
  
}
