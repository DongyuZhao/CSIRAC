package project.emulator.framework.cpu.decoder;

import project.emulator.framework.api.decoder.IDecodeUnit;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public interface IDecoder {
    Command[] decode(int[] data);

    void registerDecodeUnit(String type, IDecodeUnit decodeUnit);
}
