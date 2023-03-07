package Praktikumid.Praktikum2;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FailiTest {
    public static void main(String[] args) throws Exception {
        Path path = Path.of(args[0]);

        // If not a directory throw an exception
        if (!Files.isDirectory(path)) {
            throw new Exception("Path is not a directory.");
        }

        FailiVaatleja vaatleja = new FailiVaatleja();
        NimeVõrdleja võrdleja = new NimeVõrdleja();

        Files.walkFileTree(path, vaatleja);

        List<String> failiNimed = vaatleja.getFailiNimed();
        Collections.sort(failiNimed, võrdleja);
    }
}

class FailiVaatleja implements FileVisitor {
    private List<String> failiNimed = new ArrayList<String>();

    public List<String> getFailiNimed() {
        return failiNimed;
    }
    public FileVisitResult preVisitDirectory(Object o, BasicFileAttributes basicFileAttributes) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFile(Object o, BasicFileAttributes basicFileAttributes) throws IOException {
        Path path = (Path) o;

        this.failiNimed.add(path.getFileName().toString());

        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFileFailed(Object o, IOException e) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult postVisitDirectory(Object o, IOException e) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}

class NimeVõrdleja implements Comparator<String> {
    @Override
    public int compare(String s, String t1) {
        return t1.compareTo(s); // Reverse comparison result
    }
}
