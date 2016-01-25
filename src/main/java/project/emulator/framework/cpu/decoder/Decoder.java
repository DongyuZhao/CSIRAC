package project.emulator.framework.cpu.decoder;

import project.emulator.framework.Bootstrap;
import project.emulator.framework.api.decoder.IDecodeUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class Decoder implements IDecoder
{
    private Map<String, IDecodeUnit> _decoderList = new HashMap<>();

    public Decoder()
    {

    }

    public static IDecoder createInstance(List<IDecodeUnit> IDecodeUnits)
    {
        IDecoder decoder = new Decoder();
        for (IDecodeUnit IDecodeUnit : IDecodeUnits)
        {
            decoder.registerDecodeUnit(IDecodeUnit.getType(), IDecodeUnit);
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
            IDecodeUnit IDecodeUnit = this._decoderList.get(type);
            Command decoded = IDecodeUnit.decode(data);
            if (decoded != null)
            {
                result[i] = decoded;
            }
        }
        return result;
    }

    @Override
    public void registerDecodeUnit(String type, IDecodeUnit IDecodeUnit)
    {
        if (IDecodeUnit != null)
        {
            this._decoderList.put(type, IDecodeUnit);
        }
    }
}
