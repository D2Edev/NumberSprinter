package org.home.d2e.numbersprinter.Core;

import java.util.Comparator;

/**
 * Created by druzhyni on 01.07.2015.
 */
public class NameComparator implements Comparator<Person> {
    @Override
    public int compare(Person first, Person second) {
        return first.getName().compareTo(second.getName());
    }
}
