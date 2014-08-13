package com.github.xeranic.blacksmith.event;

import java.util.HashMap;
import java.util.Map;

public class EventBus {

    private final Map<Class<?>, EventSource<?, ?>> eventSources = new HashMap<>();

    public final <E extends Event<L>, L> ListenerRegistration<L> addListener(
            Class<E> eventClass, L listener) {
        return addListener(eventClass, listener, false);
    }

    public final <E extends Event<L>, L> ListenerRegistration<L> addListener(
            Class<E> eventClass, L listener, boolean once) {
        return addListener(eventClass, listener, once,
                EventSource.PRIORITY_NORMAL);
    }

    public final <E extends Event<L>, L> ListenerRegistration<L> addListener(
            Class<E> eventClass, L listener, boolean once, int priority) {
        EventSource<E, L> eventSource = getEventSource(eventClass, true);
        return eventSource.addListener(listener, once, priority);
    }
    
    public final <E extends Event<L>, L> ListenerRegistration<L> on(Class<E> eventClass, L listener) {
        return addListener(eventClass, listener);
    }
    
    public final <E extends Event<L>, L> ListenerRegistration<L> once(Class<E> eventClass, L listener) {
        return addListener(eventClass, listener, true);
    }

    public final <E extends Event<L>, L> boolean removeListener(Class<E> eventClass, L listener) {
        EventSource<E, L> eventSource = getEventSource(eventClass, false);
        if (eventSource == null) {
            return false;
        }
        return eventSource.removeListener(listener);
    }
    
    public final synchronized void removeAllListeners() {
        for (EventSource<?, ?> eventSource : eventSources.values()) {
            eventSource.removeAllListeners();
        }
    }

    @SuppressWarnings("unchecked")
    public final <E extends Event<L>, L> void fire(E e) {
        EventSource<E, L> eventSource = getEventSource(e.getClass());
        if (eventSource != null) {
            eventSource.fire(e);
        }
    }

    public final <E extends Event<L>, L> EventSource<E, L> getEventSource(
            Class<E> eventClass) {
        return getEventSource(eventClass, false);
    }

    @SuppressWarnings("unchecked")
    private synchronized <E extends Event<L>, L> EventSource<E, L> getEventSource(
            Class<E> eventClass, boolean create) {
        EventSource<E, L> eventSource = (EventSource<E, L>) eventSources.get(eventClass);
        if (eventSource == null && create) {
            eventSource = new EventSource<>();
            eventSources.put(eventClass, eventSource);
        }
        return eventSource;
    }

}
