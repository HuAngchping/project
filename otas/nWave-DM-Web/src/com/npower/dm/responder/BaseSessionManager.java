package com.npower.dm.responder;

/**
 * Session����Ľӿ�
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2008/12/25 10:29:25 $
 */
public interface BaseSessionManager {

  /**
   * ��ȡ����ʱ��
   * @return
   */
  public abstract long getExpireTimeInSeconds();

  /**
   * ���õ���ʱ��
   * @param expireTimeInSeconds
   */
  public abstract void setExpireTimeInSeconds(long expireTimeInSeconds);

  /**
   * �½�Session,��������map
   * @param req
   * @return
   */
  public abstract Session createSession(SMSRequest req);

  /**
   * ���ݵ�ǰrequest�õ�ָ��session
   * @param req
   * @return
   */
  public abstract Session loadSession(SMSRequest req);

  /**
   * ɾ��ָ��Session
   * @param session
   */
  public abstract void removeSession(Session session);

}