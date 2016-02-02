package project.emulator.framework.cpu.decoder;

import project.emulator.framework.api.decoder.IDecodeUnit;
import project.emulator.framework.cpu.models.Command;

import java.util.List;

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
     * @param decodeUnits
     */
    void registerDecodeUnits(String type, List<IDecodeUnit> decodeUnits);
}
