package project.emulator.framework.api.decoder;

import project.emulator.framework.Bootstrap;
import project.emulator.framework.cpu.decoder.Command;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public interface IDecodeUnit
{
    Command decode(int[] data);

    String getType();
}
