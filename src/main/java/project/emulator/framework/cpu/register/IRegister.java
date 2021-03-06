package project.emulator.framework.cpu.register;

/**
 * Created by Dy.Zhao on 2016/1/21 0021.
 */
public interface IRegister {
    /**
     * save the data
     *
     * @param address the destination address
     * @param data the data
     * @return if saved successful
     */
    boolean put(int address, int[] data);

    /**
     * get the data on the address
     *
     * @param address the address
     * @return the data
     */
    int[] get(int address);
}
