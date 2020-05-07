package org.smirl.julisha.core.data.rdc;

import java.util.ArrayList;
import java.util.List;

public class Places<E> extends ArrayList<E> {

    public List<String> toSimpleList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size(); i++)
            list.add(((Place) (get(i))).nom);
        return list;
    }

    @Override
    public String[] toArray() {
        String[] list = new String[size()];
        for (int i = 0; i < size(); i++)
            list[i] = ((Place) get(i)).nom;
        return list;
    }
}
