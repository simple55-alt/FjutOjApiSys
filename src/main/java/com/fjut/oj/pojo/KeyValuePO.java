package com.fjut.oj.pojo;

/**
 * @author axiang [20190829]
 */
public class KeyValuePO {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyValuePO{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
