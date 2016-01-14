package project.csirac.models.emulator;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public interface IDecoder
{
    DataType DecodeData(String data);

    Instruction DecodeInstruction(String instruction);

    String[] peekSupportedInstruction();
}
