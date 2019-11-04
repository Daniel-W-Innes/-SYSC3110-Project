package model;

import view.View;

@FunctionalInterface
interface Model {
    void setView(View view);
}