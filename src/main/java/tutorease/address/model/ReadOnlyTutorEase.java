package tutorease.address.model;

import javafx.collections.ObservableList;
import tutorease.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTutorEase {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns a person with the given name.
     */
    Person getPerson(String name);
}
