package it.pagopa.interop.performancetest.enums;

import lombok.Getter;

@Getter
public enum SignalTypeEnum {

    CREATE("CREATE", ""),
    UPDATE("UPDATE", ""),
    DELETE("DELETE", "");

    private final String title;
    private final String message;

    SignalTypeEnum(String title, String message) {

        this.title = title;
        this.message = message;
    }
}
