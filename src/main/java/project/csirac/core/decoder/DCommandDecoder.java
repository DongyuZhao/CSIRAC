package project.csirac.core.decoder;

import project.csirac.core.Bootstrap;
import project.emulator.framework.api.decoder.IDecodeUnit;
import project.emulator.framework.cpu.decoder.Command;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class DCommandDecoder implements IDecodeUnit
{
    @Override
    public Command decode(int[] data)
    {
        int[] trimmedData = Bootstrap.symbolTranslator.trimData(data);
        Command result = new Command();
        result.opCode = trimmedData[3];
        result.source = Bootstrap.symbolTranslator.translateToCode("Temp");
        result.target = AddressDecoder.determineAddress(result.opCode, trimmedData[0], trimmedData[1]);
        result.commandType = "D";
        return result;
    }

    @Override
    public String getType()
    {
        return "D";
    }
}