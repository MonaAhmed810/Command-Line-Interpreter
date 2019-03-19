import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        while (true) {
            parser.printCurrentDirectory();
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            int idx = command.indexOf("|");
            while (idx != -1) {
                String sub = command.substring(0, idx);
                command = command.substring(idx + 1);
                parser.parse(sub);
                idx = command.indexOf("|");
                System.out.println();
            }
            parser.parse(command);
        }

    }
}
