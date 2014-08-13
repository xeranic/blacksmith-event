package com.github.xeranic.blacksmith.event;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class EventSourceTest {

    interface ChangedListener {
        
        void changed(String what);
        
    }
    
    static class Changed extends Event<ChangedListener> {

        private final String what;

        public Changed(String what) {
            this.what = what;
        }

        @Override
        protected void dispatchEvent(ChangedListener listener) {
            listener.changed(what);
        }
        
    }
    
    
    private EventSource<Changed, ChangedListener> eventSource;
    
    @Before
    public void setUp() {
        eventSource = new EventSource<>();
    }
    
	@Test
	public void testOn() {
	    ChangedListener listener = mock(ChangedListener.class);
	    eventSource.on(listener);
	    verify(listener, never()).changed((String) any());
	    
        eventSource.fire(new Changed("1"));
        verify(listener).changed("1");
        
        eventSource.fire(new Changed("2"));
        verify(listener).changed("2");
	}
	
	@Test
    public void testOnce() {
        ChangedListener listener = mock(ChangedListener.class);
        eventSource.once(listener);
        verify(listener, never()).changed((String) any());
        
        eventSource.fire(new Changed("1"));
        verify(listener).changed("1");
        
        eventSource.fire(new Changed("2"));
        verify(listener, never()).changed("2");
    }
	
	@Test
	public void testPriority() {
	    ChangedListener listenerL = mock(ChangedListener.class);
	    ChangedListener listenerN = mock(ChangedListener.class);
	    ChangedListener listenerH = mock(ChangedListener.class);
        
	    eventSource.addListener(listenerN);
	    eventSource.addListener(listenerL, false, EventSource.PRIORITY_LOW);
	    eventSource.addListener(listenerH, false, EventSource.PRIORITY_HIGH);
	    
        String what = "something";
        eventSource.fire(new Changed(what));
        
        InOrder order = inOrder(listenerL, listenerN, listenerH);
        order.verify(listenerH).changed(what);
        order.verify(listenerN).changed(what);
        order.verify(listenerL).changed(what);
	}
	
}
