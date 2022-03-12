package ru.skypro;

//Задание2 LinkedList
public class LinkedList1 {

    public static void main(String[] args) {
        LinkedList<String> fruit = new LinkedList<>();
        //Добавляет указанный элемент.
        fruit.add("red apple");
        fruit.add("pear");
        fruit.add("banana");
        fruit.add("plum");
        fruit.add("pineapple");
        fruit.add("orange");
        fruit.add("tangerine");
        fruit.add("lemon");
        fruit.add("apricot");
        fruit.add("peach");

        // Вывод списка на экран.
        System.out.println("We have a fruit basket: " + fruit + ".");

        // Возвращение первого и последнего элементов в списке.
        String firstFruit = fruit.getFirst();
        String lastFruit = fruit.getLast();
        System.out.println("The first fruit is " + firstFruit + ". The last fruit is " + lastFruit + ".");

        // Удаление и возврат первого элемента из списка.
        String eatFruit1 = fruit.removeFirst();
        String eatFruit2 = fruit.removeLast();
        System.out.println("We ate a " + eatFruit1 + " and a " + eatFruit2 + ".");
        System.out.println("Fruit basket now: " + fruit + ".");

        // Вставка указанного элемента в начало списка.
        fruit.addFirst("grapefruit");
        fruit.addLast("lychee");
        System.out.println("John brought " + fruit.get(0) + " and " + fruit.get(9) + ".");
        System.out.println("Fruit basket now: " + fruit + ".");

        // Возвращает true, если этот список содержит указанный элемент.
        System.out.println("We have a plum. Answer: " + fruit.contains("plum") + ".");

        // Возвращает количество элементов в списке.
        System.out.println("How many fruits are in our basket now? " + fruit.size + ".");

        // Удаляет первое вхождение указанного элемента из списка, если он присутствует.
        boolean eatBanana = fruit.remove("banana");
        boolean doNotEatCoconut = fruit.remove("coconut");
        System.out.println("We eat a banana. Answer: " + eatBanana + ". We have a coconut. Answer: " + doNotEatCoconut
                + ".");

        /* Вставляет все элементы указанной коллекции в список, начиная с указанной позиции.
        Сдвигает элемент, находящийся в данный момент в этой позиции (если есть),
        и любые последующие элементы вправо (увеличивает их индексы). */
        LinkedList<String> fruit3 = new LinkedList<>();
        fruit3.add("pomegranate");
        fruit3.add("kiwi");
        boolean fruit2 = fruit.addAll(0, fruit3);
        System.out.println("Fruit basket after pomegranate and kiwi have been placed in it: " + fruit + ". " +
                "Do we have new fruits? Answer: " + fruit2 + ".");

        // Добавляет все элементы указанной коллекции в конец списка в том порядке,
        // в котором они возвращаются итератором указанной коллекции.
        LinkedList<String> fruit4 = new LinkedList<>();
        fruit4.add("dragon fruit");
        boolean fruit5 = fruit.addAll(fruit4);
        System.out.println("Fruit basket after dragon fruit has been placed in it: " + fruit + ". " +
                "Do we have new fruits? Answer: " + fruit5 + ".");

        /* Операции позиционного доступа
        Возвращает элемент в указанной позиции в списке. Параметры: index – индекс возвращаемого элемента.
        */
        String getMeFirstFruit = fruit.get(4);
        System.out.println("The firth fruit is a " + getMeFirstFruit + ".");

        // Заменяет элемент в указанной позиции в списке указанным элементом.
        System.out.println("Fruit basket before apple replacement: " + fruit + ".");
        String appleReplacement = fruit.set(4, "green apple");
        System.out.println("Fruit basket after apple replacement: " + fruit + ".");

        // Вставляет указанный элемент в указанную позицию в списке.
        fruit.add(3, "guava");
        System.out.println("Fruit basket after guava has been placed in it: " + fruit + ".");

        // Удаляет элемент в указанной позиции в списке. Сдвигает любые последующие элементы влево
        // (вычитает единицу из их индексов). Возвращает элемент, который был удален из списка.
        fruit.remove(4);
        System.out.println("Fruit basket after grapefruit has been removed: " + fruit + ".");

        // Сообщает, является ли аргумент индексом существующего элемента.
        boolean fruit6 = ((LinkedList<String>) fruit).isElementIndex(1);
        System.out.println(fruit6);

        // Сообщает, является ли аргумент индексом допустимой позиции для итератора или операции добавления.
        boolean fruit7 = ((LinkedList<String>) fruit).isPositionIndex(1);
        System.out.println(fruit7);

        /* Операции поиска.
        Возвращает индекс первого появления указанного элемента в списке или -1, если список не содержит элемента.
        */
        System.out.println("What is the place of the pear? " + fruit.indexOf("pear"));
        System.out.println("What is the place of the pineapple? " + fruit.lastIndexOf("pineapple"));

        /* Queue operations(очередь операций) */
        // Извлекает, но не удаляет заголовок (первый элемент) списка.
        System.out.println("The first element of our list is " + fruit.peek() + ".");
        // Извлекает, но не удаляет заголовок (первый элемент) списка.
        System.out.println("The first element of our list is " + fruit.element() + ".");
        System.out.println("Fruit basket now: " + fruit + ".");

        // Извлекает и удаляет заголовок (первый элемент) списка.
        System.out.println("The first element of our list is " + fruit.poll() + ".");
        System.out.println("Fruit basket now: " + fruit + ".");

        // Извлекает и удаляет заголовок (первый элемент) списка.
        System.out.println("The first element of our list is " + fruit.remove() + ".");
        System.out.println("Fruit basket now: " + fruit + ".");

        // Добавляет указанный элемент в качестве последнего элемента списка.
        fruit.offer("golden apple");
        System.out.println("Fruit basket now: " + fruit + ".");

        /* Deque operations(операция удаления из очереди) */
        // Вставляет указанный элемент в начало списка.
        fruit.offerFirst("mango");
        // Вставляет указанный элемент в конец списка.
        fruit.offerLast("feijoa");
        System.out.println("Fruit basket now: " + fruit + ".");

        // Извлекает, но не удаляет первый элемент списка или возвращает null, если список пуст.
        System.out.println("Now our first fruit is " + fruit.peekFirst() + ".");
        // Извлекает, но не удаляет, последний элемент списка или возвращает null, если список пуст.
        System.out.println("Now our last fruit is " + fruit.peekLast() + ".");

        // Извлекает и удаляет первый и последний элементы списка или возвращает null, если список пуст.
        System.out.println("We ate these fruit: " + fruit.pollFirst() + " and " + fruit.pollLast() + ".");

        // Помещает элемент в стек, представленный списком. Вставляет элемент в начале этого списка.
        fruit.push("apple quince");
        System.out.println("Fruit basket now: " + fruit + ".");
        // Извлекает элемент из стека, представленного списком. Удаляет и возвращает первый элемент списка.
        fruit.pop();
        System.out.println("Fruit basket now: " + fruit + ".");

        // Удаляет первое вхождение указанного элемента в списке (при обходе списка от начала до конца).
        boolean aboutDragonFruit = fruit.removeFirstOccurrence("dragon fruit");
        // Удаляет последнее вхождение указанного элемента в списке (при обходе списка от начала до конца).
        boolean aboutOrange = fruit.removeLastOccurrence("orange");
        System.out.println("Fruit basket now: " + fruit + ".");
        System.out.println("We have a dragon fruit. Answer: " + aboutDragonFruit + ". " +
                "We have an orange. Answer: " + aboutOrange + ".");

        // Удаляет все элементы из списка. Список будет пуст после возврата этого вызова.
        fruit.clear();
        System.out.println("Fruit basket now: " + fruit + ". Our basket is empty. " +
                "We have eat everything in the evening.");
    }
}
