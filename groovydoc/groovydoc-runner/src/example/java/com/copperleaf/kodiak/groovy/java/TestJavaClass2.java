package com.copperleaf.kodiak.groovy.java;

/**
 * This is the `TestJavaClass` comment text. It contains <code>code snippets</code>, <b>bold text tags</b>, and
 * also **bold markdown things**.
 */
public class TestJavaClass2 {

    /**
     * This is a field comment
     */
    public transient String stringField = "";

    /**
     * This is a method which returns a string
     *
     * @return the stringField value
     */
    public String methodReturningString() {
        return stringField;
    }


    /**
     * This is the comment text
     *
     * @param a1 the a1 to process
     * @param a2 the a2 to process
     * @param a3 the a3 to process
     * @return the value to return
     */
    public String methodWithParametersReturningString(String a1, int a2, Object a3) {
        return stringField;
    }


}
