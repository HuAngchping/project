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
import com.npower.help.tree.SubjectTree;
import com.npower.help.tree.SubjectTreeItem;
import com.npower.help.tree.SubjectTreeServicePlugIn;

public class ShowActionHelper {

  private static String ACTION_URL_FOR_SHOW_ALLSUBJECT_TREE_NODE     = "/help/show/subjects/showAllSubject.do";

  private static String ACTION_URL_FOR_SHOW_SUBJECTCONTENT_TREE_NODE = "/help/show/subjects/showSubjectContent.do";

  /**
   * 
   */
  private ShowActionHelper() {
    super();
  }

  public static void loadShowSubjectsTree(ServletContext context, HttpServletRequest request, HelpDAOFactory factory)
      throws Exception {
    // Load data for Tree
    MenuRepository repository = loadTree(context, factory, request);
    request.setAttribute("repository", repository);
  }
  
  /**
   * @param context
   * @param repository
   * @param rootMenu
   */
  private static void convertInCacheMode(ServletContext context, MenuRepository repository, MenuComponent rootMenu) {
    SubjectTree tree = SubjectTreeServicePlugIn.getSubjectTreeService(context).getTree();
    for (SubjectTreeItem items : tree.getRootNodes()) {
      convertInCacheMode(items, repository, rootMenu);
    }
  }

  private static void convertInCacheMode(SubjectTreeItem item, MenuRepository repository, MenuComponent parentMenu) {

    MenuComponent menu = new MenuComponent();
    menu.setName("ID" + item.getId());
    String title = item.getLabel();
    menu.setTitle(title);
    menu.setParent(parentMenu);

    menu.setTarget("_self");
    if (item.getChildren().isEmpty()) {
      menu.setAction(ACTION_URL_FOR_SHOW_SUBJECTCONTENT_TREE_NODE + "?" + "subjectId=" + item.getId());
    } else {
      menu.setAction(ACTION_URL_FOR_SHOW_ALLSUBJECT_TREE_NODE + "?subjectId=" + item.getId());
    }
    repository.addMenu(menu);

    for (SubjectTreeItem child : item.getChildren()) {
      convertInCacheMode(child, repository, menu);
    }
  }

  /**
   * @param factory
   * @param repository
   * @param rootMenu
   * @throws Exception
   */
  private static void convertInDAOMode(HelpDAOFactory factory, MenuRepository repository, MenuComponent rootMenu)
      throws Exception {
    // 查询第一级Subjects
    SubjectDAO subjectDAO = factory.getSubjectDAO();
    List<Subject> firstLevelSubjects = subjectDAO.findRootSubjects();
    for (Subject subject : firstLevelSubjects) {
      convertInDAOMode(subject, repository, rootMenu);
    }
  }

  private static void convertInDAOMode(Subject subject, MenuRepository repository, MenuComponent parentMenu) {

    MenuComponent menu = new MenuComponent();
    menu.setName("ID" + subject.getSubjectId());
    String title = subject.getExternalId();
    menu.setTitle(title);
    menu.setParent(parentMenu);

    menu.setTarget("_self");
    if (subject.getParent() == null) {
      menu.setAction(ACTION_URL_FOR_SHOW_ALLSUBJECT_TREE_NODE + "?subjectId=" + subject.getSubjectId());
    } else {
      menu.setAction(ACTION_URL_FOR_SHOW_SUBJECTCONTENT_TREE_NODE + "?" + "subjectId=" + subject.getSubjectId());
    }
    repository.addMenu(menu);

    for (Subject child : subject.getChildren()) {
      convertInDAOMode(child, repository, menu);
    }
  }

  private static MenuRepository loadTree(ServletContext context, HelpDAOFactory factory, HttpServletRequest request)
      throws Exception {

    MenuRepository repository = new MenuRepository();
    // Get the repository from the application scope - and copy the
    // DisplayerMappings from it.
    MenuRepository defaultRepository = (MenuRepository) context.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
    repository.setDisplayers(defaultRepository.getDisplayers());
    MenuComponent rootMenu = new MenuComponent();
    Locale locale = RequestUtils.getUserLocale(request, null);
    MessageResources messageResources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    String label = messageResources.getMessage(locale, "otas.help.show.root.lable");
    rootMenu.setName("helpRoot");
    rootMenu.setTitle(label);
    rootMenu.setAction(ACTION_URL_FOR_SHOW_ALLSUBJECT_TREE_NODE);
    repository.addMenu(rootMenu);

    boolean inCacheMode = true;
    if (inCacheMode) {
      convertInCacheMode(context, repository, rootMenu);
    } else {
      convertInDAOMode(factory, repository, rootMenu);
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
