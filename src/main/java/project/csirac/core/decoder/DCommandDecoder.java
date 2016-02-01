package project.csirac.core.decoder;

import project.csirac.core.CsiracBootstrap;
import project.emulator.framework.api.decoder.IDecodeUnit;
import project.emulator.framework.cpu.models.Command;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class DCommandDecoder implements IDecodeUnit {

    @Override
    public Command decode(int[] data) {
        int[] trimmedData = CsiracBootstrap.symbolTranslator.trimData(data);
        Command result = new Command();
        result.opCode = trimmedData[3];
        result.source = CsiracBootstrap.symbolTranslator.translateToCode("Temp");
        result.target = AddressDecoder.determineAddress(result.opCode, trimmedData[0], trimmedData[1]);
        result.commandType = "D";
        return result;
    }

    @Override
    public String getType() {
        return "D";
    }
}
