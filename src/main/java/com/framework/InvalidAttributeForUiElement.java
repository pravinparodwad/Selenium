package com.framework;

import lombok.Getter;

public class InvalidAttributeForUiElement extends Exception {
    @Getter
    private final String attr;
    private final UiElement element;
    private static final long serialVersionUID = 1L;

    public InvalidAttributeForUiElement(UiElement element, String attr) {
        super(String.format(
                "%s is not a valid attribute for %s",
                attr,
                element
        ));
        this.element = element;
        this.attr = attr;
    }

    /**
     * @return the web element that threw the exception
     */
    public UiElement getWebElement() {
        return element;
    }
}
