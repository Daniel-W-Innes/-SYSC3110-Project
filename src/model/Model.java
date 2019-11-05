package model;

import view.View;

/**
* The interface that a model must implement.
*/

@FunctionalInterface
interface Model {
    void setView(View view);
}
