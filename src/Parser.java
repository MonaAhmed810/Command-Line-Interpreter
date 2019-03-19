import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Vector;

public class Parser {

    public Vector<String> args = new Vector<String>();
    public String cmd;
    private Terminal terminal;

    public Parser() {
        terminal = new Terminal();
    }

    public void printCurrentDirectory() {
        System.out.print(terminal.pwd() + "> ");
    }

    public Boolean parse(String command) {
        String[] words = command.split("\"");
        Vector<String> parts = new Vector<>();
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
            if (words[i].length() > 0) {
                parts.add(words[i]);
            }
        }
        if (parts.size() == 0) {
            return false;
        }
        File file;
        switch (parts.get(0)) {
            case "cd":
                if (parts.size() > 2) {
                    System.out.println("Invalid parameters, cd takes 0 or 1 parameters, Write help for more details");
                    return false;
                }
                if (parts.size() == 2) {
                    if (parts.get(1).equals("..")) {
                        String path = terminal.pwd();
                        for (int i = path.length() - 1; i >= 0; i--) {
                            if (path.charAt(i) == '\\') {
                                path = path.substring(0, i);
                                break;
                            }
                        }
                        terminal.cd(path);
                        break;
                    }
                    file = new File(parts.get(1));
                    if (file.isDirectory()) {
                        terminal.cd(parts.get(1));
                        break;
                    }
                    file = new File(terminal.pwd() + '\\' + parts.get(1));
                    if (file.isDirectory()) {
                        terminal.cd(terminal.pwd() + '\\' + parts.get(1));
                        break;
                    }
                    System.out.println("Invalid Path");
                    return false;
                }
                terminal.cd();
                break;
            case "cp":
                if (parts.size() != 3) {
                    System.out.println("Invalid parameters, cp takes 2 parameters, Write help for more details");
                    return false;
                }
                terminal.cp(parts.get(1), parts.get(2));
                cmd = "cp";
                args.add(parts.get(1));
                args.add(parts.get(2));
                break;
            case "mv":
                if (parts.size() != 3) {
                    System.out.println("Invalid parameters, mv takes 2 parameters, Write help for more details");
                    return false;
                }
                terminal.mv(parts.get(1), parts.get(2));
                cmd = "mv";
                args.add(parts.get(1));
                args.add(parts.get(2));
                break;
            case "rm":
                if (parts.size() != 2) {
                    System.out.println("Invalid parameters, rm takes 1 parameter, Write help for more details");
                    return false;
                }
                if (!terminal.rm(parts.get(1))) {
                    System.out.println("Error, File may be not exist");
                    break;
                }
                cmd = "rm";
                args.add(parts.get(1));
                break;
            case "mkdir":
                if (parts.size() != 2) {
                    System.out.println("Invalid parameters, mkdir takes 1 parameter, Write help for more details");
                    return false;
                }
                if (!terminal.mkdir(parts.get(1))) {
                    System.out.println("Error");
                    break;
                }
                cmd = "mkdir";
                args.add(parts.get(1));
                break;
            case "rmdir":
                if (parts.size() != 2) {
                    System.out.println("Invalid parameters, rmdir takes 1 parameter, Write help for more details");
                    return false;
                }
                if (!terminal.rmdir(parts.get(1))) {
                    System.out.println("Error");
                    break;
                }
                cmd = "rmdir";
                args.add(parts.get(1));
                break;
            case "pwd":
                if (parts.size() == 3 && (parts.get(1).equals(">") || parts.get(1).equals(">>"))) {
                    try {
                        boolean flag = false;
                        if (parts.get(1).equals(">>")) flag = true;
                        PrintWriter out = new PrintWriter(new FileOutputStream(new File(parts.get(2)), flag));
                        out.println(terminal.pwd());
                        out.close();
                        System.out.println("Done");
                        cmd = "pwd";
                        args.add(parts.get(2));
                    } catch (Exception e) {
                        System.out.println("Error " + e);
                        return false;
                    }
                    break;
                }
                if (parts.size() != 1) {
                    System.out.println("Invalid parameters, pwd takes no parameters, Write help for more details");
                    return false;
                }
                System.out.println(terminal.pwd());
                cmd = "pwd";
                break;
            case "clear":
                if (parts.size() != 1) {
                    System.out.println("Invalid parameters, clear takes no parameters, Write help for more details");
                    return false;
                }
                terminal.clear();
                cmd = "clear";
                break;
            case "ls":
                if (parts.size() == 3 && (parts.get(1).equals(">") || parts.get(1).equals(">>"))) {
                    boolean flag = false;
                    if (parts.get(1).equals(">>")) flag = true;
                    terminal.ls(parts.get(2), flag);
                    System.out.println("Done");
                    cmd = "ls";
                    args.add(parts.get(2));
                    break;
                }
                if (parts.size() != 1) {
                    System.out.println("Invalid parameters, ls takes no parameters, Write help for more details");
                    return false;
                }
                terminal.ls();
                break;
            case "help":
                if (parts.size() == 3 && (parts.get(1).equals(">") || parts.get(1).equals(">>"))) {
                    boolean flag = false;
                    if (parts.get(1).equals(">>")) flag = true;
                    terminal.help(parts.get(2), flag);
                    System.out.println("Done");
                    cmd = "help";
                    args.add(parts.get(2));
                    break;
                }
                if (parts.size() != 1) {
                    System.out.println("Invalid parameters, help takes no parameters");
                    return false;
                }
                terminal.help();
                cmd = "help";
                break;
            case "date":
                if (parts.size() == 3 && (parts.get(1).equals(">") || parts.get(1).equals(">>"))) {
                    boolean flag = false;
                    if (parts.get(1).equals(">>")) flag = true;
                    terminal.date(parts.get(2), flag);
                    System.out.println("Done");
                    cmd = "date";
                    args.add(parts.get(2));
                    break;
                }
                if (parts.size() != 1) {
                    System.out.println("Invalid parameters, help takes no parameters, Write help for more details");
                    return false;
                }
                terminal.date();
                break;
            case "args":
                if (parts.size() == 4 && (parts.get(2).equals(">") || parts.get(2).equals(">>"))) {
                    boolean flag = false;
                    if (parts.get(2).equals(">>")) flag = true;
                    terminal.args(parts.get(1), parts.get(3), flag);
                    System.out.println("Done");
                    cmd = "args";
                    args.add(parts.get(1));
                    args.add(parts.get(3));
                    break;
                }
                if (parts.size() != 2) {
                    System.out.println("Invalid parameters, args takes 1 parameter, Write help for more details");
                    return false;
                }
                terminal.args(parts.get(1));
                cmd = "args";
                args.add(parts.get(1));
                break;
            case "more":
                if (parts.size() != 2) {
                    System.out.println("Invalid parameters, more takes 1 parameter, Write help for more details");
                    return false;
                }
                terminal.more(parts.get(1));
                cmd = "more";
                args.add(parts.get(1));
                break;
            case "cat":
                if (parts.size() < 2) {
                    System.out.println("Invalid parameters, cat takes at least 1 parameter, Write help for more details");
                    return false;
                }
                if (parts.size() == 5 && (parts.get(3).equals(">") || parts.get(3).equals(">>"))) {
                    boolean flag = false;
                    if (parts.get(3).equals(">>")) flag = true;
                    terminal.cat(parts.get(1), parts.get(2), parts.get(4), flag);
                    System.out.println("Done");
                    break;
                }
                for (int i = 1; i < parts.size(); i++) {
                    terminal.cat(parts.get(i));
                    System.out.println();
                }
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid cmd");
        }
        return true;
    }
}