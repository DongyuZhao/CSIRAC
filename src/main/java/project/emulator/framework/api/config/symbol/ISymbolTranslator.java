package project.emulator.framework.api.config.symbol;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public interface ISymbolTranslator
{
    void registerSymbol(String symbol, int code);

    int translateToCode(String symbol);

    String translateToSymbol(int code);

    int[] translateInput(String input);

    String translateOutput(int[] date);

    int[] trimData(int[] data);
}
