package model;

import view.Observer;

interface Observable {
    void setObserver(Observer observer);
    void notifyObserver();
}
