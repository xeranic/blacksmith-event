package com.github.xeranic.blacksmith.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class EventSource<E extends Event<L>, L> {

    public static final int PRIORITY_LOWEST = 100;

    public static final int PRIORITY_LOWER = 200;

    public static final int PRIORITY_LOW = 300;

    public static final int PRIORITY_NORMAL = 400;

    public static final int PRIORITY_HIGH = 500;

    public static final int PRIORITY_HIGHER = 600;

    public static final int PRIORITY_HIGHEST = 700;

    private static class Item<L> {
        private int priority;
        private boolean once;
        private L listener;
    }

    private List<Item<L>> items;

    public ListenerRegistration<L> addListener(L listener) {
        return addListener(listener, false);
    }

    public ListenerRegistration<L> addListener(L listener, boolean once) {
        return addListener(listener, once, PRIORITY_NORMAL);
    }

    public synchronized ListenerRegistration<L> addListener(L listener,
            boolean once, int priority) {
        if (items == null) {
            items = new ArrayList<>();
        }
        assert (listenerDoesNotExist(listener));
        int index = items.size();
        for (int i = 0, len = items.size(); i < len; i++) {
            Item<L> item = items.get(i);
            if (item.priority < priority) {
                index = i;
                break;
            }
        }
        Item<L> item = new Item<>();
        item.priority = priority;
        item.once = once;
        item.listener = listener;
        items.add(index, item);
        return new ListenerRegistration<L>(this, listener);
    }

    private boolean listenerDoesNotExist(L listener) {
        for (int i = 0, len = items.size(); i < len; i++) {
            Item<L> item = items.get(i);
            if (item.listener == listener) {
                return false;
            }
        }
        return true;
    }

    public ListenerRegistration<L> on(L listener) {
        return addListener(listener);
    }

    public ListenerRegistration<L> once(L listener) {
        return addListener(listener, true);
    }

    public synchronized boolean removeListener(L listener) {
        if (items == null) {
            return false;
        }
        for (int i = 0, len = items.size(); i < len; i++) {
            Item<L> item = items.get(i);
            if (item.listener == listener) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    public synchronized void removeAllListeners() {
        if (items != null) {
            items.clear();
        }
    }

    public synchronized void fire(E event) {
        if (items == null) {
            return;
        }
        Iterator<Item<L>> iter = items.iterator();
        while (iter.hasNext()) {
            Item<L> item = iter.next();
            event.dispatchEvent(item.listener);
            if (item.once) {
                iter.remove();
            }
        }
    }

}
