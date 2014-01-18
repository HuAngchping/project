/**
 * test
 */
package com.npower.help.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import com.npower.help.core.Subject;
import com.npower.help.core.SubjectLocale;
import com.npower.help.dao.HelpDAOFactory;
import com.npower.help.dao.SubjectDAO;
import com.npower.help.dao.SubjectLocaleDAO;

/**
 * @author Huang
 * 
 */
public class ActionHelper {

  private static String ACTION_URL_FOR_SUBJECTS_TREE_NODE    = "/help/admin/subjects/subjects.do";

  private static String ACTION_URL_FOR_VIEWCONTENT_TREE_NODE = "/help/admin/subjects/viewContent.do";

  /**
   * 
   */
  private ActionHelper() {
    super();
  }

  public static void loadSubjectsTree(ServletContext context, HttpServletRequest request, HelpDAOFactory factory)
      throws Exception {
    // Load data for Tree
    MenuRepository repository = loadTree(context, factory, request);
    request.setAttribute("repository", repository);
  }

  private static void convert(Subject subject, MenuRepository repository, MenuComponent parentMenu) {

    MenuComponent menu = new MenuComponent();
    menu.setName("ID" + subject.getSubjectId());
    String title = subject.getExternalId();
    menu.setTitle(title);
    menu.setParent(parentMenu);

    menu.setTarget("_self");
    if (subject.getChildren().isEmpty()) {
      menu.setAction(ACTION_URL_FOR_VIEWCONTENT_TREE_NODE + "?" + "subjectId=" + subject.getSubjectId());
    } else {
      menu.setAction(ACTION_URL_FOR_SUBJECTS_TREE_NODE + "?subjectId=" + subject.getSubjectId());
    }
    repository.addMenu(menu);

    for (Subject child : subject.getChildren()) {
      convert(child, repository, menu);
    }
  }

  private static MenuRepository loadTree(ServletContext context, HelpDAOFactory factory, HttpServletRequest request)
      throws Exception {
    SubjectDAO subjectDAO = factory.getSubjectDAO();

    MenuRepository repository = new MenuRepository();
    // Get the repository from the application scope - and copy the
    // DisplayerMappings from it.
    MenuRepository defaultRepository = (MenuRepository) context.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
    repository.setDisplayers(defaultRepository.getDisplayers());
    MenuComponent rootMenu = new MenuComponent();
    Locale locale = RequestUtils.getUserLocale(request, null);
    MessageResources messageResources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    String label = messageResources.getMessage(locale, "otas.help.admin.root.lable");
    rootMenu.setName("helpRoot");
    rootMenu.setTitle(label);
    rootMenu.setAction(ACTION_URL_FOR_SUBJECTS_TREE_NODE);
    repository.addMenu(rootMenu);

    // 查询第一级Subjects
    List<Subject> firstLevelSubjects = subjectDAO.findRootSubjects();
    for (Subject subject : firstLevelSubjects) {
      convert(subject, repository, rootMenu);
    }

    return repository;
  }

  /**
   * Return default locale
   * 
   * @param factory
   * @return
   * @throws Exception
   */
  public static SubjectLocale getDefaultSubjectLocale(HelpDAOFactory factory) throws Exception {
    SubjectLocaleDAO dao = factory.getSubjectLocaleDAO();
    SubjectLocale locale = dao.findById(Locale.getDefault().toString());
    return locale;
  }

}
