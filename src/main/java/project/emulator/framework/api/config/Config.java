package project.emulator.framework.api.config;

import java.util.regex.Pattern;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class Config implements IConfig
{
    @Override
    public int defaultCellContent()
    {
        return -1;
    }

    @Override
    public int trimCellContent()
    {
        return 0;
    }

    @Override
    public int opCodeMismatchReturnValue()
    {
        return -64;
    }

    @Override
    public int finishSignal()
    {
        return -32;
    }

    @Override
    public int mainDataSectionCount()
    {
        return 4;
    }

    @Override
    public int simplifiedDataSectionCount()
    {
        return 2;
    }

    @Override
    public Pattern inputFilterPattern()
    {
        return Pattern.compile("[\\s]*\\w[,|\\s]+\\w[,|\\s]+\\w[,|\\s]+\\w[,|\\s]*");
    }

    @Override
    public Pattern simplifiedFilterPattern()
    {
        return Pattern.compile("[\\s]*\\w[\\s]+\\w[\\s]*");
    }

    @Override
    public boolean alignLeft()
    {
        return false;
    }

    @Override
    public String[] commandType()
    {
        return new String[]{"S","D"};
    }

    @Override
    public String[] decodePriority()
    {
        return new String[]{"S","D"};
    }

    @Override
    public int unitCount()
    {
        return 32;
    }

    @Override
    public int cellPerUnit()
    {
        return 32;
    }

    @Override
    public int defaultPcRegGrowth()
    {
        return -1;
    }

    @Override
    public int opCodeUpperbound()
    {
        return 32;
    }
}
