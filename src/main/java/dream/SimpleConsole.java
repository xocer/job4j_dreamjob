package dream;

import dream.store.Store;

public class SimpleConsole implements ConsoleHelper{
    Store store;

    public SimpleConsole(Store store) {
        this.store = store;
    }

    @Override
    public String viewCandidate(int id) {
        final var candidate = store.findCandidateById(id);
        String s = "Id " + candidate.getId() + ". Name " + candidate.getName();
        System.out.println(s);
        return s;
    }
}
