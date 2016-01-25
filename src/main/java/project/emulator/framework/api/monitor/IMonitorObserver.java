package project.emulator.framework.api.monitor;

import project.emulator.framework.api.monitor.CpuMonitorMessageSender;
import project.emulator.framework.api.monitor.MemoryMonitorMessageSender;

/**
 * Created by Dy.Zhao on 2016/1/24 0024.
 */
public interface IMonitorObserver
{
    void onDataLoaded(MemoryMonitorMessageSender messageSender);


    void onProgramLoaded(MemoryMonitorMessageSender messageSender);


    void onStart(CpuMonitorMessageSender messageSender);


    void onPause(CpuMonitorMessageSender messageSender);


    void onContinue(CpuMonitorMessageSender messageSender);


    void onStop(CpuMonitorMessageSender messageSender);


    void onOutput(String[] data, CpuMonitorMessageSender messageSender);


    void attachMonitor(IMonitor monitor);
}
