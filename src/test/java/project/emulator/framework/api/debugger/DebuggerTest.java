package project.emulator.framework.api.debugger;

import org.junit.Assert;
import org.junit.Test;
import project.csirac.core.Bootstrap;
import project.emulator.framework.api.monitor.Monitor;



/**
 * Created by Dy.Zhao on 2016/1/26 0026.
 */
public class DebuggerTest
{
    IDebuggerObserver observer = new IDebuggerObserver()
    {
        @Override
        public void onPcRegisterUpdate(int message, DebuggerMessageSender messageSender)
        {
            Assert.assertEquals(null, "test", messageSender.getMessageSenderId());
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
        }

        @Override
        public void attachDebugger(IDebugger debugger)
        {

        }
    };

    @Test
    public void testOnPcRegisterUpdate() throws Exception
    {
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.observer);
        debugger.onPcRegisterUpdate(1, new DebuggerMessageSender("test")
        {
            @Override
            public String getMessageSenderId()
            {
                return super.getMessageSenderId();
            }
        });
    }

    @Test
    public void testOnOpCodeUpdate() throws Exception
    {
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.observer);
        debugger.onOpCodeUpdate(1, new DebuggerMessageSender("test")
        {
            @Override
            public String getMessageSenderId()
            {
                return super.getMessageSenderId();
            }
        });
    }

    @Test
    public void testOnRegisterUpdate() throws Exception
    {
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.observer);
        debugger.onRegisterUpdate(new RegisterMessage("A", new int[]{1, 1, 1, 1}), new DebuggerMessageSender("test")
        {
            @Override
            public String getMessageSenderId()
            {
                return super.getMessageSenderId();
            }
        });
    }

    @Test
    public void testOnDataMemoryUpdate() throws Exception
    {
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.observer);
        debugger.onDataMemoryUpdate(new MemoryMessage(1, 1, new int[]{1, 1, 1, 1}), new DebuggerMessageSender("test")
        {
            @Override
            public String getMessageSenderId()
            {
                return super.getMessageSenderId();
            }
        });
    }

    @Test
    public void testOnProgramMemoryUpdate() throws Exception
    {
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.observer);
        debugger.onProgramMemoryUpdate(new MemoryMessage(1, 1, new int[]{1, 1, 1, 1}), new DebuggerMessageSender("test")
        {
            @Override
            public String getMessageSenderId()
            {
                return super.getMessageSenderId();
            }
        });
    }

    @Test
    public void testOnFrequencyChange() throws Exception
    {
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.observer);
        debugger.onFrequencyChange(25, new DebuggerMessageSender("test")
        {
            @Override
            public String getMessageSenderId()
            {
                return super.getMessageSenderId();
            }
        });

    }


    @Test
    public void testAttachObserver() throws Exception
    {
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.observer);
        Assert.assertTrue(debugger.isObserverAttached(this.observer));
    }

    @Test
    public void testIsObserverAttached() throws Exception
    {
        Debugger debugger = new Debugger();
        debugger.attachObserver(this.observer);
        Assert.assertTrue(debugger.isObserverAttached(this.observer));
    }

    @Test
    public void testAddNewSession() throws Exception
    {
        Debugger debugger = new Debugger();
        Bootstrap.createEmulator("test", new Monitor(), debugger);
        Assert.assertTrue(debugger.sessionExists("test"));
    }

    @Test
    public void testSessionExists() throws Exception
    {
        Debugger debugger = new Debugger();
        Bootstrap.createEmulator("test", new Monitor(), debugger);
        Assert.assertTrue(debugger.sessionExists("test"));
    }

    @Test
    public void testRemoveSession() throws Exception
    {
        Debugger debugger = new Debugger();
        Bootstrap.createEmulator("test", new Monitor(), debugger);
        debugger.removeSession("test");
        Assert.assertFalse(debugger.sessionExists("test"));
    }

    @Test
    public void testSetPcRegister() throws Exception
    {
        Debugger debugger = new Debugger();
        Bootstrap.createEmulator("test", new Monitor(), debugger);
        debugger.setPcRegister("test", 100);
    }
}