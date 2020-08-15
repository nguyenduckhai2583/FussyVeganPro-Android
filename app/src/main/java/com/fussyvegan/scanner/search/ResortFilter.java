package com.fussyvegan.scanner.search;

public class ResortFilter {
    public String filter;
    public boolean isClear;

    public ResortFilter(String filter,boolean isClear) {
        this.filter = filter;
        this.isClear = isClear;
    }

    public String getFilter() {
        return filter;
    }

    public boolean isClear() {
        return isClear;
    }
}
