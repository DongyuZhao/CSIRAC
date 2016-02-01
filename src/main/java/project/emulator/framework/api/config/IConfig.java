package project.emulator.framework.api.config;

import java.util.regex.Pattern;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public interface IConfig {
    /**
     * the default content of the memory cell
     *
     * @return he default content
     */
    int defaultCellContent();

    /**
     * the default content we should fill in when performing trim data
     *
     * @return the default content
     */
    int trimCellContent();

    /**
     * the return value the processor should give when
     *
     * @return the return value
     */
    int opCodeMismatchReturnValue();

    /**
     * the finish signal to indicate the emulator should stop
     *
     * @return the finish signal
     */
    int finishSignal();

    /**
     * the count of the data sections the normal data have
     *
     * @return the count of the data sections
     */
    int normalDataSectionCount();

    /**
     * the count of the data sections the simplified data have
     *
     * @return the count of the data sections
     */
    int simplifiedDataSectionCount();

    /**
     * The regex pattern for filter normal input
     *
     * @return the regex pattern
     */
    Pattern inputFilterPattern();


    /**
     * The regex pattern for filter simplified input
     *
     * @return the regex pattern
     */
    Pattern simplifiedFilterPattern();

    /**
     * should data be trimmed to left or right
     *
     * @return true if align to to left
     */
    boolean alignLeft();

    /**
     * the command type list the emulator should support
     *
     * @return the command type list
     */
    String[] commandType();

    /**
     * the command type list order by its decoding priority
     *
     * @return the command type list order by its decoding priority
     */
    String[] decodePriority();

    /**
     * The memory unit this emulator have
     *
     * @return The memory unit this emulator have
     */
    int unitCount();

    /**
     * The count of cell per unit of memory have
     *
     * @return The count of cell per unit of memory have
     */
    int cellPerUnit();

    /**
     * the step in each automatically growth of the emulator
     *
     * @return
     */
    int defaultPcRegGrowth();

    /**
     * The opCode upper bound
     *
     * @return The opCode upper bound
     */
    int opCodeUpperbound();

    /**
     * The maximum content for each section
     *
     * @return
     */
    int maximumPerSection();
}
