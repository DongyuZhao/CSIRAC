package project.csirac.core.decoder;

import org.junit.Assert;
import org.junit.Test;
import project.csirac.core.CsiracBootstrap;
import project.emulator.framework.cpu.models.Command;

/**
 * Created by Dy.Zhao on 2016/1/25 0025.
 */
public class SCommandDecoderTest
{

    @Test
    public void testDecode() throws Exception
    {
        CsiracBootstrap.registerCsiracSymbolTable();
        SCommandDecoder dcoder = new SCommandDecoder();
        Command actual = dcoder.decode(new int[]{0,1,CsiracBootstrap.getSymbolTranslator().translateToCode("M"),CsiracBootstrap.getSymbolTranslator().translateToCode("A")});
        Command expected = new Command();
        expected.opCode = CsiracBootstrap.getSymbolTranslator().translateToCode("M");
        expected.source = 1;
        expected.target = CsiracBootstrap.getSymbolTranslator().translateToCode("Temp");
        Assert.assertEquals(null, actual.opCode,expected.opCode);
        Assert.assertEquals(null, actual.source,expected.source);
        Assert.assertEquals(null, actual.target,expected.target);

        actual = dcoder.decode(new int[]{0,1,CsiracBootstrap.getSymbolTranslator().translateToCode("A"),CsiracBootstrap.getSymbolTranslator().translateToCode("M")});
        expected = new Command();
        expected.opCode = CsiracBootstrap.getSymbolTranslator().translateToCode("A");
        expected.source = CsiracBootstrap.getSymbolTranslator().translateToCode("A");
        expected.target = CsiracBootstrap.getSymbolTranslator().translateToCode("Temp");
        Assert.assertEquals(null, actual.opCode,expected.opCode);
        Assert.assertEquals(null, actual.source,expected.source);
        Assert.assertEquals(null, actual.target,expected.target);
    }
}