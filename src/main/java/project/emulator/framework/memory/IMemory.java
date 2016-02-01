package project.emulator.framework.memory;

import java.util.List;

/**
 * Created by Dy.Zhao on 2016/1/21 0021.
 */
public interface IMemory {
    /**
     * save data to address
     *
     * @param unitAddress the unit address
     * @param cellAddress the cell address
     * @param data the data
     * @return if the save successful
     */
    boolean put(int unitAddress, int cellAddress, int[] data);

    /**
     * get data on the given address
     *
     * @param unitAddress the unit address
     * @param cellAddress the cell address
     * @return the data
     */
    int[] get(int unitAddress, int cellAddress);

    int unitCount();

    int cellPerUnit();

    /**
     * save input to memory
     *
     * @param input the input
     * @return if save successful
     */
    boolean load(String[] input);

    boolean isInstructionMemory();
}
