package com.weilai.swmf.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.weilai.swmf.hibernate.HibernateSessionFactory;

public class HibernateSessionFilter implements Filter {

  public void destroy() {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    try {
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      HibernateSessionFactory.closeSession();
    }
  }

  public void init(FilterConfig filterConfig) throws ServletException {
  }

}
