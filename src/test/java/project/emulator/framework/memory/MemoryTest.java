package project.emulator.framework.memory;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dy.Zhao on 2016/1/25 0025.
 */
public class MemoryTest
{

    @Test
    public void testPut() throws Exception
    {
        Memory memory = new Memory("", false);
        memory.put(31,31, new int[] {15,15,15,15});
        Assert.assertArrayEquals(null, memory.get(31,31), new int[] {15,15,15,15});
    }

    @Test
    public void testGet() throws Exception
    {
        Memory memory = new Memory("", false);
        memory.put(31,31, new int[] {15,15,15,15});
        Assert.assertArrayEquals(null, memory.get(15,15), new int[] {0,0,0,0});
        Assert.assertArrayEquals(null, memory.get(31,31), new int[] {15,15,15,15});
        Assert.assertArrayEquals(null, memory.get(32,32), new int[] {-32,-32,-32,-32});
    }

    @Test
    public void testLoad() throws Exception
    {
        Memory memory = new Memory("", false);
        String[] list = new String[] {"1 1 1 1","2 2 2 2", "3 3 3 3"};
        memory.load(list);
        Assert.assertArrayEquals(null, memory.get(0,1), new int[] {-1,-1,-1,-1});
    }
}