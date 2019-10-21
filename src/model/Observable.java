package model;

import view.Observer;

/**
 * The Observable interface of the observer pattern (which is normally implemented by a Model).
 * Contrary to a normal observer pattern, The Observable object is only observed by one other object (1:1 ratio of Observers and Observables).
 * This is because this project is small and does not require multiple observers.
 * @author frank liu
 */
interface Observable {

    /**
     * Notify the Observer of this Observable Object.
     * This should only be called after changing the data in this Observable Object.
     */
    void notifyObserver();

    /**
     * Get the Observer attached this Observable Object.
     *
     * @return The Observer
     */
    Observer getObserver();

    /**
     * Set the Observer to notify whenever notifyObserver is called.
     *
     * @param observer The new observer
     */
    void setObserver(Observer observer);
}
