package com.github.xeranic.blacksmith.event;

public final class EventBus {

	public <L> ListenerRegistration<L> addListener(Class<Event<L>> eventClass, L listener) {
		return null;
	}
	
	public <L> boolean removeListener(Class<Event<L>> eventClass, L listener) {
		return false;
	}
	
	public void removeAllListeners() {
		
	}
	
	public void fire(Event<?> e) {
		
	}
	
	
	
}
