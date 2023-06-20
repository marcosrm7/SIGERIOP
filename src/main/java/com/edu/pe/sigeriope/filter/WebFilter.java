package com.edu.pe.sigeriope.filter;


import com.edu.pe.sigeriope.bean.acceso.User;
import com.edu.pe.sigeriope.bean.menu.MenuProfile;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebFilter implements Filter {


	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		boolean check = false;
		//try {
			if (session != null && session.getAttribute("user")!=null) {

				//response.sendRedirect(request.getContextPath()+"/error/403");
				//System.out.println("sesion valida"+request.getRequestURI()+"---"+!request.getServletPath().matches("/security/setting/newPassword"));
				//System.out.println(request.getMethod()); request.getMethod().equals("POST")
//				if(((User) session.getAttribute("user")).isReset_password() && (!request.getServletPath().matches("newPassword") && !request.getServletPath().matches("sign_out"))) {
				if(((User) session.getAttribute("user")).isReset_password() && !request.getRequestURI().matches(".*(newPassword|setNewPassword|sign_out|css|jpg|png|gif|js|svg|eot|ttf|woff|woff2|map|ico)")) {
					//System.out.println("nuevo passsword**************");
					response.sendRedirect(request.getContextPath()+"/security/setting/newPassword");
					//request.getRequestDispatcher(request.getContextPath()+"/security/user/newPassword").forward(request, response);
				}else{
					//System.out.println("pasooooooooooooooo **********");

					if (request.getRequestURI().matches(".*(home|error|css|jpg|png|gif|js|svg|eot|ttf|woff|woff2|map|ico)")
							|| request.getContextPath().concat("/").equals(request.getRequestURI())
							|| request.getServletPath().matches("/login/sign_out")
							|| request.getServletPath().matches("/error/.*")
							|| request.getServletPath().matches("/Rest/.*")
							|| request.getServletPath().matches("/security/setting/.*")
							|| request.getServletPath().matches("/publico/.*")
						//|| request.getServletPath().matches("/publico/.*")
					) {

						//System.out.println("sesion valida 2");
						check = true;
					} else {
						//System.out.println("sesion valida 3");

						if(request.getRequestURI().equals("/sigeriop/login")){
							check = true;
							response.sendRedirect(request.getContextPath());
						}else {
							for (MenuProfile m : (ArrayList<MenuProfile>) session.getAttribute("menuFilter")) {
								if (request.getRequestURI().equals(request.getContextPath() + '/' + m.getUrl())
										|| (0 ==
										request.getRequestURI().indexOf(request.getContextPath() + '/' + m.getUrl()) &&
										m.getNroh() == 0)) {
									check = true;
								}
							}
						}

						/*
						Person data = (Person) session.getAttribute("users");
							for (Profile p : data.getUser().getProfiles()) {
								for (Modules m : p.getModules()) {
									if (request.getRequestURI().equals(request.getContextPath()+'/'+ m.getPath())
										|| (0==request.getRequestURI().indexOf(request.getContextPath()+'/'+ m.getPath()) && m.getOffspring().equals(false))) {
										check = true;
									}
							}
						}*/
					}

					if (check) {
						//System.out.println("sesion valida 4");
						chain.doFilter(request, response);
					} else {
						//System.out.println("sesion valida 5");
						//response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						response.sendRedirect(request.getContextPath()+"/error/403");
					}
				}
			}else{
				System.out.println("sesion invalida");
				//if (request.getRequestURI().matches(".*(menujson|locale|error|security/login|login|logout|css|jpg|png|gif|js|svg|eot|ttf|woff|woff2|map|ico)")
				if (request.getRequestURI().matches(".*(locale|error|login|login/sign_in|css|jpg|png|gif|js|svg|eot|ttf|woff|woff2|map|ico)")
						|| request.getContextPath().concat("/").equals(request.getRequestURI())
						|| request.getServletPath().matches("/publico/.*")
				) {
					chain.doFilter(request, response);
				}
				else if(request.getHeader("X-Requested-With")!=null){
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setHeader("Location", request.getContextPath());
				}else {
					response.sendRedirect(request.getContextPath());
				}
			}
		/*}
		catch(Exception excepcion){
			System.out.println("******************************* - getErrorPath "+request.getRequestURL());
			System.out.println("******************************* - getErrorPath "+request.getRequestURI());
			//excepcion.printStackTrace();
			System.out.println("xxxxxxxxx "+request.getContextPath());
			//response.sendRedirect(request.getContextPath());
			//response.sendRedirect(request.getContextPath()+"/error");
			throw excepcion;

		}*/

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}


}
