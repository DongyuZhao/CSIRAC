package project.csirac.models.emulator.controlunit;

import project.csirac.models.emulator.IComputeUnit;
import project.csirac.models.emulator.IDecoder;
import project.csirac.models.emulator.IExternalStorage;
import project.csirac.models.emulator.IMemory;
import project.csirac.models.emulator.parser.ICommandParser;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public interface IControlUnit {

	/**
	 * Attach compute unit this control unit will control on
	 *
	 * @param computeUnit
	 *            the compute unit
	 */
	void attachComputeUnit(IComputeUnit computeUnit);
	
	/**
	 * Attach the memory to the control unit
	 *
	 * @param memory
	 */
	void attachMemory(IMemory memory);

	/**
	 * Attach the decoder to the control unit
	 *
	 * @param decoder
	 *            the decoder
	 */
	void attachDecoder(IDecoder decoder);

	/**
	 * Attach the external storage to the control unit
	 *
	 * @param externalStorage
	 *            the external storage
	 */
	void attachExternalStorage(IExternalStorage externalStorage);
	
	/**
	 * Attach the command parser to the control unit
	 *
	 * @param commandParser
	 *            the command parser
	 */
	void attachCommandParser(ICommandParser commandParser);

	/**
	 * Upload program to the emulator
	 *
	 * @param sessionId
	 *            the session id of the program will the program be assign to
	 * @param program
	 *            the program
	 */
	void loadProgramToMemory(String sessionId, String[] program);
	
	/**
	 * Start executing the program assigned to the session
	 *
	 * @param sessionId
	 *            the session id
	 */
	void startExecuting(String sessionId);

	/**
	 * Pause the executing of the program
	 *
	 * @param sessionId
	 *            the session id of the program will be pause executing
	 */
	void pauseExecuting(String sessionId);

	/**
	 * Executing the next instruction of the program
	 *
	 * @param sessionId
	 *            the session id of the program will be continue executing
	 */
	void nextInstruction(String sessionId);

	/**
	 * Continue Executing the program
	 *
	 * @param sessionId
	 *            the session id of the program will be continue executing
	 */
	void continueExecuting(String sessionId);

	/**
	 * Stop executing the program assigned to the session
	 *
	 * @param sessionId
	 *            the session id
	 */
	void stopExecuting(String sessionId);

	/**
	 * Peek the instruction this simulator supported
	 */
	String[] peekSupportedInstructions();

	/**
	 * Set the frequency for the session
	 *
	 * @param sessionId
	 *            the session id
	 * @param frequency
	 *            new frequency
	 */
	void setClockFrequency(String sessionId, float frequency);
}
