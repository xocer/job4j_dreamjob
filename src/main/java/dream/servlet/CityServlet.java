package dream.servlet;

import com.alibaba.fastjson.JSON;
import dream.model.City;
import dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<City> cities = PsqlStore.instOf().findAllCity();
        String json = JSON.toJSONString(cities);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
