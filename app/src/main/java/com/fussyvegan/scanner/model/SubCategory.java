
package com.fussyvegan.scanner.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class SubCategory extends ExpandableGroup<Category> {

    public SubCategory(String title, List<Category> items) {
        super(title, items);
    }
}
