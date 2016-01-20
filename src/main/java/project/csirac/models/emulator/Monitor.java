package project.csirac.models.emulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import project.csirac.CsiracApplication;
import project.csirac.models.emulator.controlunit.IControlUnit;
import project.csirac.viewmodels.emulator.SettingViewModel;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public class Monitor implements IMonitor {
	private Vector<IViewObserver> _viewObserverList = new Vector<>();

	private Vector<IStatusObserver> _statusObserverList = new Vector<>();

	private Vector<ISettingObserver> _settingObeserverList = new Vector<>();

	public Monitor() {
	}

	private String generateFakeResult(int length) {

		String result = "";
		for (int j = 0; j < length; j++) {
			int rand = (int) (Math.random() * 10);
			result += rand % 2;
		}
		return result;
	}

	private List<String> generateFakeListResult(int lengthPerItem) {
		List<String> result = new ArrayList<>();
		int length = (int) (Math.random() * 20 + 20);
		for (int i = 0; i < length; i++) {
			result.add(generateFakeResult(lengthPerItem));
		}
		return result;
	}

	/**
	 * Attach the Control Unit this monitor will monitor on.
	 *
	 * @param controlUnit
	 *            the Control Unit this monitor will monitor on
	 */
	@Override
	public void attachControlUnit(IControlUnit controlUnit) {

	}

	/**
	 * Attach the Debugger this monitor will monitor on.
	 *
	 * @param debugger
	 *            the Control Unit this debugger will monitor on
	 */
	@Override
	public void attachDebugger(IDebugger debugger) {

	}

	/**
	 * Attach the observer which is observing this monitor.
	 *
	 * @param observer
	 *            the observer which is observing this monitor
	 */
	@Override
	public void attachViewObserver(IViewObserver observer) {
		if (!this.isViewObserverAttached(observer)) {
			this._viewObserverList.add(observer);
		}
	}

	/**
	 * Attach the observer which is observing this monitor.
	 *
	 * @param observer
	 *            the observer which is observing this monitor
	 */
	@Override
	public void attachStatusObserver(IStatusObserver observer) {
		if (!this.isStatusObserverAttached(observer)) {
			this._statusObserverList.add(observer);
		}
	}

	/**
	 * Attach the observer which is observing this monitor.
	 *
	 * @param observer
	 *            the observer which is observing this monitor
	 */
	@Override
	public void attachSettingObserver(ISettingObserver observer) {
		if (!this.isSettingObserverAttached(observer)) {
			this._settingObeserverList.add(observer);
		}
	}

	/**
	 * Continue Executing the program
	 *
	 * @param sessionId
	 *            the session id of the program will be continue executing
	 */
	@Override
	public void continueExecuting(String sessionId) {
		for (IStatusObserver observer : this._statusObserverList) {
			observer.pushRunning(sessionId);
		}
	}

	// @Override
	// public void Init(IDecoder decoder, IMemory memory, IComputeUnit
	// computeUnit)
	// {
	//
	// }

	/**
	 * Upload program to the emulator
	 *
	 * @param sessionId
	 *            the session id of the program will the program be assign to
	 * @param program
	 */
	@Override
	public void loadProgram(String sessionId, String[] program) {
		for (IStatusObserver observer : this._statusObserverList) {
			observer.pushReady(sessionId);
		}
	}

	/**
	 * Executing the next instruction of the program
	 *
	 * @param sessionId
	 *            the session id of the program will be continue executing
	 */
	@Override
	public void nextInstruction(String sessionId) {
		for (IStatusObserver observer : this._statusObserverList) {
			observer.pushPause(sessionId);
		}
	}

	/**
	 * Pause the executing of the program
	 *
	 * @param sessionId
	 *            the session id of the program will be pause executing
	 */
	@Override
	public void pauseExecuting(String sessionId) {
		for (IStatusObserver observer : this._statusObserverList) {
			observer.pushPause(sessionId);
		}
	}

	// /**
	// * Peek the pc register of the monitor
	// *
	// * @param sessionId
	// * the session id of the pc register should be peeked
	// */
	// @Override
	// public int peekPcRegister(String sessionId)
	// {
	// return 0;
	// }

	/**
	 * Peek the instruction this simulator supported
	 */
	@Override
	public String[] peekSupportedInstructions() {
		String[] result = {};
		return generateFakeListResult(20).toArray(result);
	}

	/**
	 * Set the frequency for the session
	 *
	 * @param sessionId
	 *            the session id
	 * @param frequency
	 */
	@Override
	public void setClockFrequency(String sessionId, float frequency) {
		Settings settings = new Settings();
		settings.setSessionId(sessionId);
		settings.setFrequency(frequency * 8);
		this.updateCurrentSettingView(sessionId, settings);
	}

	/**
	 * Start executing the program assigned to the session
	 *
	 * @param sessionId
	 *            the session id
	 */
	@Override
	public void startExecuting(String sessionId) {
		for (IStatusObserver observer : this._statusObserverList) {
			observer.pushRunning(sessionId);
		}
	}

	/**
	 * Stop executing the program assigned to the session
	 *
	 * @param sessionId
	 *            the session id
	 */
	@Override
	public void stopExecuting(String sessionId) {
		if (CsiracApplication.sessionExists(sessionId)) {
			for (IStatusObserver observer : this._statusObserverList) {
				observer.pushStop(sessionId);
			}
		}
	}

	/**
	 * Update the current instruction of the session
	 *
	 * @param sessionId
	 *            the session id
	 * @param instruction
	 */
	@Override
	public void updateInstructionView(String sessionId, String instruction) {
		for (IViewObserver observer : this._viewObserverList) {
			// observer.pushUpdatedInstruction(sessionId,
			// this.getCurrentInstruction(sessionId));
		}
	}

	/**
	 * Update the memory at specified address of the session
	 *
	 * @param sessionId
	 *            the session id
	 * @param address
	 *            the address
	 * @param data
	 */
	@Override
	public void updateMemoryView(String sessionId, int address, String data) {
		for (IViewObserver observer : this._viewObserverList) {
			// observer.pushUpdatedMemory(sessionId, this.getMemory(sessionId));
		}
	}

	/**
	 * Update the memory of the session
	 *
	 * @param sessionId
	 *            the session id the address
	 * @param data
	 */
	@Override
	public void updateMemoryView(String sessionId, String[] data) {
		for (IViewObserver observer : this._viewObserverList) {
			// observer.pushUpdatedMemory(sessionId, this.getMemory(sessionId));
		}
	}

	/**
	 * Update the register at specified address of the session
	 *
	 * @param sessionId
	 *            the session id
	 * @param address
	 *            the address
	 * @param data
	 */
	@Override
	public void updateRegisterView(String sessionId, int address, String data) {
		for (IViewObserver observer : this._viewObserverList) {
			// observer.pushUpdatedRegister(sessionId,
			// this.getRegister(sessionId));
		}
	}

	/**
	 * Update the register of the session
	 *
	 * @param sessionId
	 *            the session id
	 * @param data
	 */
	@Override
	public void updateRegisterView(String sessionId, String[] data) {
		for (IViewObserver observer : this._viewObserverList) {
			// observer.pushUpdatedRegister(sessionId,
			// this.getRegister(sessionId));
		}
	}

	/**
	 * Update the pc reg of the session
	 *
	 * @param sessionId
	 *            the session id
	 * @param pcReg
	 */
	@Override
	public void updatePcRegView(String sessionId, int pcReg) {
		for (IViewObserver observer : this._viewObserverList) {
			observer.pushPcRegister(sessionId, pcReg + "");
		}
	}

	/**
	 * Update the output of the session
	 *
	 * @param sessionId
	 *            the session id
	 * @param output
	 */
	@Override
	public void updateOutputView(String sessionId, String[] output) {
		for (IViewObserver observer : this._viewObserverList) {
			observer.pushOutput(sessionId, output);
		}
	}

	/**
	 * Update current setting views
	 *
	 * @param sessionId
	 *            the session id
	 * @param settings
	 *            current settings
	 */
	@Override
	public void updateCurrentSettingView(String sessionId, Settings settings) {
		for (ISettingObserver observer : this._settingObeserverList) {
			SettingViewModel model = new SettingViewModel();
			model.setSessionId(sessionId);
			model.setFrequency(settings.getFrequency());
			observer.pushCurrentSettings(sessionId, model);
		}
	}

	// /**
	// * Get the output of the session
	// *
	// * @param sessionId
	// * the session id
	// *
	// * @return the output
	// */
	// @Override
	// public String[] getOutput(String sessionId)
	// {
	// String[] result = {};
	// return generateFakeListResult(20).toArray(result);
	// }
	//
	// /**
	// * Get the memory of the session
	// *
	// * @param sessionId
	// * the session id
	// *
	// * @return the memory
	// */
	// @Override
	// public String[] getMemory(String sessionId)
	// {
	// String[] result = {};
	// return generateFakeListResult(20).toArray(result);
	// }
	//
	// /**
	// * Get the register of the session
	// *
	// * @param sessionId
	// * the session id
	// *
	// * @return the register
	// */
	// @Override
	// public String[] getRegister(String sessionId)
	// {
	// String[] result = {};
	// return generateFakeListResult(20).toArray(result);
	// }
	//
	// /**
	// * Get current instruction of the session
	// *
	// * @param sessionId
	// * the session id
	// *
	// * @return current instruction
	// */
	// @Override
	// public String getCurrentInstruction(String sessionId)
	// {
	// return generateFakeResult(20);
	// }

	/**
	 * Check if the observer is attached
	 *
	 * @param observer
	 *            the observer
	 *
	 * @return if it is attached
	 */
	@Override
	public boolean isViewObserverAttached(IViewObserver observer) {
		return this._viewObserverList.contains(observer);
	}

	/**
	 * Check if the observer is attached
	 *
	 * @param observer
	 *            the observer
	 *
	 * @return if it is attached
	 */
	@Override
	public boolean isStatusObserverAttached(IStatusObserver observer) {
		return this._statusObserverList.contains(observer);
	}

	/**
	 * Check if the observer is attached
	 *
	 * @param observer
	 *            the observer
	 *
	 * @return if it is attached
	 */
	@Override
	public boolean isSettingObserverAttached(ISettingObserver observer) {
		return this._settingObeserverList.contains(observer);
	}
}
