package project.csirac.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dy.Zhao on 2016/1/5 0005.
 */
public class StringHelper
{
    public static boolean isNullOrWhiteSpace(java.lang.String target)
    {
        if (target == null)
        {
            return true;
        }
        Pattern pattern = Pattern.compile("[\\s]*");
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }
}
