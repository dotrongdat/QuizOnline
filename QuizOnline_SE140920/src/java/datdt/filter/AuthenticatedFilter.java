/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.filter;

import datdt.account.AccountDTO;
import datdt.subject.SubjectDAO;
import datdt.util.MapObject;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Win
 */
public class AuthenticatedFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public AuthenticatedFilter() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticatedFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticatedFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
       ServletContext context = request.getServletContext();
        HashMap<String, String> nonAuthenMap = (HashMap<String, String>) context.getAttribute("NONAUTHENMAP");       
        HashMap<String, String> authenMap = (HashMap<String, String>) context.getAttribute("AUTHENMAP");
        HashMap<String, String> authorizeMap = (HashMap<String, String>) context.getAttribute("AUTHORIZEMAP");
        HashMap<String, String> disallowDirectAccess = (HashMap<String, String>) context.getAttribute("DISALLOWDIRECTACCESS");

        HttpServletRequest req = (HttpServletRequest) request;

        String uri = req.getRequestURI();
        int index = uri.lastIndexOf("/");
        String resource = uri.substring(index + 1);
        HttpSession session = req.getSession();
        AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");

        String url = null;
        boolean checkAuthorize = false;
        boolean checkAccessResource = true;
        if (!resource.contains(".css") && !resource.contains(".js")) {
            if (disallowDirectAccess.containsKey(resource)) {
                url = disallowDirectAccess.get(resource);
                checkAccessResource = false;
            } else {
                if(nonAuthenMap.containsKey(resource)&&authenMap.containsKey(resource)&&authorizeMap.containsKey(resource)){
                    if(account==null){
                        url=nonAuthenMap.get(resource);
                    }else{
                        checkAuthorize = true;
                        chain.doFilter(request, response);
                    }
                }
                else if (nonAuthenMap.containsKey(resource)) {
                    if (account == null) {

                        url = nonAuthenMap.get(resource);

                    } else {
                        url = "HomePage";
                        checkAccessResource = false;
                    }
                } else if (authenMap.containsKey(resource) || authorizeMap.containsKey(resource)) {
                    if (account != null) {
                        checkAuthorize = true;
                        chain.doFilter(request, response);
                    } else {
                        url = "HomePage";
                        checkAccessResource = false;
                    }
                } else {
                    url = "ErrorPage?msg=Resouce Not Found";
                    checkAccessResource = false;
                }
            }

        } else {
            url = resource;
        }
        if (!checkAuthorize && checkAccessResource) {
            req.getRequestDispatcher(url).forward(request, response);
        } else if (!checkAccessResource) {
            ((HttpServletResponse) response).sendRedirect(url);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthenticateFilter:Initializing filter");
            }
            ServletContext context = filterConfig.getServletContext();

            String nonAuthenFilePath = context.getInitParameter("nonAuthenticate");
            String authenFilePath = context.getInitParameter("authenticate");
            String authorizeFilePath = context.getInitParameter("authorize");
            String disallowDirectAccess = context.getInitParameter("disallowDirectAccess");

            String nonAuthenRealPath = context.getRealPath(nonAuthenFilePath);
            String authenRealPath = context.getRealPath(authenFilePath);
            String authorizeRealPath = context.getRealPath(authorizeFilePath);
            String disallowDirectAccessRealPath = context.getRealPath(disallowDirectAccess);

            MapObject nonAuthenMap = new MapObject();
            MapObject authenMap = new MapObject();
            MapObject authorizeMap = new MapObject();
            MapObject disallowDirectAccessMap = new MapObject();
            try {
                nonAuthenMap.getMap(nonAuthenRealPath);
                context.setAttribute("NONAUTHENMAP", nonAuthenMap);
                authenMap.getMap(authenRealPath);
                context.setAttribute("AUTHENMAP", authenMap);
                authorizeMap.getMap(authorizeRealPath);
                context.setAttribute("AUTHORIZEMAP", authorizeMap);
                disallowDirectAccessMap.getMap(disallowDirectAccessRealPath);
                context.setAttribute("DISALLOWDIRECTACCESS", disallowDirectAccessMap);
            } catch (IOException ex) {
                Logger.getLogger(AuthenticatedFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            SubjectDAO subjectDAO=new SubjectDAO();
        try {
            subjectDAO.getAllSubjects();
            context.setAttribute("SUBJECTS", subjectDAO.getSubList());
        } catch (NamingException ex) {
            log("ERROR at AuthenticatedFilter_Naming: "+ex.getMessage());
        } catch (SQLException ex) {
            log("ERROR at AuthenticatedFilter_SQL: "+ex.getMessage());
        }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticatedFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticatedFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
