import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    public String currentCommand = "";// current command
    public String inputFile; // file being assembled
    public int LineCount = 0; // current line number in the file

    public String currentLine;// current line in file


    private BufferedReader fileReader; // input file reader

    // command types
    enum CommandType {
        A_COMMAND,      // A instruction
        C_COMMAND,      // C instruction
        L_COMMAND       // (Symbol)
    }

    public Parser(String file) throws FileNotFoundException  // open input file and get ready to parse it
    {
        inputFile = file;
        fileReader = new BufferedReader(new FileReader(file));
        LineCount = 0;
    }


    public boolean advance() throws IOException {
        while (true) {
            currentLine = fileReader.readLine();
            LineCount++;
            if (currentLine == null)
                return false;
            currentCommand = currentLine.replaceAll("//.*$", "").trim();
            if (currentCommand.equals(""))
                continue;

            return true;
        }
    }

    // returns the type of the current command
    // A_COMMAND for @xxx
    // C_COMMAND for dest=comp;jump
    // L_COMMAND for a label, (xxx)
    public CommandType commandType() {
        if (currentCommand.startsWith("@")) {
            return CommandType.A_COMMAND;
        } else if (currentCommand.startsWith("(")) {
            return CommandType.L_COMMAND;
        } else {
            return CommandType.C_COMMAND;
        }
    }

    // returns symbol or decimal xxx of the current command
    // only applies to A_COMMAND or L_COMMAND
    public String symbol() {
        return currentCommand.substring(1).replace(")", "");
    }

    // returns dest field of current command (8 possibilities)
    // only applies to C_COMMAND
    public String dest() {
        String dest = "";
        if (currentCommand.contains("=")) {
            String[] array = currentCommand.split("=");
            dest = array[0];
        }
        return dest;
    }

    // returns the comp field of the current command (28 possibilities)
    // only applies to C_COMMAND
    public String comp() {
        String comp;
        if (currentCommand.contains("=")) {
            String[] array = currentCommand.split("=");
            String[] array1 = array[1].split(";");
            comp = array1[0];
        } else {
            String[] array = currentCommand.split(";");
            comp = array[0];
        }
        return comp;
    }

    // returns the jump field of the current command (8 possibilities)
    // only applies to C_COMMAND
    public String jump() {
        String jump = "";
        if (currentCommand.contains(";")) {
            String[] array = currentCommand.split(";");
            jump = array[1];
        }
        return jump;
    }

    // close input file
    public void close() throws IOException {
        fileReader.close();
        return;
    }
}
