package ru.demmax93.sweater.domain.enums;

public enum TypeOfSubscription {
    SUBSCRIPTIONS("subscriptions"),
    SUBSCRIBERS("subscribers");

    private final String type;

    TypeOfSubscription(String value) {
        this.type = value;
    }

    public String getType() {
        return type;
    }

    public boolean equals(String value) {
        return this.type.equals(value);
    }
}
