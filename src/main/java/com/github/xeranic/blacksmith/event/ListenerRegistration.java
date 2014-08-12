package com.github.xeranic.blacksmith.event;

public final class ListenerRegistration<L> {

	private final EventSource<?, L> eventSource;
	private final L listener;

	public ListenerRegistration(EventSource<?, L> eventSource, L listener) {
		this.eventSource = eventSource;
		this.listener = listener;
	}
	
	public EventSource<?, L> getEventSource() {
		return eventSource;
	}
	
	public L getListener() {
		return listener;
	}

	public boolean removeListener() {
		return this.eventSource.removeListener(listener);
	}

}
