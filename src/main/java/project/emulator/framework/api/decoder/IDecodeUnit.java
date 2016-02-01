package project.emulator.framework.api.decoder;

import project.emulator.framework.Bootstrap;
import project.emulator.framework.cpu.decoder.Command;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public interface IDecodeUnit
{
    /**
     * Decode the instruction into a Command Object for processor to process
     *
     * @param data the instruction
     * @return the decoded command
     */
    Command decode(int[] data);

    /**
     * The decoder's instruction type
     *
     * @return
     */
    String getType();
}
