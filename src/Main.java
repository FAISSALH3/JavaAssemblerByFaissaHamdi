import jdk.swing.interop.SwingInterOpUtils;

//Assembler By fayssal hamdi , wuhan university of technolgy

import javax.swing.*;
import java.io.*;

class Main
{

    public static void main(String[] args)
    {

        String FileToAssemble ="C:\\Users\\lenovo\\Desktop\\nand2tetris\\projects\\04\\Mult22.asm" ; // specify the directory by example: "C:\\Users\\lenovo\\Desktop\\nand2tetris\\projects\\04\\Mult.asm";

        try {
            Assembler assembler = new Assembler(FileToAssemble);
            assembler.firstAssembler();
            assembler.secondAssembler();
            assembler.close();
        } catch (FileNotFoundException ex)
        {
            System.out.println("file \'" + FileToAssemble + "\' not found");
        } catch (IOException ex)
        {
            System.out.println("error has occured :(");
        }

        System.out.println("the hack file has successfully saved to the same .asm file location " );
    }
}