import java.util.HashMap;

public class SymbolTable {

    private HashMap<String, Integer> symbolTable;

    // create an empty symbol table
    public SymbolTable()
    {
        symbolTable = new HashMap<String, Integer>();
    }

    // add an entry to the symbol table
    public void addEntry(String symbol, int address)
    {
        symbolTable.put(symbol, address);
        return;
    }

    // check if the symbol table contains the given symbol
    public boolean contains(String symbol)
    {
        return symbolTable.containsKey(symbol);
    }

    // return the address for a given symbol in the table
    public int getAddress(String symbol)
    {
        return symbolTable.get(symbol);
    }

    // initialize the symbol table with predefined symbols
    public void initialize()
    {
        // virtual registers
        for (int i = 0; i < 16; i++)
            this.addEntry("R" + i, i);

        // i/o pointers
        this.addEntry("KBD", 24576);
        this.addEntry("SCREEN", 16384);

        // other symbols
        this.addEntry("SP", 0);
        this.addEntry("LCL", 1);
        this.addEntry("ARG", 2);
        this.addEntry("THIS", 3);
        this.addEntry("THAT", 4);

        return;
    }
}