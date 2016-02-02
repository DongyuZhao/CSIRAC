package project.csirac.core.decoder;

import project.csirac.core.CsiracBootstrap;
import project.emulator.framework.api.decoder.IDecodeUnit;
import project.emulator.framework.cpu.models.Command;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class SCommandDecoder implements IDecodeUnit {

    @Override
    public Command decode(int[] data) {
        int[] trimmedData = CsiracBootstrap.getSymbolTranslator().trimData(data);
        Command result = new Command();
        result.opCode = trimmedData[2];
        result.source = AddressDecoder.determineAddress(result.opCode, trimmedData[0], trimmedData[1]);
        result.target = CsiracBootstrap.getSymbolTranslator().translateToCode("Temp");
        result.commandType = "S";
        return result;
    }

    @Override
    public String getType() {
        return "S";
    }
}
