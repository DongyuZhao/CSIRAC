package project.csirac.models.emulator;

import project.csirac.models.emulator.model.DataType;
import project.csirac.models.emulator.model.Instruction;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public interface IDecoder
{
    DataType DecodeData(String data);

    Instruction DecodeInstruction(String instruction);

    String[] peekSupportedInstruction();
}
