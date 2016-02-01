package project.emulator.framework.cpu.decoder;

import project.emulator.framework.api.decoder.IDecodeUnit;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public interface IDecoder {
    /**
     * decode the instruction to find all Commands it contains
     *
     * @param instruction the instruction
     * @return Commands it contains in the priority order
     */
    Command[] decode(int[] instruction);

    /**
     * Add a new decode unit into the emulator
     *
     * @param type
     * @param decodeUnit
     */
    void registerDecodeUnit(String type, IDecodeUnit decodeUnit);
}
