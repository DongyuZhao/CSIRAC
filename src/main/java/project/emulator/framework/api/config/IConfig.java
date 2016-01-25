package project.emulator.framework.api.config;

import java.util.regex.Pattern;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public interface IConfig
{
    int defaultCellContent();

    int trimCellContent();

    int opCodeMismatchReturnValue();

    int finishSignal();

    int mainDataSectionCount();

    int simplifiedDataSectionCount();

    Pattern inputFilterPattern();

    Pattern simplifiedFilterPattern();

    boolean alignLeft();

    String[] commandType();

    String[] decodePriority();

    public int unitCount();

    public int cellPerUnit();

    public int defaultPcRegGrowth();

    public int opCodeUpperbound();
}
