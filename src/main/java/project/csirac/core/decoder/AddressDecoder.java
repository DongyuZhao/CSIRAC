package project.csirac.core.decoder;

import project.csirac.core.CsiracBootstrap;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */

public class AddressDecoder {

    /**
     * Give the address according the OpCode and the first two section of the instruction
     *
     * @param opCode the OpCode
     * @param section0 the first section
     * @param section1 the second section
     * @return the decoded address
     */
    static int determineAddress(int opCode, int section0, int section1) {
        if (section0 < 0 || section1 < 0 || section0 >= CsiracBootstrap.getInnerConfig().unitCount() || section1 >= CsiracBootstrap.getInnerConfig().cellPerUnit()) {
            return -1;
        }
        int address = -1;
        if (opCode == CsiracBootstrap.getSymbolTranslator().translateToCode("M")) {
            address = section0 * CsiracBootstrap.getInnerConfig().cellPerUnit() + section1;
        } else if (opCode == CsiracBootstrap.getSymbolTranslator().translateToCode("D")) {
            address = section1 % 16;
        } else if (opCode == CsiracBootstrap.getSymbolTranslator().translateToCode("A") || opCode == CsiracBootstrap.getSymbolTranslator().translateToCode("B") || opCode == CsiracBootstrap.getSymbolTranslator().translateToCode("C")) {
            address = opCode;
        }
        // TODO:: Add for further instructions
        return address;
    }
}
