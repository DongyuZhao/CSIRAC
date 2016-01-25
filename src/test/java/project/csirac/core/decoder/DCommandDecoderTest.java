package project.csirac.core.decoder;

import org.junit.Assert;
import org.junit.Test;
import project.csirac.core.Bootstrap;
import project.emulator.framework.cpu.decoder.Command;

import static org.junit.Assert.*;

/**
 * Created by Dy.Zhao on 2016/1/25 0025.
 */
public class DCommandDecoderTest
{

    @Test
    public void testDecode() throws Exception
    {
        Bootstrap.registerCsiracSymbolTable();
        DCommandDecoder dcoder = new DCommandDecoder();
        Command actual = dcoder.decode(new int[]{0,1,Bootstrap.symbolTranslator.translateToCode("M"),Bootstrap.symbolTranslator.translateToCode("A")});
        Command expected = new Command();
        expected.opCode = Bootstrap.symbolTranslator.translateToCode("A");
        expected.source = Bootstrap.symbolTranslator.translateToCode("Temp");
        expected.target = expected.opCode;
        Assert.assertEquals(null, actual.opCode,expected.opCode);
        Assert.assertEquals(null, actual.source,expected.source);
        Assert.assertEquals(null, actual.target,expected.target);

        actual = dcoder.decode(new int[]{0,1,Bootstrap.symbolTranslator.translateToCode("A"),Bootstrap.symbolTranslator.translateToCode("M")});
        expected = new Command();
        expected.opCode = Bootstrap.symbolTranslator.translateToCode("M");
        expected.source = Bootstrap.symbolTranslator.translateToCode("Temp");
        expected.target = 1;
        Assert.assertEquals(null, actual.opCode,expected.opCode);
        Assert.assertEquals(null, actual.source,expected.source);
        Assert.assertEquals(null, actual.target,expected.target);
    }
}