import java.io.File;

public class FSObject {
    private final String pathname;
    private final File file;
    private final boolean exists;
    private final boolean isDirectory;

    public FSObject(String pathname) {
        this.pathname = pathname;
        // TODO: use newDirectoryStream from java.nio.file.Files
        this.file = new File(pathname);
        this.exists = file.exists();
        this.isDirectory = this.file.isDirectory();
    }

    private FSObject(File file) {
        this.pathname = file.getPath();
        this.file = file;
        this.exists = true;
        this.isDirectory = file.isDirectory();
    }

    private String getIndent(int indent) {
        return " ".repeat(indent * 2);
    }

    public void print(int indent) {
        if (isDirectory) {
            System.out.printf("%sd %s\n", getIndent(indent), pathname);

            // Dereference of 'this.file.listFiles()' may produce 'NullPointerException'
            for(File dirItem : this.file.listFiles()) {
                new FSObject(dirItem).print(indent + 1);
            }
        }
        else if (exists) {
            System.out.printf("%s- %s\n", getIndent(indent), pathname);
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            new FSObject(args[0]).print(0);
        }
        else {
            System.out.println("Please specify path to print");
        }
    }
}
