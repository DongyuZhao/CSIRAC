package project.emulator.framework.cpu.decoder;

import project.emulator.framework.Bootstrap;
import project.emulator.framework.api.decoder.IDecodeUnit;
import project.emulator.framework.cpu.models.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class Decoder implements IDecoder {
    private Map<String, IDecodeUnit> _decoderList = new HashMap<>();

    public Decoder() {

    }

    public static IDecoder createInstance(List<IDecodeUnit> decodeUnites) {
        IDecoder decoder = new Decoder();
        for (IDecodeUnit IDecodeUnit : decodeUnites) {
            decoder.registerDecodeUnit(IDecodeUnit.getType(), IDecodeUnit);
        }
        return decoder;
    }
    /*
     *Parse all the instructions by using the former decoder.
     *Derive all the operation command objects and the series of them.
     */

    @Override
    public Command[] decode(int[] instruction) {
        Command[] result = new Command[Bootstrap.getInnerConfig().commandType().length];
        String[] decodePriority = Bootstrap.getInnerConfig().decodePriority();
        for (int i = 0; i < decodePriority.length; i++) {
            String type = decodePriority[i];
            IDecodeUnit IDecodeUnit = this._decoderList.get(type);
            Command decoded = IDecodeUnit.decode(instruction);
            if (decoded != null) {
                result[i] = decoded;
            }
        }
        return result;
    }
    /*
     * register a pair of type and the particular decoder which used this type.
     * @param type  two types here: source command and destination command.
     */
    @Override
    public void registerDecodeUnit(String type, IDecodeUnit decodeUnit) {
        if (decodeUnit != null) {
            this._decoderList.put(type, decodeUnit);
        }
    }
}
