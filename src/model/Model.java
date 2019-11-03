package model;

import view.View;

public interface Model {
    void addView(View view);

    void removeView(View view);
}