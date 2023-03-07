package Kodutööd.Kodutöö2;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Collection for holding Person objects.<br>
 * Provides the following guarantees:<br>
 * 1) Elements are guaranteed to be in ascending order, sorted by their ID code value.<br>
 * 2) Elements are guaranteed to have unique ID code values.<br>
 * <p>
 * Uses an underlying array for storing the elements. <br>
 * The object guarantees to not use more than twice the required array size.<br>
 * For example, if currently 10 persons are stored, then the underlying array size might range from 10 to 20, but will not be larger.
 */
public class SortedUniquePersonList {

    private Person[] people = new Person[0];

    /**
     * Returns reference to object at the given index. Checks that the given index is in bounds of the underlying array, returns null if it isn't.
     *
     * @param index Index at which the object is searched.
     * @return Person object at the given index, or null if the index is out of bounds.
     */
    public Person getElementAt(int index) {
        if (index < 0 || index >= this.people.length) return null; // Catch out of bounds index
        return this.people[index];
    }

    /**
     * Returns the index of the object with the given ID code. If an object with the given ID code is not present, returns -1.
     *
     * @param idCode ID code that is searched.
     * @return Index at which the Person object with the given ID code can be found, or -1 if no such ID code is present.
     */
    public int indexOf(int idCode) {
        return IntStream.range(0, people.length).filter(i -> people[i].getIdCode() == idCode).findFirst().orElse(-1);
    }

    /**
     * Attempts to add the person to the collection, but only if no person with the same ID code is already present.<br>
     * If an element is added, it is inserted to the correct position according to their ID code. Also, the index of all subsequent elements is then increased.<br>
     * If a Person object with the same ID code is already present, does nothing.
     *
     * @param person Person object to be added.
     * @return true if person was added to the collection, false otherwise.
     */
    public boolean add(Person person) {
        int[] idArray = Arrays.stream(this.people).mapToInt(Person::getIdCode).toArray();
        int personId = person.getIdCode();

        if (Arrays.stream(idArray).anyMatch(i -> i == personId)) return false; // If id is present stop the operation

        int finalLength = idArray.length + 1;
        Person[] finalArray = new Person[finalLength];

        // If currently empty or personId is bigger than any other, add person to the end and stop operation to avoid potential errors
        if (idArray.length == 0 || idArray[idArray.length - 1] < personId) {
            System.arraycopy(this.people, 0, finalArray, 0, this.people.length);
            finalArray[finalLength - 1] = person;
            this.people = finalArray;
            return true;
        }

        // Find insert position
        int indexOfPerson = -Arrays.binarySearch(idArray, personId) - 1;

        System.arraycopy(this.people, 0, // Copy all before indexOfPerson
                finalArray, 0,
                indexOfPerson);
        finalArray[indexOfPerson] = person; // Insert person
        System.arraycopy(this.people, indexOfPerson,  // Copy all after indexOfPerson (shifting)
                finalArray, indexOfPerson + 1,
                this.people.length - indexOfPerson);
        this.people = finalArray;
        return true;
    }

    /**
     * Attempts to remove the person with the given ID code from the collection. Does nothing if no Person object with the given ID code is present.<br>
     * In the case of a successful removal of an object, decreases the index of all subsequent elements.
     *
     * @param idCode ID code that is searched.
     * @return true if the person with the given ID code was removed, false otherwise.
     */
    public boolean removeElement(int idCode) {
        int[] idArray = Arrays.stream(this.people).mapToInt(Person::getIdCode).toArray();


        // Stop the function in case of no match
        if (Arrays.stream(idArray).noneMatch(i -> i == idCode)) return false;

        int indexOfPerson = Arrays.binarySearch(idArray, idCode);

        Person[] finalArray = new Person[this.people.length - 1];

        // Dual copy up to skip indexOfPerson
        System.arraycopy(this.people, 0, // Copy all before indexOfPerson
                finalArray, 0,
                indexOfPerson);
        System.arraycopy(this.people, indexOfPerson + 1, // Copy all after indexOfPerson
                finalArray, indexOfPerson,
                this.people.length - indexOfPerson - 1);

        this.people = finalArray;
        return true;
    }

    /**
     * Calculates and returns the size of the collection.
     *
     * @return Number of elements in the collection.
     */
    public int size() {
        return this.people.length;
    }
}
