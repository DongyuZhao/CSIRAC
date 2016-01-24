package project.emulator.framework.cpu.register;

/**
 * Created by Dy.Zhao on 2016/1/21 0021.
 */
public interface IRegister
{
    boolean put(int address, int[] data);

    int[] get(int address);
}
