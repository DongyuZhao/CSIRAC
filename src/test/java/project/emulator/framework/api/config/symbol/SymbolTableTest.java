package project.emulator.framework.api.config.symbol;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Dy.Zhao on 2016/1/25 0025.
 */
public class SymbolTableTest
{

    @Test
    public void testRegisterSymbol() throws Exception
    {
        SymbolTable symbolTable = new SymbolTable();
        String symbol = symbolTable.translateToSymbol(0);
        Assert.assertNotEquals(null, symbol, "A");
        symbolTable.registerSymbol("A", 0);
        symbol = symbolTable.translateToSymbol(0);
        Assert.assertEquals(null, symbol, "A");
    }

    @Test
    public void testTranslateToCode() throws Exception
    {
        SymbolTable symbolTable = new SymbolTable();
        int code = symbolTable.translateToCode("B");
        Assert.assertNotEquals(code, 20);
        symbolTable.registerSymbol("B", 20);
        code = symbolTable.translateToCode("B");
        Assert.assertEquals(code, 20);
    }

    @Test
    public void testTranslateToSymbol() throws Exception
    {
        SymbolTable symbolTable = new SymbolTable();
        String symbol = symbolTable.translateToSymbol(0);
        Assert.assertNotEquals(null, symbol, "C");
        symbolTable.registerSymbol("C", 0);
        symbolTable.registerSymbol("0", 0);
        symbol = symbolTable.translateToSymbol(0);
        Assert.assertEquals(null, symbol, "0/C");
    }

    @Test
    public void testTranslateInput() throws Exception
    {
        SymbolTable symbolTable = new SymbolTable();

        for (int i = 0; i < 32; i++)
        {
            symbolTable.registerSymbol(i + "", i);
        }

        symbolTable.registerSymbol("M", 0);
        symbolTable.registerSymbol("A", 4);
        symbolTable.registerSymbol("B", 11);
        symbolTable.registerSymbol("C", 14);
        symbolTable.registerSymbol("D", 17);
        symbolTable.registerSymbol("Temp", -1024);
        Assert.assertArrayEquals(null, new int[]{0, 0, 0, 4}, symbolTable.translateInput("0,0, M  ,A"));
        Assert.assertArrayEquals(null, new int[]{0, 0, 11, 4}, symbolTable.translateInput("0 0 , B A"));
    }

    @Test
    public void testTranslateOutput() throws Exception
    {
        SymbolTable symbolTable = new SymbolTable();
        for (int i = 0; i < 32; i++)
        {
            symbolTable.registerSymbol(i + "", i);
        }

        symbolTable.registerSymbol("M", 0);
        symbolTable.registerSymbol("A", 4);
        symbolTable.registerSymbol("B", 11);
        symbolTable.registerSymbol("C", 14);
        symbolTable.registerSymbol("D", 17);
        symbolTable.registerSymbol("Temp", -1024);

        Assert.assertEquals(null, symbolTable.translateOutput(new int[] {0,0,0,4}), "0004");
    }

    @Test
    public void testTrimData() throws Exception
    {
        int[] input = new int[]{};
        int[] input1 = new int[] {1};
        int[] input2 = new int[] {1,2};
        int[] input3 = new int[] {1,2,3};
        int[] input4 = new int[] {1,2,3,4};
        int[] input5 = new int[] {1,2,3,4,5};
        SymbolTable symbolTable = new SymbolTable();
        Assert.assertArrayEquals(null,symbolTable.trimData(input),new int[] { 0,0,0,0 });
        Assert.assertArrayEquals(null,symbolTable.trimData(input1),new int[] { 0,0,0,1 });
        Assert.assertArrayEquals(null,symbolTable.trimData(input2),new int[] { 0,0,1,2 });
        Assert.assertArrayEquals(null,symbolTable.trimData(input3),new int[] { 0,1,2,3 });
        Assert.assertArrayEquals(null,symbolTable.trimData(input4),new int[] { 1,2,3,4 });
        Assert.assertArrayEquals(null, symbolTable.trimData(input5),new int[] {2,3,4,5});
    }
}