import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Scanner;

public class Terminal {

    private static File curFile, defaultDic;

    Terminal() {
        curFile = new File("E:\\FCI\\3rd year\\OS");
        defaultDic = new File("E:\\FCI");
    }

    public void clear() {
        for (int i = 0; i < 20; i++)
            System.out.println();
    }

    public void cd() {
        curFile = defaultDic;
    }

    public void cd(String path) {
        File newDir = new File(path);
        curFile = newDir;
    }

    public void ls() {
        String arr[] = curFile.list();
        for (String str : arr)
            System.out.println(str);
    }

    public void ls(String filePath, boolean appendMode) {
        String arr[] = curFile.list();
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(new File(filePath), appendMode));
            for (String str : arr)
                out.println(str);
            out.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    public void cp(String sourcePath, String destinationPath) {
        try {
            File source = new File(sourcePath);
            File destination = new File(destinationPath);
            if (source.isFile())
                Files.copy(source.toPath(), destination.toPath());
            else {
                System.out.println(source.toPath());
                System.out.println("cp takes files as arguments not directories");
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    public void mv(String sourcePath, String destinationPath) {
        try {
            File source = new File(sourcePath);
            File destination = new File(destinationPath);
            Files.move(source.toPath(), destination.toPath());
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    public boolean rm(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            String arr[] = file.list();
            for (String str : arr) {
                File tempFile = new File(filePath + '\\' + str);
                if (tempFile.isFile())
                    tempFile.delete();
            }
            return true;
        }
        return file.delete();
    }

    public boolean rmdir(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            return file.delete();
        }
        return false;
    }

    public boolean mkdir(String path) {
        File file = new File(path);
        return file.mkdir();
    }

    public void cat(String filePath) {
        try {
            File file = new File(filePath);
            Scanner in = new Scanner(file);
            while (in.hasNextLine())
                System.out.println(in.nextLine());
            in.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    public void cat(String file1Path, String file2Path, String file3Path, boolean appendMode) {
        try {
            File file1 = new File(file1Path), file2 = new File(file2Path), file3 = new File(file3Path);
            PrintWriter out = new PrintWriter(new FileOutputStream(file3, appendMode));
            Scanner in = new Scanner(file1);
            while (in.hasNextLine())
                out.println(in.nextLine());
            in = new Scanner(file2);
            while (in.hasNextLine())
                out.println(in.nextLine());
            in.close();
            out.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    public void more(String filePath) {
        try {
            File file = new File(filePath);
            int x = 5;
            Scanner in = new Scanner(file), userInput = new Scanner(System.in);
            while (x > 0 && in.hasNextLine()) {
                System.out.println(in.nextLine());
                x--;
            }
            char reply;
            while (in.hasNextLine()) {
                System.out.print("for more Y/N ?");
                reply = userInput.next().charAt(0);
                if (reply == 'N')
                    break;
                System.out.println(in.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    public String pwd() {
        return curFile.getPath();
    }

    public void date() {
        System.out.println(java.time.LocalDate.now() + " " + java.time.LocalTime.now());
    }

    public void date(String filePath, boolean appendMode) {
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(new File(filePath), appendMode));
            out.println(java.time.LocalDate.now() + " " + java.time.LocalTime.now());
            out.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    public void args(String command) {

        if (command.equals("cd")) {
            System.out.println("There are 2 options :");
            System.out.println("- No parameters, to change current directory to the default directory");
            System.out.println("- Number of args is 1 : New directoryPath to change to");
        } else if (command.equals("ls"))
            System.out.println("No parameters");
        else if (command.equals("rm"))
            System.out.println("Number of args is 1 : FilePath to be removed or directoryPath to remove files there");
        else if (command.equals("rmdir"))
            System.out.println("Number of args is 1 : Path of empty directory to be removed");
        else if (command.equals("mkdir"))
            System.out.println("Number of args is 1 : DirectoryPath to be created");
        else if (command.equals("pwd"))
            System.out.println("No parameters");
        else if (command.equals("more"))
            System.out.println("Number of args is 1 : FilePath to be displayed");
        else if (command.equals("cat")) {
            System.out.println("There are 3 options :");
            System.out.println("Number of args is 1 : FilePath to be displayed");
            System.out.println("Number of args is 2 : FilePath1 and FilePath2 to be displayed");
            System.out.println("Number of args is 4 : FilePath1 FilePath2 > FilePath3");
        } else if (command.equals("cp"))
            System.out.println("Number of args is 2 : SourcePath DestinationPath");
        else if (command.equals("mv"))
            System.out.println("Number of args is 2 : SourcePath DestinationPath");
        else if (command.equals("date"))
            System.out.println("No parameters");
        else System.out.println("Error");

    }

    public void args(String command, String filePath, boolean appendMode) {
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(new File(filePath), appendMode));
            if (command.equals("cd")) {
                out.println("There are 2 options :");
                out.println("- No parameters, to change current directory to the default directory");
                out.println("- Number of args is 1 : New directoryPath to change to");
            } else if (command.equals("ls"))
                out.println("No parameters");
            else if (command.equals("rm"))
                out.println("Number of args is 1 : FilePath to be removed or directoryPath to remove files there");
            else if (command.equals("rmdir"))
                out.println("Number of args is 1 : Path of empty directory to be removed");
            else if (command.equals("mkdir"))
                out.println("Number of args is 1 : DirectoryPath to be created");
            else if (command.equals("pwd"))
                out.println("No parameters");
            else if (command.equals("more"))
                out.println("Number of args is 1 : FilePath to be displayed");
            else if (command.equals("cat")) {
                out.println("There are 3 options :");
                out.println("Number of args is 1 : FilePath to be displayed");
                out.println("Number of args is 2 : FilePath1 and FilePath2 to be displayed");
                out.println("Number of args is 4 : FilePath1 FilePath2 > FilePath3");
            } else if (command.equals("cp"))
                out.println("Number of args is 2 : SourcePath DestinationPath");
            else if (command.equals("mv"))
                out.println("Number of args is 2 : SourcePath DestinationPath");
            else if (command.equals("date"))
                out.println("No parameters");
            else out.println("Error");
            out.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    public void help() {
        System.out.println("cd directoryPath: moving to another directory.");
        System.out.println("ls: print all files and directories in current path.");
        System.out.println("rm filePath or directoryPath : removing file or removing files from a directory");
        System.out.println("rmdir directoryPath : delete an empty directory.");
        System.out.println("mkdir directoryPath : creating new directory.");
        System.out.println("pwd : print working directory.");
        System.out.println("cat FilePath : Printing the content of a text file.");
        System.out.println("cat FilePath1 FilePath2 > FilePath3 : Printing the content of file1 and file2 in file3.");
        System.out.println("more FilePath : Printing the content of a text file in a scrollable manner.");
        System.out.println("cp SourcePath DestinationPath : Copying the content from SourceFile to DestinationFile.");
        System.out.println("mv SourcePath DestinationPath : Moving the content from SourceFile to DestinationFile.");
        System.out.println("clear : Clearing console content.");
        System.out.println("args commandName : List all command arguments.");
        System.out.println("date : Current date/time.");
        System.out.println("exit : Stop all.");
    }

    public void help(String filePath, boolean appendMode) {
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(new File(filePath), appendMode));
            out.println("cd directoryPath: moving to another directory.");
            out.println("ls: print all files and directories in current path.");
            out.println("rm filePath or directoryPath : removing file or removing files from a directory");
            out.println("rmdir directoryPath : delete an empty directory.");
            out.println("mkdir directoryPath : creating new directory.");
            out.println("pwd : print working directory.");
            out.println("cat FilePath : Printing the content of a text file.");
            out.println("cat FilePath1 FilePath2 > FilePath3 : Printing the content of file1 and file2 in file3.");
            out.println("more FilePath : Printing the content of a text file in a scrollable manner.");
            out.println("cp SourcePath DestinationPath : Copying the content from SourceFile to DestinationFile.");
            out.println("mv SourcePath DestinationPath : Moving the content from SourceFile to DestinationFile.");
            out.println("clear : Clearing console content.");
            out.println("args commandName : List all command arguments.");
            out.println("date : Current date/time.");
            out.println("exit : Stop all.");
            out.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }
}
