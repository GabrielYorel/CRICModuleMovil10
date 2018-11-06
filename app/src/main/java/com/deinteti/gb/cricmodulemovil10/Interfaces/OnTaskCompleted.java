package com.deinteti.gb.cricmodulemovil10.Interfaces;

/**
 * Created by desarrollo on 12/12/2017.
 */

public interface OnTaskCompleted {
    void onTaskCompleted(Object feed);

    void onTaskError(Object feed);
    void onCancelled();
}
