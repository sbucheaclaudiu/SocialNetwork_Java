package com.example.socialntwrkgui.utils.observer;

import com.example.socialntwrkgui.utils.events.Event;

public interface Observer<E extends Event>{
    void update(E e);
}
