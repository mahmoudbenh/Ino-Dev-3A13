package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javafx.application.Application;
import javafx.stage.Stage;

public class BadWords {
    public static class BadWordsException extends Exception {
        public BadWordsException(String message) {
            super(message);
        }
    }

    public boolean wordExistsInFile(String word, String filePath) throws BadWordsException {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            Set<String> wordsInFile = new HashSet<>();
            while (scanner.hasNext()) {
                wordsInFile.add(scanner.nextLine().trim().toLowerCase());

            }


            if (wordsInFile.contains(word)) {
                System.out.println("Mot interdit trouvé dans la description : " + word);
                throw new BadWordsException("Mot interdit trouvé dans la description !");
            }
            return false;
        } catch (FileNotFoundException e) {
            throw new BadWordsException("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
    public static void main(String[] args) {

    }


}
