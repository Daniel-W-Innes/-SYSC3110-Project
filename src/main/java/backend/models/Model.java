package backend.models;

import frontend.View;

interface Model {
    void addView(View view);

    void removeView(View view);
}
