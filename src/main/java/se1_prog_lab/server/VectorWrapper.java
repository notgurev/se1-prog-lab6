package se1_prog_lab.server;

import com.google.inject.Singleton;
import se1_prog_lab.collection.LabWork;
import se1_prog_lab.exceptions.NoElementWithSuchIdException;
import se1_prog_lab.server.interfaces.CollectionWrapper;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static se1_prog_lab.util.BetterStrings.coloredYellow;
import static se1_prog_lab.util.BetterStrings.multiline;

@Singleton
public class VectorWrapper implements CollectionWrapper {
    private Vector<LabWork> labWorks = new Vector<>();
    private LocalDate initDate;
    private HashSet<Long> idSet = new HashSet<>();

    private Comparator<LabWork> labWorkComparator = Comparator.naturalOrder();

    public VectorWrapper() {
        initDate = LocalDate.now();
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public int getSize() {
        return labWorks.size();
    }

    public String getCollectionType() {
        return labWorks.getClass().getSimpleName();
    }

    public void clear() {
        labWorks.clear();
    }

    private void assignId(LabWork labWork) {
        if (labWork.getId() == null || idSet.contains(labWork.getId())) {
            for (long i = 0L; i <= idSet.size(); i++) {
                if (idSet.add(i)) {
                    labWork.safeSetId(i);
                    break;
                }
            }
        } else {
            idSet.add(labWork.getId());
        }
    }

    public long add(LabWork labWork) {
        assignId(labWork);
        labWorks.add(labWork);
        return labWork.getId();
    }

    public long addToPosition(LabWork labWork, int index) {
        assignId(labWork);
        labWorks.setSize(index);
        labWorks.add(index, labWork);
        return labWork.getId();
    }

    public String filterGreaterThanMinimalPoint(Integer minimalPoint) {
        return multiline(labWorks.stream()
                .filter(labWork -> (labWork.getMinimalPoint() != null && labWork.getMinimalPoint() > minimalPoint))
                .map(LabWork::toString).toArray());
    }

    public Set<Integer> getUniqueTunedInWorks() {
        return labWorks.stream().map(LabWork::getTunedInWorks).collect(Collectors.toSet());
    }

    public boolean sort() {
        if (labWorks.isEmpty()) return false;
        labWorks.sort(labWorkComparator);
        return true;
    }

    public void removeByID(long id) throws NoElementWithSuchIdException {
        if (getByID(id) != null) {
            labWorks.remove(getByID(id));
        } else throw new NoElementWithSuchIdException();
    }

    public boolean replaceByID(long id, LabWork newLabWork) {
        if (getByID(id) != null) {
            LabWork oldLabWork = getByID(id);
            newLabWork.safeSetId(id);
            labWorks.set(labWorks.indexOf(oldLabWork), newLabWork);
            return true;
        } else return false;
    }

    public long countLessThanDescription(String description) {
        return labWorks.stream().filter(labWork -> labWork.getDescription().compareTo(description) < 0).count();
    }

    public LabWork getByID(Long id) {
        return labWorks.stream().filter(labWork -> labWork.getId().equals(id)).findAny().orElse(null);
    }

    public String showAll() {
        if (labWorks.isEmpty()) {
            return coloredYellow("Коллекция пуста!");
        } else {
            return multiline(labWorks.stream().filter(Objects::nonNull).map(LabWork::toString).toArray());
        }
    }

    public boolean isEmpty() {
        return labWorks.isEmpty();
    }

    public String[] toArray() {
        ArrayList<String> array = new ArrayList<>();
        labWorks.forEach(labWork -> array.add(labWork.toCSVline()));

        String[] stringArray = new String[array.size()];
        stringArray = array.toArray(stringArray);

        return stringArray;
    }
}
