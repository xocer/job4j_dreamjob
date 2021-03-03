package dream.servlet;

import dream.model.User;
import dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var name = req.getParameter("name");
        final var email = req.getParameter("email");
        final var password = req.getParameter("password");
        final var user = new User(0, name, email, password);
        PsqlStore.instOf().addUser(user);
        req.getRequestDispatcher("/auth.do").forward(req, resp);
    }
}
