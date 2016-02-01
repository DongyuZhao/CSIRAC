package project.emulator.framework.api.config.symbol;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public interface ISymbolTranslator
{
    /**
     * register the map between the symbol of an instruction and its inner code into the emulator
     *
     * @param symbol the symbol of an instruction
     * @param code the inner code
     */
    void registerSymbol(String symbol, int code);

    /**
     * translate the symbol to code
     *
     * @param symbol the symbol
     * @return the code
     */
    int translateToCode(String symbol);

    /**
     * translate the code to symbol
     *
     * @param code the code
     * @return the symbol
     */
    String translateToSymbol(int code);

    /**
     * translate the input to its inner expression in the emulator
     *
     * @param input the input
     * @return the inner expression
     */
    int[] translateInput(String input);


    /**
     * translate the output in the inner expression format to symbol format
     *
     * @param date
     * @return
     */
    String translateOutput(int[] date);

    /**
     * trim the data to default length
     *
     * @param data the data
     * @return the trimmed data
     */
    int[] trimData(int[] data);
}
