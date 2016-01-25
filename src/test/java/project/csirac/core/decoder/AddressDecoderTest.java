package project.csirac.core.decoder;

import org.junit.Assert;
import org.junit.Test;
import project.csirac.core.Bootstrap;

import static org.junit.Assert.*;

/**
 * Created by Dy.Zhao on 2016/1/25 0025.
 */
public class AddressDecoderTest
{

    @Test
    public void testDetermineAddress() throws Exception
    {
        Bootstrap.registerCsiracSymbolTable();
        Assert.assertEquals(null,AddressDecoder.determineAddress(Bootstrap.symbolTranslator.translateToCode("M"), 1, 1),33);
        Assert.assertEquals(null,AddressDecoder.determineAddress(Bootstrap.symbolTranslator.translateToCode("A"), 255,255), Bootstrap.symbolTranslator.translateToCode("A"));
    }
}