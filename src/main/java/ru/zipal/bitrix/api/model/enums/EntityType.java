package ru.zipal.bitrix.api.model.enums;

public enum EntityType {
    USER("user.get"),
    ACTIVITY("crm.activity.get"),
    CONTACT("crm.contact.get"),
    LEAD("crm.lead.get"),
    COMPANY("crm.company.get");

    private final String getMethod;

    EntityType(String getMethod) {
        this.getMethod = getMethod;
    }

    public String getGetMethod() {
        return getMethod;
    }
}
