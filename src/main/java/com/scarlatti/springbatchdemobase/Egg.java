package com.scarlatti.springbatchdemobase;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 3/28/2019
 */
public class Egg {
    private boolean cooked;
    private boolean willFailParse;

    public Egg() {
    }

    public Egg(boolean willFailParse) {
        this.willFailParse = willFailParse;
    }

    public boolean getCooked() {
        return cooked;
    }

    public void setCooked(boolean cooked) {
        this.cooked = cooked;
    }

    public boolean getWillFailParse() {
        return willFailParse;
    }

    public void setWillFailParse(boolean willFailParse) {
        this.willFailParse = willFailParse;
    }
}
