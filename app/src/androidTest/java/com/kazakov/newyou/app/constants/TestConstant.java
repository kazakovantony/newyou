package com.kazakov.newyou.app.constants;

public enum TestConstant {
    TEST_DB_SUFFIX("_test");
    private final String value;
    TestConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
