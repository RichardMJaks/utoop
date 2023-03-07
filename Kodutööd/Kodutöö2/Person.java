package Kodutööd.Kodutöö2;

public class Person {
    private final int idCode;
    private final String firstName;
    private final String lastName;

    public Person(int idCode, String firstName, String lastName) {
        this.idCode = idCode;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getIdCode() {
        return idCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return idCode + "-" + firstName + "-" + lastName;
    }
}


