package dream;

import dream.model.Candidate;
import dream.store.Store;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mockito;

public class SimpleConsoleTest extends TestCase {

    @Test
    public void testViewCandidate() {
        Store store = Mockito.mock(Store.class);
        ConsoleHelper ch = new SimpleConsole(store);
        Mockito.when(store.findCandidateById(1)).thenReturn(new Candidate(1, "Тест мокито"));
        assertEquals(ch.viewCandidate(1), "Id 1. Name Тест мокито");
    }
}