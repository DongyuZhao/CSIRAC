package project.emulator.framework.api.monitor;

import org.junit.Assert;
import org.junit.Test;
import project.csirac.core.Bootstrap;
import project.emulator.framework.api.debugger.Debugger;

import static org.junit.Assert.*;

/**
 * Created by Dy.Zhao on 2016/1/26 0026.
 */
public class MonitorTest
{
    IMonitorObserver observer = new IMonitorObserver()
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

    @Test
    public void testOnDataLoaded() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.observer);
        monitor.onDataLoaded(new MemoryMonitorMessageSender("test"));
    }

    @Test
    public void testOnProgramLoaded() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.observer);
        monitor.onProgramLoaded(new MemoryMonitorMessageSender("test"));
    }

    @Test
    public void testOnStart() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.observer);
        monitor.onStart(new CpuMonitorMessageSender("test")
        {
            @Override
            public void attachMonitor(IMonitor monitor)
            {
                super.attachMonitor(monitor);
            }
        });
    }

    @Test
    public void testOnPause() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.observer);
        monitor.onPause(new CpuMonitorMessageSender("test")
        {
            @Override
            public void attachMonitor(IMonitor monitor)
            {
                super.attachMonitor(monitor);
            }
        });
    }

    @Test
    public void testOnContinue() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.observer);
        monitor.onContinue(new CpuMonitorMessageSender("test")
        {
            @Override
            public void attachMonitor(IMonitor monitor)
            {
                super.attachMonitor(monitor);
            }
        });
    }

    @Test
    public void testOnStop() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.observer);
        monitor.onStop(new CpuMonitorMessageSender("test")
        {
            @Override
            public void attachMonitor(IMonitor monitor)
            {
                super.attachMonitor(monitor);
            }
        });
    }

    @Test
    public void testLoadData() throws Exception
    {
        Bootstrap.registerCsiracSymbolTable();
        Monitor monitor = new Monitor();
        Bootstrap.createEmulator("test", monitor, new Debugger());
        monitor.loadData("test", new String[] {"1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1"});
    }

    @Test
    public void testLoadProgram() throws Exception
    {
        Bootstrap.registerCsiracSymbolTable();
        Monitor monitor = new Monitor();
        Bootstrap.createEmulator("test", monitor, new Debugger());
        monitor.loadData("test", new String[] {"0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A"});
    }

    @Test
    public void testStart() throws Exception
    {
        Bootstrap.registerCsiracSymbolTable();
        Monitor monitor = new Monitor();
        Bootstrap.createEmulator("test", monitor, new Debugger());
        monitor.loadProgram("test", new String[] {"0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A"});
        monitor.loadData("test", new String[] {"1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1"});
        monitor.start("test");
    }

    @Test
    public void testPause() throws Exception
    {
        Bootstrap.registerCsiracSymbolTable();
        Monitor monitor = new Monitor();
        Bootstrap.createEmulator("test", monitor, new Debugger());
        monitor.loadProgram("test", new String[] {"0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A"});
        monitor.loadData("test", new String[] {"1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1"});
        monitor.start("test");
        monitor.pause("test");
    }

    @Test
    public void testNext() throws Exception
    {
        Bootstrap.registerCsiracSymbolTable();
        Monitor monitor = new Monitor();
        Bootstrap.createEmulator("test", monitor, new Debugger());
        monitor.loadProgram("test", new String[] {"0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A"});
        monitor.loadData("test", new String[] {"1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1"});
        monitor.start("test");
        monitor.pause("test");
        monitor.next("test");
    }

    @Test
    public void testGo() throws Exception
    {
        Bootstrap.registerCsiracSymbolTable();
        Monitor monitor = new Monitor();
        Bootstrap.createEmulator("test", monitor, new Debugger());
        monitor.loadProgram("test", new String[] {"0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A"});
        monitor.loadData("test", new String[] {"1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1"});
        monitor.start("test");
        monitor.pause("test");
        monitor.go("test");
    }

    @Test
    public void testStop() throws Exception
    {
        Bootstrap.registerCsiracSymbolTable();
        Monitor monitor = new Monitor();
        Bootstrap.createEmulator("test", monitor, new Debugger());
        monitor.loadProgram("test", new String[] {"0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A","0 0 M A"});
        monitor.loadData("test", new String[] {"1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1","1 1 1 1"});
        monitor.start("test");
        monitor.pause("test");
        monitor.go("test");
        monitor.stop("test");
    }

    @Test
    public void testAttachObserver() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.observer);
        Assert.assertTrue(null, monitor.isObserverAttached(this.observer));
    }

    @Test
    public void testIsObserverAttached() throws Exception
    {
        Monitor monitor = new Monitor();
        monitor.attachObserver(this.observer);
        Assert.assertTrue(null, monitor.isObserverAttached(this.observer));
    }

    @Test
    public void testAddNewSession() throws Exception
    {
        Monitor monitor = new Monitor();
        Bootstrap.createEmulator("test", monitor, new Debugger());
        Assert.assertTrue(monitor.sessionExists("test"));
    }

    @Test
    public void testSessionExists() throws Exception
    {
        Monitor monitor = new Monitor();
        Bootstrap.createEmulator("test", monitor, new Debugger());
        Assert.assertTrue(monitor.sessionExists("test"));
    }

    @Test
    public void testRemoveSession() throws Exception
    {
        Monitor monitor = new Monitor();
        Bootstrap.createEmulator("test", monitor, new Debugger());
        monitor.removeSession("test");
        Assert.assertFalse(monitor.sessionExists("test"));
    }
}