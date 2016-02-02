package project.csirac.core.decoder;

import org.junit.Assert;
import org.junit.Test;
import project.csirac.core.CsiracBootstrap;

import static org.junit.Assert.*;

/**
 * Created by Dy.Zhao on 2016/1/25 0025.
 */
public class AddressDecoderTest
{

    @Test
    public void testDetermineAddress() throws Exception
    {
        CsiracBootstrap.registerCsiracSymbolTable();
        Assert.assertEquals(null,AddressDecoder.determineAddress(CsiracBootstrap.getSymbolTranslator().translateToCode("M"), 1, 1),33);
        Assert.assertEquals(null,AddressDecoder.determineAddress(CsiracBootstrap.getSymbolTranslator().translateToCode("A"), 255,255), CsiracBootstrap.getSymbolTranslator().translateToCode("A"));
    }
}