package com.github.xeranic.blacksmith.event;

public abstract class Event<L> {

	protected abstract void dispatchEvent(L listener);
	
}
