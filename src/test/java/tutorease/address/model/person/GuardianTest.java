package tutorease.address.model.person;

import org.junit.jupiter.api.Test;
import tutorease.address.testutil.GuardianBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static tutorease.address.logic.commands.CommandTestUtil.*;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.TypicalGuardians.MEG;
import static tutorease.address.testutil.TypicalGuardians.CHICK;

public class GuardianTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Guardian guardian = new GuardianBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> guardian.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(MEG.isSamePerson(MEG));

        // null -> returns false
        assertFalse(MEG.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Guardian editedMeg = new GuardianBuilder(MEG).withPhone(VALID_PHONE_MEG).withEmail(VALID_EMAIL_MEG)
                .withAddress(VALID_ADDRESS_MEG).withTags(VALID_TAG_SUPPORTIVE).build();
        assertTrue(MEG.isSamePerson(editedMeg));

        // different name, all other attributes same -> returns false
        editedMeg = new GuardianBuilder(MEG).withName(VALID_NAME_CHICK).build();
        assertFalse(MEG.isSamePerson(editedMeg));

        // name differs in case, all other attributes same -> returns false
        Guardian editedChick = new GuardianBuilder(CHICK).withName(VALID_NAME_MEG.toLowerCase()).build();
        assertFalse(CHICK.isSamePerson(editedChick));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_MEG + " ";
        editedChick = new GuardianBuilder(CHICK).withName(nameWithTrailingSpaces).build();
        assertFalse(CHICK.isSamePerson(editedChick));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Guardian megCopy = new GuardianBuilder(MEG).build();
        assertTrue(MEG.equals(megCopy));

        // same object -> returns true
        assertTrue(MEG.equals(MEG));

        // null -> returns false
        assertFalse(MEG.equals(null));

        // different type -> returns false
        assertFalse(MEG.equals(5));

        // different person -> returns false
        assertFalse(MEG.equals(CHICK));

        // different name -> returns false
        Guardian editedMeg = new GuardianBuilder(MEG).withName(VALID_NAME_CHICK).build();
        assertFalse(MEG.equals(editedMeg));

        // different phone -> returns false
        editedMeg = new GuardianBuilder(MEG).withPhone(VALID_PHONE_CHICK).build();
        assertFalse(MEG.equals(editedMeg));

        // different email -> returns false
        editedMeg = new GuardianBuilder(MEG).withEmail(VALID_EMAIL_CHICK).build();
        assertFalse(MEG.equals(editedMeg));

        // different address -> returns false
        editedMeg = new GuardianBuilder(MEG).withAddress(VALID_ADDRESS_CHICK).build();
        assertFalse(MEG.equals(editedMeg));

        // different tags -> returns false
        editedMeg = new GuardianBuilder(MEG).withTags(VALID_TAG_MENTOR).build();
        assertFalse(MEG.equals(editedMeg));
    }

    @Test
    public void toStringMethod() {
        String expected = Guardian.class.getCanonicalName() + "{name=" + MEG.getName() + ", phone=" + MEG.getPhone()
                + ", email=" + MEG.getEmail() + ", address=" + MEG.getAddress() + ", tags=" + MEG.getTags()
                + ", role=" + MEG.getRole() + "}";
        assertEquals(expected, MEG.toString());
    }
}
