package cst8218.joshua.bouncer.business;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter for handling Cross-Origin Resource Sharing (CORS) policies.
 */
@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class CORSFilter implements Filter {

    /**
     * Applies CORS policies to the HTTP response.
     *
     * @param req The servlet request.
     * @param res The servlet response.
     * @param chain The filter chain.
     * @throws IOException If an I/O error occurs during the filtering process.
     * @throws ServletException If the request could not be handled.
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(req, res);
    }

    /**
     * Initializes the filter configuration.
     *
     * @param filterConfig The filter configuration.
     * @throws ServletException If an error occurs during initialization.
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Called when the filter is taken out of service.
     */
    public void destroy() {
    }
}
