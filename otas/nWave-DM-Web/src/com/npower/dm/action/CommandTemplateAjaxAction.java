/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/CommandTemplateAjaxAction.java,v 1.5 2009/02/18 09:59:39 hcp Exp $
 * $Revision: 1.5 $
 * $Date: 2009/02/18 09:59:39 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */

package com.npower.dm.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.npower.dm.util.XMLPrettyFormatter;

/**
 * @author LiJun
 * @version $Revision: 1.5 $ $Date: 2009/02/18 09:59:39 $
 */
public class CommandTemplateAjaxAction extends DispatchAction {
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  //--------AddLeafNodeTemplate------------
  public ActionForward getAddLeafNodeTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
      HttpServletResponse res) throws Exception {
    String template = "\n<Add>\n<LeafNode>\n<Target>./SCTSValue1</Target>\n<Data format=\"int\">800</Data>\n</LeafNode>\n</Add>\n";
    res.getWriter().write(template);
    return null;
  }
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  //--------AddInteriorNodeTemplate------------
  public ActionForward getAddInteriorNodeTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
      HttpServletResponse res) throws Exception {
    String template = "\n<Add>\n<InteriorNode>\n<Target>./SCTSNode</Target>\n</InteriorNode>\n</Add>\n";
    res.getWriter().write(template);
    return null;
  }

  /**
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  //--------DeleteTemplate------------
  public ActionForward getDeleteTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
      HttpServletResponse res) throws Exception {
    String template = "\n<Delete><Target>./SCTSValue/1</Target></Delete>\n";
    res.getWriter().write(template);
    return null;
  }
  
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  //----------ExecItemTemplate-------------
  public ActionForward getExecItemTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
	      HttpServletResponse res) throws Exception {
	    String template = "\n<Exec><Item><Target>./SCTNode/DMv1</Target></Item></Exec>\n";
	    res.getWriter().write(template);
	    return null;
	  }
  
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  //----------ExecItemDataTemplate-------------
  public ActionForward getExecItemDataTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
	      HttpServletResponse res) throws Exception {
	    String template = "\n<Exec><Item><Target>./SCTNode/DMv1</Target><Data>Parameter1</Data></Item></Exec>\n";
	    res.getWriter().write(template);
	    return null;
	  }
  
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
//----------GetTemplate-------------
  public ActionForward getGetTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
	      HttpServletResponse res) throws Exception {
	    String template = "\n<Get><Target>./SCTNode/DMv</Target></Get>\n";
	    res.getWriter().write(template);
	    return null;
	  }
  
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
//----------ReplaceIntTemplate-------------
  public ActionForward getReplaceIntTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
	      HttpServletResponse res) throws Exception {
	    String template = "\n<Replace><LeafNode><Target>./SCTSValue/1</Target><Data format=\"int\">8000</Data></LeafNode></Replace>\n";
	    res.getWriter().write(template);
	    return null;
	  }
  
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
//----------ReplaceCharTemplate-------------
  public ActionForward getReplaceCharTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
	      HttpServletResponse res) throws Exception {
	    String template = "\n<Replace><LeafNode><Target>./SCTSValue/2</Target><Data format=\"chr\">replaced leaf node 2</Data></LeafNode></Replace>\n";
	    res.getWriter().write(template);
	    return null;
	  }
  
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
//----------AlertDisplayTemplate-------------
  public ActionForward getAlertDisplayTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
	      HttpServletResponse res) throws Exception {
	    String template = "\n<Alert><Display><Text>Management in progress</Text><!-- Minimum display time in seconds (-1 for not specified) --><MinDisplayTime>60</MinDisplayTime><!-- Maximum display time in seconds (-1 for not specified) --><MaxDisplayTime>120</MaxDisplayTime></Display></Alert>\n";
	    res.getWriter().write(template);
	    return null;
	  }
  
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
//----------AlertConfirmTemplate-------------
  public ActionForward getAlertConfirmTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
	      HttpServletResponse res) throws Exception {
	    String template = "\n<Alert><Confirm><Text>Do you want to add the CNN access point?</Text><!-- Minimum display time in seconds (-1 for not specified) --><MinDisplayTime>30</MinDisplayTime><!-- Maximum display time in seconds (-1 for not specified) --><MaxDisplayTime>60</MaxDisplayTime></Confirm></Alert>\n";
	    res.getWriter().write(template);
	    return null;
	  }
  
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
//----------AlertSingleChoiceTemplate-------------
  public ActionForward getAlertSingleChoiceTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
	      HttpServletResponse res) throws Exception {
	    String template = "\n<Alert><SingleChoice><Text>Select service to configure</Text><Options><Option>CNN</Option><Option>Mobilbank</Option><Option>Game Channel</Option></Options><!-- Minimum display time in seconds (-1 for not specified) --><MinDisplayTime>10</MinDisplayTime><!-- Maximum display time in seconds (-1 for not specified) --><MaxDisplayTime>60</MaxDisplayTime><DefaultResponse>1</DefaultResponse></SingleChoice></Alert>\n";
	    res.getWriter().write(template);
	    return null;
	  }
  
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
//----------AlertMultipleChoiceTemplate-------------
  public ActionForward getAlertMultipleChoiceTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
	      HttpServletResponse res) throws Exception {
	    String template = "\n<Alert><MultipleChoice><Text>Select service to configure</Text><Options><Option>CNN</Option><Option>Mobilbank</Option><Option>Game Channel</Option> </Options><!-- Minimum display time in seconds (-1 for not specified) --><MinDisplayTime>10</MinDisplayTime><!-- Maximum display time in seconds (-1 for not specified) --><MaxDisplayTime>60</MaxDisplayTime><DefaultResponse>2-3</DefaultResponse></MultipleChoice></Alert>\n";
	    res.getWriter().write(template);
	    return null;
	  }
  
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
//----------SequenceTemplate-------------
  public ActionForward getSequenceTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
	      HttpServletResponse res) throws Exception {
	    String template = "\n<Sequence></Sequence>\n";
	    res.getWriter().write(template);
	    return null;
	  }
  
  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  
//----------AtomicTemplate-------------
  public ActionForward getAtomicTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest req,
	      HttpServletResponse res) throws Exception {
	    String template = "\n<Atomic></Atomic>\n";
	    res.getWriter().write(template);
	    return null;
	  }
  
  /**
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  //--------∏Ò ΩªØ---------
  public ActionForward doXMLFormat(ActionMapping mapping, ActionForm form, HttpServletRequest req,
      HttpServletResponse res) throws Exception {
    String content = req.getParameter("content");
    XMLPrettyFormatter formatter = null;
    try {
        formatter = new XMLPrettyFormatter(content);
        String result = formatter.format();
        res.getWriter().write(result);
    } catch(Exception e){
      res.getWriter().write("errorMsg");
    }
    return null;
  }

}
