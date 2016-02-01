package project.emulator.framework.api.config.symbol;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Dy.Zhao on 2016/1/25 0025.
 */
public class SymbolTranslatorTest
{

    @Test
    public void testRegisterSymbol() throws Exception
    {
        SymbolTranslator symbolTranslator = new SymbolTranslator();
        String symbol = symbolTranslator.translateToSymbol(0);
        Assert.assertNotEquals(null, symbol, "A");
        symbolTranslator.registerSymbol("A", 0);
        symbol = symbolTranslator.translateToSymbol(0);
        Assert.assertEquals(null, symbol, "A");
    }

    @Test
    public void testTranslateToCode() throws Exception
    {
        SymbolTranslator symbolTranslator = new SymbolTranslator();
        int code = symbolTranslator.translateToCode("B");
        Assert.assertNotEquals(code, 20);
        symbolTranslator.registerSymbol("B", 20);
        code = symbolTranslator.translateToCode("B");
        Assert.assertEquals(code, 20);
    }

    @Test
    public void testTranslateToSymbol() throws Exception
    {
        SymbolTranslator symbolTranslator = new SymbolTranslator();
        String symbol = symbolTranslator.translateToSymbol(0);
        Assert.assertNotEquals(null, symbol, "C");
        symbolTranslator.registerSymbol("C", 0);
        symbolTranslator.registerSymbol("0", 0);
        symbol = symbolTranslator.translateToSymbol(0);
        Assert.assertEquals(null, symbol, "0/C");
    }

    @Test
    public void testTranslateInput() throws Exception
    {
        SymbolTranslator symbolTranslator = new SymbolTranslator();

        for (int i = 0; i < 32; i++)
        {
            symbolTranslator.registerSymbol(i + "", i);
        }

        symbolTranslator.registerSymbol("M", 0);
        symbolTranslator.registerSymbol("A", 4);
        symbolTranslator.registerSymbol("B", 11);
        symbolTranslator.registerSymbol("C", 14);
        symbolTranslator.registerSymbol("D", 17);
        symbolTranslator.registerSymbol("Temp", -1024);
        Assert.assertArrayEquals(null, new int[]{0, 0, 0, 4}, symbolTranslator.translateInput("0,0, M  ,A"));
        Assert.assertArrayEquals(null, new int[]{0, 0, 11, 4}, symbolTranslator.translateInput("0 0 , B A"));
    }

    @Test
    public void testTranslateOutput() throws Exception
    {
        SymbolTranslator symbolTranslator = new SymbolTranslator();
        for (int i = 0; i < 32; i++)
        {
            symbolTranslator.registerSymbol(i + "", i);
        }

        symbolTranslator.registerSymbol("M", 0);
        symbolTranslator.registerSymbol("A", 4);
        symbolTranslator.registerSymbol("B", 11);
        symbolTranslator.registerSymbol("C", 14);
        symbolTranslator.registerSymbol("D", 17);
        symbolTranslator.registerSymbol("Temp", -1024);

        Assert.assertEquals(null, symbolTranslator.translateOutput(new int[] {0,0,0,4}), "0004");
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
        SymbolTranslator symbolTranslator = new SymbolTranslator();
        Assert.assertArrayEquals(null, symbolTranslator.trimData(input),new int[] { 0,0,0,0 });
        Assert.assertArrayEquals(null, symbolTranslator.trimData(input1),new int[] { 0,0,0,1 });
        Assert.assertArrayEquals(null, symbolTranslator.trimData(input2),new int[] { 0,0,1,2 });
        Assert.assertArrayEquals(null, symbolTranslator.trimData(input3),new int[] { 0,1,2,3 });
        Assert.assertArrayEquals(null, symbolTranslator.trimData(input4),new int[] { 1,2,3,4 });
        Assert.assertArrayEquals(null, symbolTranslator.trimData(input5),new int[] {2,3,4,5});
    }
}