blacksmith-event
================
Simplified Java Library for Observer, Event/Listener, Sub/Pub, Signal/Slot, and EventBus

[![Build Status](https://travis-ci.org/xeranic/blacksmith-event.svg?branch=master)](https://travis-ci.org/xeranic/blacksmith-event) [![Coverage Status](https://img.shields.io/coveralls/xeranic/blacksmith-event.svg)](https://coveralls.io/r/xeranic/blacksmith-event?branch=master)

What's wrong with Java Observer?
--------------------------------
[TODO]


Basic Event/Listener/EventSource
--------------------------------

```java
// define your own event handling interface, don't need to extends from other interface
public interface ValueChangedListener {
    void valueChanged(String oldValue, String newValue);
}

// define your own event class
public class ValueChanged extends Event<ValueChangedListener> {
    private final String oldValue;
    private final String newValue;
    
    public ValueChanged(String oldValue, String newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    
    @Override
    protected void dispatchEvent(ValueChangedListener listener) {
        listener.valueChanged(oldValue, newValue);
    }
}

// define your domain object that need to emit event
public class ValueField {

    // add one event source for each event you want to emit.
    private final EventSource<ValueChanged, ValueChangedListener> changed = new EventSource<>();
    
    private String value = "";
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String newValue) {
        String oldValue = this.value;
        changed.fire(new ValueChanged(oldValue, newValue));
    }
    
    public ListenerRegistration<ValueChangedListener> onValueChanged(ValueChangedListener listener) {
        return changed.on(listener);
    }
}

public class ValueFieldTest {
    public void test() {
        ValueField valueField = new ValueField();
        valueField.setValue("OLD");
        valueField.onValueChanged(new ValueChangedListener() {
            public void valueChanged(String oldValue, String newValue) {
                System.out.println(oldValue + "->" + newValue);
            }
        });
        valueField.setValue("NEW");
    }
}
```

How to use EventBus?
--------------------
