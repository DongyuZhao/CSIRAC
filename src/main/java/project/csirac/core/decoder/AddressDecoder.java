package project.csirac.core.decoder;

import project.csirac.core.Bootstrap;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class AddressDecoder
{
    static int determineAddress(int opCode, int section0, int section1)
    {
        int address = -1;
        if (opCode == Bootstrap.symbolTranslator.translateToCode("M"))
        {
            address = section0 * Bootstrap.innerConfig.cellPerUnit() + section1;
        }
        else if (opCode == Bootstrap.symbolTranslator.translateToCode("D"))
        {
            address = section1 % 16;
        }
        else if (opCode == Bootstrap.symbolTranslator.translateToCode("A") || opCode == Bootstrap.symbolTranslator.translateToCode("B") || opCode == Bootstrap.symbolTranslator.translateToCode("C"))
        {
            address = opCode;
        }
        // TODO:: Add for further instructions
        return address;
    }
}
