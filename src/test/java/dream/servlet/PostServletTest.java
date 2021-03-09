package dream.servlet;

import dream.model.Post;
import dream.store.MemStore;
import dream.store.PsqlStore;
import dream.store.Store;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest extends TestCase {

    @Test
    public void whenSavePost() throws ServletException, IOException {
        final Store memStore = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(memStore);
        HttpServletRequest req = PowerMockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = PowerMockito.mock(HttpServletResponse.class);
        Mockito.when(req.getParameter("id")).thenReturn("4");
        Mockito.when(req.getParameter("name")).thenReturn("Bob");
        new PostServlet().doPost(req, resp);
        final Post postById = memStore.findPostById(4);
        assertEquals(new Post(4, "Bob"), postById);
    }

    @Test
    public void whenUseDoGet() throws ServletException, IOException, InterruptedException {
        String path = "posts.jsp";
        final Store memStore = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(memStore);

        PostServlet ps = new PostServlet();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(path)).thenReturn(dispatcher);

        ps.doGet(req, resp);

        Mockito.verify(req).getRequestDispatcher(path);
    }
}