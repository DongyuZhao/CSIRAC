package project.csirac.core.register;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dy.Zhao on 2016/1/25 0025.
 */
public class RegisterTest
{

    @Test
    public void testPutGet() throws Exception
    {
        Register register = new Register("");
        register.put(45, new int[]{1, 1, 1, 1});
        register.put(7, new int[]{1, 1, 1, 1});
        Assert.assertArrayEquals(null, new int[]{1, 1, 1, 1}, register.get(45));
        Assert.assertArrayEquals(null, new int[]{-32,-32,-32,-32}, register.get(1));
    }
}