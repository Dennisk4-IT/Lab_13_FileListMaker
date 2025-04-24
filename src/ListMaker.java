import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ListMaker {
    static boolean needsSaving = false;
    static String currentFileName = null;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        boolean done = false;

        while (!done) {
            printMenu();
            printList(list);
            String choice = safeInput.getRegExString(in, "Enter a choice [A/D/I/M/V/O/S/C/Q]", "[AaDdIiMmVvOoSsCcQq]").toUpperCase();

            switch (choice) {
                case "A":
                    String newItem = safeInput.getNonZeroLenString(in, "Please enter item to add to the list");
                    list.add(newItem);
                    needsSaving = true;
                    break;
                case "D":
                    if (list.isEmpty()) {
                        System.out.println("The list is empty. There is nothing to delete.");
                        break;
                    }
                    int delIndex = safeInput.getRangedInt(in, "Enter the item number to delete", 1, list.size()) - 1;
                    list.remove(delIndex);
                    needsSaving = true;
                    break;
                case "I":
                    if (list.isEmpty()) {
                        System.out.println("The list is empty. Please add Add first.");
                        break;
                    }
                    int insIndex = safeInput.getRangedInt(in, "Please enter position to insert at", 1, list.size() + 1) - 1;
                    String insItem = safeInput.getNonZeroLenString(in, "Please enter item to insert");
                    list.add(insIndex, insItem);
                    needsSaving = true;
                    break;
                case "M":
                    if (list.size() < 2) {
                        System.out.println("You will need at least 2 items to move.");
                        break;
                    }
                    int from = safeInput.getRangedInt(in, "Please enter item number to move", 1, list.size()) - 1;
                    int to = safeInput.getRangedInt(in, "Please enter new position", 1, list.size()) - 1;
                    String item = list.remove(from);
                    list.add(to, item);
                    needsSaving = true;
                    break;
                case "V":
                    printList(list);
                    break;
                case "O":
                    if (needsSaving) promptToSave(in, list);
                    currentFileName = safeInput.getNonZeroLenString(in, "Please enter filename to load (no extension)") + ".txt";
                    try {
                        list = loadFile(currentFileName);
                        needsSaving = false;
                    } catch (IOException e) {
                        System.out.println("It has failed to load file: " + e.getMessage());
                    }
                    break;
                case "S":
                    if (currentFileName == null) {
                        currentFileName = safeInput.getNonZeroLenString(in, "Please enter filename to save as (no extension)") + ".txt";
                    }
                    try {
                        saveFile(currentFileName, list);
                        needsSaving = false;
                    } catch (IOException e) {
                        System.out.println("You have failed to save file: " + e.getMessage());
                    }
                    break;
                case "C":
                    boolean confirmClear = safeInput.getYNConfirm(in, "Would you like to confirm you would like to clear the list?");
                    if (confirmClear) {
                        list.clear();
                        needsSaving = true;
                    }
                    break;
                case "Q":
                    if (needsSaving) promptToSave(in, list);
                    done = true;
                    break;
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\nMenu Options:");
        System.out.println("A – Add");
        System.out.println("D – Delete");
        System.out.println("I – Insert");
        System.out.println("M – Move");
        System.out.println("V – View List");
        System.out.println("O – Open List File");
        System.out.println("S – Save List File");
        System.out.println("C – Clear List");
        System.out.println("Q – Quit");
    }

    private static void printList(ArrayList<String> list) {
        System.out.println("\nCurrent List:");
        if (list.isEmpty()) {
            System.out.println("[List is empty]");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, list.get(i));
            }
        }
    }

    private static void saveFile(String fileName, ArrayList<String> list) throws IOException {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(fileName)))) {
            for (String item : list) {
                writer.println(item);
            }
        }
    }

    private static ArrayList<String> loadFile(String fileName) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        result.addAll(lines);
        return result;
    }

    private static void promptToSave(Scanner in, ArrayList<String> list) {
        boolean save = safeInput.getYNConfirm(in, "You have unsaved changes. Would you like to save now?");
        if (save) {
            if (currentFileName == null) {
                currentFileName = safeInput.getNonZeroLenString(in, ":Please enter filename to save as (no extension)") + ".txt";
            }
            try {
                saveFile(currentFileName, list);
                needsSaving = false;
            } catch (IOException e) {
                System.out.println("You failed to save file: " + e.getMessage());
            }
        }
    }
}