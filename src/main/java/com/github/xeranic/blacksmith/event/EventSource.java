package com.github.xeranic.blacksmith.event;

public final class EventSource<E extends Event<L>, L> {

	public static final int DEFAULT_PRIORITY = 100;

	public ListenerRegistration<L> addListener(L listener) {
		return addListener(listener, false);
	}

	public ListenerRegistration<L> addListener(L listener, boolean once) {
		return addListener(listener, once, DEFAULT_PRIORITY);
	}

	public ListenerRegistration<L> addListener(L listener, boolean once,
			int priority) {
		return null;
	}
	
	public ListenerRegistration<L> on(L listener) {
		return addListener(listener);
	}
	
	public ListenerRegistration<L> once(L listener) {
		return addListener(listener, true);
	}
	
	public boolean removeListener(L listener) {
		return false;
	}
	
	public void removeAllListeners() {
		
	}
	
	public void fire(E event) {
		
	}
	
}
