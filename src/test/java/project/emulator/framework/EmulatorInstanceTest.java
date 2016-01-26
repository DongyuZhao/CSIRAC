package project.emulator.framework;

import org.junit.Assert;
import org.junit.Test;
import project.csirac.core.Bootstrap;
import project.emulator.framework.api.debugger.*;
import project.emulator.framework.api.monitor.*;

/**
 * Created by Dy.Zhao on 2016/1/26 0026.
 */
public class EmulatorInstanceTest
{

    IMonitorObserver monitorObserver = new IMonitorObserver()
    {
        @Override
        public void onDataLoaded(MemoryMonitorMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
        }

        @Override
        public void onProgramLoaded(MemoryMonitorMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
        }

        @Override
        public void onStart(CpuMonitorMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
        }

        @Override
        public void onPause(CpuMonitorMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
        }

        @Override
        public void onContinue(CpuMonitorMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
        }

        @Override
        public void onStop(CpuMonitorMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
        }

        @Override
        public void onOutput(String[] data, CpuMonitorMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
        }

        @Override
        public void attachMonitor(IMonitor monitor)
        {

        }
    };

    IDebuggerObserver debuggerObserver = new IDebuggerObserver()
    {
        @Override
        public void onPcRegisterUpdate(int message, DebuggerMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
            //Assert.assertEquals(null, 5, message);
        }

        @Override
        public void onOpCodeUpdate(int message, DebuggerMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
        }

        @Override
        public void onRegisterUpdate(RegisterMessage message, DebuggerMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
        }

        @Override
        public void onDataMemoryUpdate(MemoryMessage message, DebuggerMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
        }

        @Override
        public void onProgramMemoryUpdate(MemoryMessage message, DebuggerMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
        }

        @Override
        public void onFrequencyChange(float message, DebuggerMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
            Assert.assertTrue(0.5 ==  message);
        }

        @Override
        public void attachDebugger(IDebugger debugger)
        {

        }
    };

    @Test
    public void testSetClock() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.monitorObserver);
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.debuggerObserver);
        Bootstrap.createEmulator("test", monitor, debugger);
        debugger.setClock("test", 0.5f);
    }

    @Test
    public void testSetPcRegister() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.monitorObserver);
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.debuggerObserver);
        Bootstrap.createEmulator("test", monitor, debugger);
        debugger.setPcRegister("test", 5);
    }

    @Test
    public void testStart() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.monitorObserver);
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.debuggerObserver);
        Bootstrap.createEmulator("test", monitor, debugger);
        monitor.start("test");
    }

    @Test
    public void testLoadData() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.monitorObserver);
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.debuggerObserver);
        Bootstrap.createEmulator("test", monitor, debugger);
        monitor.loadData("test", new String[] {"0 0 0 0"});
    }

    @Test
    public void testLoadProgram() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.monitorObserver);
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.debuggerObserver);
        Bootstrap.createEmulator("test", monitor, debugger);
        monitor.loadProgram("test", new String[] {"0 0 0 0"});
    }

    @Test
    public void testPause() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.monitorObserver);
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.debuggerObserver);
        Bootstrap.createEmulator("test", monitor, debugger);
        monitor.loadProgram("test", new String[] {"0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A"});
        monitor.loadData("test", new String[] {"0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0"});
        monitor.start("test");
        Thread.sleep(1000);
        monitor.pause("test");
        Assert.assertTrue(monitor.isPause("test"));
    }

    @Test
    public void testGo() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.monitorObserver);
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.debuggerObserver);
        Bootstrap.createEmulator("test", monitor, debugger);
        monitor.loadProgram("test", new String[] {"0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A"});
        monitor.loadData("test", new String[] {"0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0"});
        monitor.start("test");
        Thread.sleep(1000);
        monitor.pause("test");
        Thread.sleep(1000);
        monitor.go("test");
        Assert.assertFalse(monitor.isPause("test"));
    }

    @Test
    public void testStop() throws Exception
    {
        Bootstrap.registerCsiracSymbolTable();
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.monitorObserver);
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.debuggerObserver);
        Bootstrap.createEmulator("test", monitor, debugger);
        monitor.loadProgram("test", new String[] {"0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A"});
        monitor.loadData("test", new String[] {"0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0"});
        monitor.start("test");
        monitor.stop("test");
        Assert.assertFalse(monitor.isRunning("test"));
    }

    @Test
    public void testNext() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.monitorObserver);
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.debuggerObserver);
        Bootstrap.createEmulator("test", monitor, debugger);
        monitor.loadProgram("test", new String[] {"0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A"});
        monitor.loadData("test", new String[] {"0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0","0 0 0 0"});
        monitor.start("test");
        Thread.sleep(1000);
        monitor.pause("test");
        Thread.sleep(1000);
        monitor.next("test");
        Assert.assertTrue(monitor.isPause("test"));
    }
}