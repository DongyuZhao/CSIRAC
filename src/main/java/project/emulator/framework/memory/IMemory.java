package project.emulator.framework.memory;

import java.util.List;

/**
 * Created by Dy.Zhao on 2016/1/21 0021.
 */
public interface IMemory {
    boolean put(int unitAddress, int cellAddress, int[] data);

    int[] get(int unitAddress, int cellAddress);

    int unitCount();

    int cellPerUnit();

    boolean load(String[] input);

    boolean isInstructionMemory();
}
