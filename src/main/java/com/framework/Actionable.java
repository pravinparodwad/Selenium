package com.framework;

public interface Actionable {
    public abstract void validateObject(WebAutomator automator, String... sParams);
    public abstract void performAction();
}
