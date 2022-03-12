package ru.skypro;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //Задание1 ArrayList
        ArrayList<String> pencils = new ArrayList<>();
        pencils.add("Красный");
        pencils.add("Оражевый");
        pencils.add("Желтый");
        pencils.add("Зеленый");
        pencils.add("Голубой");
        pencils.add("Синий");
        pencils.add("Фиолетовый");

        PencilScanner pencilScanner = new PencilScanner();
        System.out.println("Посчитаем, сколько карандашей лежит на столе: ");
        pencilScanner.scanPencils(pencils);
        System.out.println("Ага, значит, у нас " + pencils.size() + " карандашей.");

        //Операция удаления
        PencilRemove pencilRemove = new PencilRemove();
        System.out.print("Упс, похоже, кто-то забрал у нас 1 карандаш! Теперь их у нас ");
        pencilRemove.removePencils(pencils);
        System.out.println("Не хватает третьего карандаша. Какой же теперь лежит на его месте?");

        //Операция поиска
        PencilSearch pencilSearch = new PencilSearch();
        pencilSearch.pencilSearch(pencils);

        //Задание1 LinkedList
        LinkedList<String> pens = new LinkedList<>();
        pens.add("Красная");
        pens.add("Синяя");
        pens.add("Зеленая");
        pens.add("Черная");

        System.out.println("Мама купила новые ручки, давай посмотрим, какие: " + pens);

        //Операция поиска
        String forthPen = pens.get(3);
        System.out.println("Брат попросил черную ручку, нужно найти ее: " + forthPen);

        //Операция удаления
        pens.remove(3);
        System.out.println("Отдаем ручку брату, какие же у нас остались: " + pens);
    }
}
