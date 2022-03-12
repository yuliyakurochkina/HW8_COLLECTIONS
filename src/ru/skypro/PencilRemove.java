package ru.skypro;

import java.util.ArrayList;

public class PencilRemove {
    public void removePencils (ArrayList<String> pencils) {
        pencils.remove(2);
        System.out.println(pencils.size());
        for(String str1: pencils) {
            System.out.println(str1);
        }
    }
}
