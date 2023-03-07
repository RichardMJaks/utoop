package Kodutööd.Kodutöö2;

public class Test {
    public static void main(String[] args) throws Exception {
        Person p1 = new Person(1, "first1", "last1");
        Person p2 = new Person(2, "first2", "last2");
        Person p3 = new Person(3, "first3", "last3");
        Person pX = new Person(Integer.MAX_VALUE, "firstX", "lastX");

        SortedUniquePersonList supl = new SortedUniquePersonList();
        SortedUniquePersonList supl2 = new SortedUniquePersonList();
        assertThat("new list should have size 0", supl.size() == 0);
        assertThat("new list should have size 0", supl2.size() == 0);
        supl.add(pX);
        supl.add(p3);
        supl.add(p1);
        supl.add(p2);
        assertThat("size must be correct after add (supl)", supl.size() == 4);
        assertThat("size must be correct after add (supl2)", supl2.size() == 0);
        assertThat("elements must be sorted (p1)", supl.getElementAt(0) == p1);
        assertThat("elements must be sorted (p2)", supl.getElementAt(1) == p2);
        assertThat("elements must be sorted (p3)", supl.getElementAt(2) == p3);
        assertThat("elements must be sorted (pX)", supl.getElementAt(3) == pX);
        assertThat("getElementAt must be null (1000)", supl.getElementAt(1000) == null);
        assertThat("getElementAt must be null (-1000)", supl.getElementAt(-1000) == null);
        assertThat("indexOf must be -1 (1000)", supl.indexOf(1000) == -1);
        assertThat("indexOf must be -1 (-1000)", supl.indexOf(-1000) == -1);
        assertThat("indexOf must be correct (p1)", supl.indexOf(1) == 0);
        assertThat("indexOf must be correct (pX)", supl.indexOf(Integer.MAX_VALUE) == 3);
        supl.removeElement(Integer.MAX_VALUE);
        supl.removeElement(1);
        assertThat("size must be correct after remove", supl.size() == 2);
        assertThat("elements must be sorted (p2)", supl.getElementAt(0) == p2);
        assertThat("elements must be sorted (p3)", supl.getElementAt(1) == p3);
        assertThat("indexOf must be correct (p2)", supl.indexOf(2) == 0);
        assertThat("indexOf must be correct (p3)", supl.indexOf(3) == 1);
        supl.removeElement(2);
        supl.removeElement(3);
        assertThat("size must be correct after remove all", supl.size() == 0);
        supl.add(p1);
        assertThat("size must be correct after re-add", supl.size() == 1);

        System.out.println("tests passed");
    }

    static void assertThat(String description, boolean isCorrect) throws Exception {
        if (!isCorrect)
            throw new Exception(description);
    }
}
