package ru.skypro;

import java.util.ArrayList;

//Задание3 ArrayList
public class ArrayList1 {
    public static void main(String[] args) {
        ArrayList<Integer> rooms = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            RoomNumbers room = new RoomNumbers();
            room.numbers = i;
            rooms.add(i);
        }
        //Размер списка ArrayList (количество содержащихся в нем элементов).
        System.out.println("We have " + rooms.size() + " rooms in our hotel, these are their numbers: " + rooms + ".");

        // Возвращает true, если этот список не содержит элементов.
        boolean hotelIsNotEmpty = rooms.isEmpty();
        System.out.println("Our hotel doesn't have free rooms now. Answer: " + hotelIsNotEmpty + ".");

        // Возвращает true, если этот список содержит указанный элемент.
        boolean bobWantTheSecondRoom = rooms.contains(1);
        System.out.println("Bob wants to live in the second room. Is the room free now? Answer: "
                + bobWantTheSecondRoom + ".");

        // Возвращает индекс первого выбора элемента в этом списке или -1, если список не содержит элемент.
        System.out.println("We need to find what number the fifth room is recorded in our computer program. It's "
                + rooms.indexOf(5) + ".");

        // Возвращает индекс последнего вхождения указанного элемента в этом списке или -1, если список не содержит
        // элемента.
        System.out.println("We need to find what number the tenth room is recorded in our computer program. It's "
                + rooms.lastIndexOf(10) + ".");

        // Возвращает элемент в указанной позиции в списке.
        System.out.println("The owner asked me to accommodate a secret guest in the penthouse. " +
                "In the program, the penthouse is recorded at number 19. Let's find out the room number: "
                + rooms.get(19) + ".");

        // Заменяет элемент в указанной позиции в списке указанным элементом.
        rooms.set(12, 133);
        System.out.println("The owner turned out to be superstitious and asked me to change the number 13 to 133: "
                + rooms + ".");

        // Добавляет указанный элемент в конец списка.
        boolean newRoom = rooms.add(22);
        System.out.println("The hotel completed the sixth floor, one room is ready there. Answer: " + newRoom + ".");
        System.out.println("Now our hotel has a new room №22: " + rooms + ".");

        // Вставляет указанный элемент в указанную позицию в списке. Сдвигает элемент, находящийся в данный момент
        // в этой позиции (если есть), и любые последующие элементы вправо (добавляет единицу к их индексам).
        rooms.add(20, 21);
        System.out.println("Now our hotel has a new room №21: " + rooms + ".");

        // Удаляет элемент в указанной позиции всписке. Сдвигает любые последующие элементы влево
        // (вычитает единицу из их индексов).
        System.out.println("Alice checked into room № " + rooms.remove(9) + ".");
        System.out.println("Now our hotel has next free rooms: " + rooms + ".");

        /* Сравнивает указанный объект сo списком на предмет равенства.
        Возвращает true, когда указанный объект также является списком, оба списка имеют одинаковый размер
        и все соответствующие пары элементов в двух списках равны.
        */
        ArrayList<Integer> rooms2 = new ArrayList<>();
        for (int i = 1; i <= 21; i++) {
            RoomNumbers room2 = new RoomNumbers();
            room2.numbers = i;
            rooms2.add(i);
        }
        boolean newHotel = rooms.equals(rooms2);
        System.out.println("Does the new hotel across the street have the same number of rooms as ours? Answer: "
                + newHotel + ".");

        // Добавляет все элементы указанной коллекции в конец списка в том порядке,
        // в котором они возвращаются итератором указанной коллекции.
        ArrayList<Integer> rooms3 = new ArrayList<>();
        for (int i = 23; i < 25; i++) {
            RoomNumbers room3 = new RoomNumbers();
            room3.numbers = i;
            rooms3.add(i);
        }
        boolean newRooms = rooms.addAll(rooms3);
        System.out.println("Do we have new rooms on the sixth floor? Answer: " + newRooms + ".");
        System.out.println("Now our hotel has next free rooms: " + rooms + ".");

        /* Вставляет все элементы указанной коллекции в список, начиная с указанной позиции.
        Сдвигает элемент, находящийся в данный момент в этой позиции (если есть), и любые последующие элементы вправо
        (увеличивает их индексы).
        */
        ArrayList<Integer> rooms4 = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            RoomNumbers room4 = new RoomNumbers();
            room4.numbers = i;
            rooms4.add(i);
        }
        boolean staffRoom = rooms.addAll(0, rooms4);
        System.out.println("We have a new staff room on the ground floor. Answer: " + staffRoom + ".");
        System.out.println("Now our hotel has next free rooms: " + rooms + ".");

        // Удаляет из списка все его элементы, содержащиеся в указанной коллекции.
        ArrayList<Integer> rooms5 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RoomNumbers room5 = new RoomNumbers();
            room5.numbers = i;
            rooms5.add(i);
        }
        System.out.println("We have new guests today? Answer: " + rooms.removeAll(rooms5) + ".");
        System.out.println("Now our hotel has next free rooms: " + rooms + ".");

        //Сохраняет в списке только те элементы, которые содержатся в указанной коллекции,
        //то есть, удаляет из списка все его элементы, не содержащиеся в указанной коллекции.
        ArrayList<Integer> rooms6 = new ArrayList<>();
        for (int i = 14; i < 25; i++) {
            RoomNumbers room6 = new RoomNumbers();
            room6.numbers = i;
            rooms6.add(i);
        }
        System.out.println("We have new guests today? Answer: " + rooms.retainAll(rooms6) + ".");
        System.out.println("Now our hotel has next free rooms: " + rooms + ".");

        // Удаляет все элементы из списка. Список будет пуст после возврата этого вызова.
        rooms.clear();
        System.out.println("Now our hotel hasn't free rooms. Wow!" + rooms + ".");
    }
}

