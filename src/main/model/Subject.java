package model;

import java.util.Set;
import java.util.HashSet;

/**
 * Represents an subject (Observable) in the Observer Design Pattern
 */
public abstract class Subject {

    protected Set<Observer> observers;

    /**
     * Constructor creates an empty set of observers
     * (there's no point of adding the same observer twice)
     */

    public Subject() {
        observers = new HashSet<Observer>();
    }

    /**
     * Adds an observer to the list of observers
     * 
     * @param o the observer to be added
     */
    public void addObserver(Observer o) {
        observers.add(o);
    }

    /**
     * Notifies the observers when a change occurs
     * in the status of this Observable.
     */
    public abstract void notifyObservers();
}
