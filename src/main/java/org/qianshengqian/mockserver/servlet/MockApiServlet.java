package org.qianshengqian.mockserver.servlet;

import org.qianshengqian.common.method.RequestHandlerMappingAdapter;
import org.qianshengqian.common.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/8/21.
 */
public class MockApiServlet extends HttpServlet {

    RequestHandlerMappingAdapter requestHandlerMappingAdapter = SpringContextHolder.getApplicationContext().getBean(RequestHandlerMappingAdapter.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String responseData = requestHandlerMappingAdapter.requestAdapter(req);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return ;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
