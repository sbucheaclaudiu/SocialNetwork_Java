package com.example.socialntwrkgui.utils.observer;

import com.example.socialntwrkgui.utils.events.Event;

public interface Observable<E extends Event>{
    void addObserver(Observer<E> e);

    void removeObserver(Observer<E> e);

    void notifyObservers(E t);
}
