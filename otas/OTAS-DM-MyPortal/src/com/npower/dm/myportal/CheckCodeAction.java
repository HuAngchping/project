package com.npower.dm.myportal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CheckCodeAction extends Action {
  
  private static Log log = LogFactory.getLog(CheckCodeAction.class);
  
  /**
   * Attribute name in HttpSession to hold the checkcode
   */
  public static final String CHECKCODE_ATTRIBUTE_NAME = "checkcode";

  class CheckCodeImage {
    
    private String checkcode="";
    
    /**
     * Default constructor
     */
    public CheckCodeImage() {
      super();
    }
    
    /**
     * @return the checkcode
     */
    public String getCheckcode() {
      return this.checkcode;
    }

    /**
     * @param checkcode the checkcode to set
     */
    public void setCheckcode(String checkcode) {
      this.checkcode = checkcode;
    }
    
    private Color getRandColor(int fc,int bc){//������Χ��������ɫ
      Random random = new Random();
      if(fc>255) fc=255;
      if(bc>255) bc=255;
      int r=fc+random.nextInt(bc-fc);
      int g=fc+random.nextInt(bc-fc);
      int b=fc+random.nextInt(bc-fc);
      return new Color(r,g,b);
    }
    
    public BufferedImage creatImage(){
      // ���ڴ��д���ͼ��
      int width=55, height=20;
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      // ��ȡͼ��������
      Graphics g = image.getGraphics();
      //���������
      Random random = new Random();
      // �趨����ɫ
      g.setColor(getRandColor(200,250));
      g.fillRect(0, 0, width, height);
      //�趨����
      g.setFont(new Font("Times New Roman",Font.PLAIN,18));
      //���߿�
      //g.setColor(new Color());
      //g.drawRect(0,0,width-1,height-1);
      // �������255�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
      g.setColor(getRandColor(160,200));
      for (int i=0;i<255;i++)
      {
          int x = random.nextInt(width);
          int y = random.nextInt(height);
          int xl = random.nextInt(12);
          int yl = random.nextInt(12);
          g.drawLine(x,y,x+xl,y+yl);
      }
      // ȡ�����������֤��(8λ����)
      //String rand = request.getParameter("rand");
      //rand = rand.substring(0,rand.indexOf("."));
      for (int i=0;i<4;i++){
          String rand=String.valueOf(random.nextInt(10));
          checkcode+=rand;
          //����֤����ʾ��ͼ����
          g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
          //���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������
          g.drawString(rand,13*i+6,16);
      }
      // ͼ����Ч
      g.dispose();
      return image;
    }

  }
  
  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods

  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    log.debug("Generate CheckCode for secure web request.");
    response.reset();
    response.setContentType("image/jpeg");
    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    
    CheckCodeImage image = new CheckCodeImage();
    //  ����֤�����SESSION
    //  ���ͼ��ҳ��
    ImageIO.write(image.creatImage(), "JPEG", response.getOutputStream());
    HttpSession session = request.getSession();
    session.setAttribute(CHECKCODE_ATTRIBUTE_NAME, image.getCheckcode());
    log.debug("Generate CheckCode for secure web request, current checkcode: " + image.getCheckcode() + ", sessionID: " + session.getId());
    return null;
  }
  
  /**
   * Return Current CheckCode
   * @param request
   * @return
   */
  public static String getCurrentCheckCode(HttpServletRequest request) {
    HttpSession session = request.getSession();
    return (String)session.getAttribute(CHECKCODE_ATTRIBUTE_NAME);
  }
}
