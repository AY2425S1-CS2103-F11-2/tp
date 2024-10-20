package tutorease.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CHICK;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_EMAIL_CHICK;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_EMAIL_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_NAME_CHICK;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_NAME_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_PHONE_CHICK;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_PHONE_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_MENTOR;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_SUPPORTIVE;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.TypicalGuardians.CHARLOTTE;
import static tutorease.address.testutil.TypicalGuardians.CHICK;
import static tutorease.address.testutil.TypicalGuardians.MEG;
import static tutorease.address.testutil.TypicalStudents.ALICE;
import static tutorease.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import tutorease.address.testutil.GuardianBuilder;

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
    public void getRole_returnsCorrectRole() {
        assertEquals(new Role(Role.GUARDIAN), MEG.getRole());
    }

    @Test
    public void addStudent_duplicateStudent_notAddedTwice() {
        // Test that the same student cannot be added twice
        CHARLOTTE.addStudent(ALICE);
        CHARLOTTE.addStudent(ALICE); // Attempt to add the same student again
        assertEquals(1, CHARLOTTE.getRelated().getPersons().size()); // Ensure only one instance exists
    }

    @Test
    public void addMultipleStudents_successfullyAddsAll() {
        // Test that multiple distinct students can be added
        MEG.addStudent(ALICE);
        MEG.addStudent(BOB);
        assertEquals(2, MEG.getRelated().getPersons().size());
        assertTrue(MEG.getRelated().containPerson(ALICE));
        assertTrue(MEG.getRelated().containPerson(ALICE));
    }



    @Test
    public void toStringMethod() {
        String expected = Guardian.class.getCanonicalName() + "{name=" + MEG.getName() + ", phone=" + MEG.getPhone()
                + ", email=" + MEG.getEmail() + ", address=" + MEG.getAddress() + ", tags=" + MEG.getTags()
                + ", role=" + MEG.getRole() + "}";
        assertEquals(expected, MEG.toString());
    }
}
