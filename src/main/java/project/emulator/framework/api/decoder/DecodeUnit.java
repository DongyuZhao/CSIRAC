package project.emulator.framework.api.decoder;

import project.emulator.framework.Bootstrap;
import project.emulator.framework.cpu.decoder.Command;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public abstract class DecodeUnit
{
    public Command decode(int[] data)
    {
        Command command = new Command();
        command.opCode = Bootstrap.innerConfig.finishSignal();
        return command;
    }

    public String getType()
    {
        return "";
    }
}
