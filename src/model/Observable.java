package model;

import view.Observer;

public interface Observable {
    void setObserver(Observer observer);
    void notifyObserver();
}
