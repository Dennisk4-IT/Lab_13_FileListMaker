import java.util.Scanner;

public class safeInput {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String name = getNonZeroLenString(in, "Enter your name");
        System.out.println("You have entered: " + name);
    }

    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString;
        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        } while (retString.isEmpty());
        return retString;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int value;
        do {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: ");
            while (!pipe.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                pipe.nextLine();
            }
            value = pipe.nextInt();
            pipe.nextLine(); // clear buffer
        } while (value < low || value > high);
        return value;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String response;
        while (true) {
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = pipe.nextLine().trim().toUpperCase();
            if (response.equals("Y")) {
                return true;
            } else if (response.equals("N")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String input;
        while (true) {
            System.out.print("\n" + prompt + ": ");
            input = pipe.nextLine();
            if (input.matches(regEx)) {
                return input;
            } else {
                System.out.println("Invalid input. Please use the correct format.");
            }
        }
    }
}




