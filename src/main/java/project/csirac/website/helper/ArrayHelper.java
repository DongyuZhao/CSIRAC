package project.csirac.website.helper;

/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
public class ArrayHelper
{
    public static String[] getSubArray(String[] source, int start, int length)
    {
        if (start >= source.length)
        {
            throw new IllegalArgumentException("Index Over Boundary");
        }
        if (length <= 0)
        {
            throw new IllegalArgumentException("Length Should Be a Positive Integer");
        }
        int finalLength = Math.min(source.length - start + 1, length);
        String[] result = new String[finalLength];
        for(int i = 0; i < finalLength; i++)
        {
            result[i] = source[i + start];
        }
        return result;
    }
}
