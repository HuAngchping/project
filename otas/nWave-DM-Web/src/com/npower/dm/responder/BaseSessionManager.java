package com.npower.dm.responder;

/**
 * Session管理的接口
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2008/12/25 10:29:25 $
 */
public interface BaseSessionManager {

  /**
   * 获取到期时间
   * @return
   */
  public abstract long getExpireTimeInSeconds();

  /**
   * 设置到期时间
   * @param expireTimeInSeconds
   */
  public abstract void setExpireTimeInSeconds(long expireTimeInSeconds);

  /**
   * 新建Session,并保存至map
   * @param req
   * @return
   */
  public abstract Session createSession(SMSRequest req);

  /**
   * 根据当前request得到指定session
   * @param req
   * @return
   */
  public abstract Session loadSession(SMSRequest req);

  /**
   * 删除指定Session
   * @param session
   */
  public abstract void removeSession(Session session);

}