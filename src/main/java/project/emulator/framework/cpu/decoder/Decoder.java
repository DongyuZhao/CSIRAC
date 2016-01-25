package project.emulator.framework.cpu.decoder;

import project.emulator.framework.Bootstrap;
import project.emulator.framework.api.decoder.DecodeUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class Decoder implements IDecoder
{
    private Map<String, DecodeUnit> _decoderList = new HashMap<>();

    public Decoder()
    {

    }

    public static IDecoder createInstance(List<DecodeUnit> decodeUnits)
    {
        IDecoder decoder = new Decoder();
        for (DecodeUnit decodeUnit : decodeUnits)
        {
            decoder.registerDecodeUnit(decodeUnit.getType(), decodeUnit);
        }
        return decoder;
    }


    @Override
    public Command[] decode(int[] data)
    {
        Command[] result = new Command[Bootstrap.innerConfig.commandType().length];
        String[] decodePriority = Bootstrap.innerConfig.decodePriority();
        for (int i = 0; i < decodePriority.length; i++)
        {
            String type = decodePriority[i];
            DecodeUnit decodeUnit = this._decoderList.get(type);
            Command decoded = decodeUnit.decode(data);
            if (decoded != null)
            {
                result[i] = decoded;
            }
        }
        return result;
    }

    @Override
    public void registerDecodeUnit(String type, DecodeUnit decodeUnit)
    {
        if (decodeUnit != null)
        {
            this._decoderList.put(type, decodeUnit);
        }
    }
}
