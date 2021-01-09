import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


class Assembler
{
    private String inputFile ; // input and output files
    private PrintWriter out;
    private SymbolTable table = new SymbolTable();  // symbol table for variables / labels

    public Assembler(String file) throws IOException
    {

        this.inputFile = file;
        String outputFile = inputFile.replaceAll("\\..*", "") + ".hack";
        out = new PrintWriter(new FileWriter(outputFile));
        // initialize symbol table
        table.initialize();
    }

    // first pass of the assembler
    // go through file building the symbol table
    // only labels are handled, variables are handeled in the 2nd pass
    public void firstAssembler() throws IOException
    {
        Parser parser = new Parser(inputFile);
        int romAddress = 0;
        String symbol;

        while (parser.advance())
        {
            if (parser.commandType() == Parser.CommandType.L_COMMAND)
            {
                symbol = parser.symbol();
                if (!table.contains(symbol))
                    table.addEntry(symbol, romAddress);
            }
        }
        parser.close();
        return;
    }

    // 2nd pass of the assembler
    // handle variables
    // generate code, replace symbols with values from symbol table
    public void secondAssembler() throws IOException
    {
        Parser parser = new Parser(inputFile);
        String dest, comp, jump;
        String symbol, value;
        int ramAddress = 16;// starting address for variables

        while (parser.advance())
        {
            try
            {
                if (parser.commandType() == Parser.CommandType.C_COMMAND)
                {
                    dest = parser.dest();
                    comp = parser.comp();
                    jump = parser.jump();

                    out.println("111" + Code.comp(comp) + Code.dest(dest) + Code.jump(jump));
                } else if (parser.commandType() == Parser.CommandType.A_COMMAND)
                {
                    symbol = parser.symbol();
                    if (Character.isDigit(symbol.charAt(0)))
                    {
                        value = Code.toBinary(symbol);
                    }
                    else if (table.contains(symbol))
                    {
                        value = Integer.toString(table.getAddress(symbol));
                        value = Code.toBinary(value);
                    }
                    else
                    {
                        // print warnings about memory usage
                        if (ramAddress > 16383)
                            System.err.println("Warning: allocating variable in I/O memory map");
                        if (ramAddress > 24576)
                            System.err.println("Warning: no more RAM left");

                        table.addEntry(symbol, ramAddress);
                        value = Code.toBinary("" + ramAddress);
                        ramAddress++;
                    }

                    out.println("0" + value);
                }
            }
            catch ( Exception ex) {
                System.out.println("Invalid destination");
            }

        }
        parser.close();
        return;
    }

    //stop reading and close the output file
    public void close() throws IOException
    {
        out.close();
        return;
    }
}
