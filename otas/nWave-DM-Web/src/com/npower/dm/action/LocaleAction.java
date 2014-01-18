/*
 * $Id: LocaleAction.java,v 1.5 2007/08/29 10:13:01 zhao Exp $ 
 */
package com.npower.dm.action;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * <p>Change user's Struts {@link java.util.Locale}.</p>
 */
public final class LocaleAction extends BaseAction {

    /**
   * 
   */
  private static final String COOKIE_LOCALE_COUNTRY = "otas_dm_web_admin_country";

    /**
   * 
   */
  private static final String COOKIE_LOCALE_LANGUAGE = "otas_dm_web_admin_language";

    /**
     * <p>Return true if parameter is null or trims to empty.</p>
     * @param string The string to text; may be  null
     * @return true if parameter is null or empty
     */
    private boolean isBlank(String string) {
        return ((string==null) || (string.trim().length()==0));
    }

    /**
     * <p>Parameter for {@link java.util.Locale} language property.
     * ["language"]</p>
     */
    private static final String LANGUAGE = "language" ;

    /**
     * <p>Parameter for {@link java.util.Locale} country property.
     * ["country"]</p>
     */
    private static final String COUNTRY = "country";

    /**
     * <p>Parameter for response page URI. ["page"]</p>
     */
    private static final String PAGE = "page";

    /**
     * <p>Parameter for response forward name.
     * ["forward"]</p>
     */
    private static final String FORWARD = "forward";

    /**
     * Restore Locale from cookies. If not found from cookies, will use defaultLocale.
     * @param defaultLocale
     * @param request
     */
    public static void restoreLocaleFromCookies(HttpServletRequest request) {
      HttpSession session = request.getSession();
      Locale localeFromSession = (Locale)session.getAttribute(Globals.LOCALE_KEY);
      Locale localeFromCookie = getLocaleFromCookie(request);

      Locale locale = null;
      if (localeFromSession == null && localeFromCookie == null) {
         // Using default system locale
         locale = request.getLocale();
      } else if (localeFromCookie == null) {
        locale = localeFromSession;
      } else {
        // Locale from cookie is not null
        // Always using locale from cookie
        locale = localeFromCookie;
      }
      // Save locale into Session
      session.setAttribute(Globals.LOCALE_KEY, locale);
    }

    /**
     * @param request
     * @return
     */
    private static Locale getLocaleFromCookie(HttpServletRequest request) {
      Locale localeFromCookie = null;
      Cookie[] cookies = request.getCookies();
      if (cookies != null && cookies.length > 0) {
         // Get Default Locale
         String language = null;
         String country = null;
         for (Cookie cookie: cookies) {
             String name = cookie.getName();
             String value = cookie.getValue();
             if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)) {
                if (name.equals(COOKIE_LOCALE_LANGUAGE)) { 
                   language = value;
                } else if (name.equals(COOKIE_LOCALE_COUNTRY)) {
                  country = value;
                }
             }
         }
         if ((StringUtils.isNotEmpty(language)) && (StringUtils.isNotEmpty(country))) {
            localeFromCookie = new Locale(language, country);
         } else if (StringUtils.isNotEmpty(language)) {
           localeFromCookie = new Locale(language, "");
         }
      }
      return localeFromCookie;
    }

    /**
     * <p>
     * Change the user's Struts {@link java.util.Locale} based on request
     * parameters for "language", "country".
     * After setting the Locale, control is forwarded to an URI path
     * indicated by a "page" parameter, or a forward indicated by a
     * "forward" parameter, or to a forward given as the mappings
     * "parameter" property.
     * The response location must be specified one of these ways.
     * </p>
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @return An ActionForward indicate the resources that will render the response
     * @exception Exception if an input/output error or servlet exception occurs
     */
    public ActionForward execute(ActionMapping mapping,
                 ActionForm form,
                 HttpServletRequest request,
                 HttpServletResponse response)
    throws Exception {

        String language = request.getParameter(LANGUAGE);
        String country = request.getParameter(COUNTRY);

        //Locale locale = getLocale(request);
        Locale locale = request.getLocale();

        if ((!isBlank(language)) && (!isBlank(country))) {
            locale = new Locale(language, country);
        } else if (!isBlank(language)) {
            locale = new Locale(language, "");
        }

        HttpSession session = request.getSession();
        session.setAttribute(Globals.LOCALE_KEY, locale);

        String target = request.getParameter(PAGE);
        if (!isBlank(target)) return new ActionForward(target);

        target = request.getParameter(FORWARD);
        if (isBlank(target)) target = mapping.getParameter();
        if (isBlank(target)) {
           target = "login";
           //throw new Exception(LOCALE_LOG);
        }
        
        // Set cookies
        Cookie languageCookie = new Cookie(LocaleAction.COOKIE_LOCALE_LANGUAGE, locale.getLanguage());
        // Never expired
        languageCookie.setMaxAge(Integer.MAX_VALUE);
        languageCookie.setPath("/");
        response.addCookie(languageCookie);
        Cookie countryCookie = new Cookie(LocaleAction.COOKIE_LOCALE_COUNTRY, locale.getCountry());
        // Never expired
        countryCookie.setMaxAge(Integer.MAX_VALUE);
        countryCookie.setPath("/");
        response.addCookie(countryCookie);
        
        // Using redirect mode for return cookies to browser.
        // forward.setRedirect(false);
        return mapping.findForward(target);
    }
}
