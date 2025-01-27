package com.framework;

@FunctionalInterface
public interface Actionable {
    /**
     * The interface which is used to define the contract for the action class.
     * This interface has one abstract method which is {@link #performAction()}.
     * The action class should implement this interface and provide the implementation of the
     * {@link #performAction()} method. The action class will be responsible for performing the action
     * on the UI element.
     *
     * @author Pravin Parodwad
     */
    public abstract void performAction();
}
