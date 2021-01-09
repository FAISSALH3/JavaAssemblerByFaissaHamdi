public class Code
{
    // returns binary code (3 bits) of dest mnemnonic
    public static String dest(String mnemnonic) throws Exception
    {
        String d1 = "0";
        String d2 = "0";
        String d3 = "0";
        if (mnemnonic.contains("A"))
            d1 = "1";
        if (mnemnonic.contains("D"))
            d2 = "1";
        if (mnemnonic.contains("M"))
            d3 = "1";

        if ((d1+d2+d3).equals("000") && !mnemnonic.equals(""))
        {
            throw new Exception();
        }

        return d1 + d2 + d3;
    }

    // returns binary code (7 bits) of comp mnemnonic
    public static String comp(String mnemnonic) throws Exception
    {
        String a = "0";
        if (mnemnonic.contains("M"))
        {
            a = "1";
            mnemnonic = mnemnonic.replace("M", "A");
        }
        String c = "000000";
        switch (mnemnonic) // i preferred to use switch instead of HashMap
        {
            case "0":   c = "101010";
                break;
            case "1":   c = "111111";
                break;
            case "-1":  c = "111010";
                break;
            case "D":   c = "001100";
                break;
            case "A":   c = "110000";
                break;
            case "!D":  c = "001101";
                break;
            case "!A":  c = "110001";
                break;
            case "-D":  c = "001111";
                break;
            case "-A":  c = "110011";
                break;
            case "D+1": c = "011111";
                break;
            case "A+1": c = "110111";
                break;
            case "D-1": c = "001110";
                break;
            case "A-1": c = "110010";
                break;
            case "D+A": c = "000010";
                break;
            case "D-A": c = "010011";
                break;
            case "A-D": c = "000111";
                break;
            case "D&A": c = "000000";
                break;
            case "D|A": c = "010101";
                break;
            default:    throw new Exception();
        }

        return a + c;
    }

    // returns binary code (3 bits) of jump mnemnonic
    public static String jump(String mnemnonic) throws Exception
    {
        switch (mnemnonic)
        {
            case "":    return "000";
            case "JGT": return "001";
            case "JEQ": return "010";
            case "JGE": return "011";
            case "JLT": return "100";
            case "JNE": return "101";
            case "JLE": return "110";
            case "JMP": return "111";
            default:    throw new Exception();
        }
    }

    // converts a numeric symbol to binary (15 bits)
    public static String toBinary(String symbol)
    {
        int value = Integer.valueOf(symbol);
        String binary = Integer.toBinaryString(value);

        return String.format("%1$15s", binary).replace(" ", "0");
    }
}