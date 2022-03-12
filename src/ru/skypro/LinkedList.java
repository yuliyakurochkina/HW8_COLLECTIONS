package ru.skypro;

import java.util.*;
import java.util.function.Consumer;

//Задание2 LinkedList
public class LinkedList<E>
        //расширяет AbstractSequentialList
        extends AbstractSequentialList<E>
        //осуществляет следующие интерфейсы
        implements List<E>, Deque<E>, Cloneable, java.io.Serializable {

    // Модификатор transient указывает на то, что данное свойство класса(размер/длина) нельзя сериализировать, то есть,
    //перевести объект в последовательность байтов, по которой затем его можно полностью восстановить
    transient int size = 0;

    //Указатель на первый узел
    transient Node<E> first;
    //Указатель на последний узел
    transient Node<E> last;

    /* Важно! Если размер списка равен нулю, то первый и последний узлы будут null (пустые),
    если же размер списка не равен нулю, то пустыми окажутся только самый первый узел и самый последний,
    остальные узлы будут содержать ссылки(адреса) на объекты
    void dataStructureInvariants() {
        assert (size == 0)
            ? (first == null && last == null)
            : (first.prev == null && last.next == null);
    }
    */

    // Создание пустого списка
    public LinkedList() {
    }

    /* Создание списка, содержащего элементы указанной коллекции, в том порядке,
    в котором они возвращаются итератором коллекции.
    Параметр с - коллекция, элементы которой должны быть помещены в этот список.
    Исключение NullPointerException - если указанная коллекция имеет значение null.
    */
    public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    //Ссылка на е как на первый элемент
    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
        modCount++;
    }

    //Ссылка на е как на последний элемент
    void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
    }

    // Вставка элемента е перед ненулевым Node succ.
    void linkBefore(E e, Node<E> succ) {
        // assert succ != null;
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
        modCount++;
    }

    //Разъединение ненулевого первого узла f.
    private E unlinkFirst(Node<E> f) {
        // assert f == first && f != null;
        final E element = f.item;
        final Node<E> next = f.next;
        f.item = null;
        f.next = null; // help GC
        first = next;
        if (next == null)
            last = null;
        else
            next.prev = null;
        size--;
        modCount++;
        return element;
    }

    // Разъединение последнего ненулевого узла l.
    private E unlinkLast(Node<E> l) {
        // assert l == last && l != null;
        final E element = l.item;
        final Node<E> prev = l.prev;
        l.item = null;
        l.prev = null; // help GC
        last = prev;
        if (prev == null)
            first = null;
        else
            prev.next = null;
        size--;
        modCount++;
        return element;
    }

    // Разъединение ненулевого узла x.
    E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }

    // Возвращение первого элемента в списке.
    // Исключение: NoSuchElementException – если список пуст.
    public E getFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return f.item;
    }

    // Возвращение последнего элемента в списке.
    // Исключение: NoSuchElementException – если список пуст.
    public E getLast() {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return l.item;
    }

    // Удаление и возврат первого элемента из списка.
    // Исключение: NoSuchElementException – если список пуст.
    public E removeFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return unlinkFirst(f);
    }

    // Удаление и возврат последнего элемента из списка.
    // Исключение: NoSuchElementException – если список пуст.
    public E removeLast() {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return unlinkLast(l);
    }

    // Вставка указанного элемента в начало списка.
    // Параметры: e – добавляемый элемент.
    public void addFirst(E e) {
        linkFirst(e);
    }

    /* Добавление указанного элемента в конец списка.
    Этот метод эквивалентен add.
    Параметры: e – добавляемый элемент */
    public void addLast(E e) {
        linkLast(e);
    }

    // Возвращает true, если этот список содержит указанный элемент.
    // Параметры: o — элемент, наличие которого в списке нужно проверить.
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    // Возвращает количество элементов в списке.
    public int size() {
        return size;
    }

    //Добавляет указанный элемент в конец списка.
    // Параметры: e – элемент, который будет добавлен к списку.
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    /* Удаляет первое вхождение указанного элемента из списка, если он присутствует.
    Если список не содержит элемента, он остается неизменным.
    Удаление элемента с наименьшим индексом i таким, что Objects.equals(o, get(i)) (если такой элемент существует).
    Возвращает true, если список содержит указанный элемент.
    Параметры: o — элемент, который нужно удалить из списка, если он присутствует.
     */
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    /* Добавляет все элементы указанной коллекции в конец списка в том порядке,
    в котором они возвращаются итератором указанной коллекции.
    Поведение этой операции не определено, если указанная коллекция изменяется во время выполнения операции.
    Параметры: c – коллекция, содержащая элементы, которые нужно добавить в список.
    Возвращает: true, если список изменился в результате вызова.
     */
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    /* Вставляет все элементы указанной коллекции в список, начиная с указанной позиции.
    Сдвигает элемент, находящийся в данный момент в этой позиции (если есть),
    и любые последующие элементы вправо (увеличивает их индексы).
    Новые элементы появятся в списке в том порядке, в котором они возвращаются указанным итератором коллекции.
    Параметры: index – индекс, по которому нужно вставить первый элемент из указанной коллекции;
    c – коллекция, содержащая элементы, которые нужно добавить в список.
    Возвращает: true, если список изменился в результате вызова.
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        checkPositionIndex(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)
            return false;

        Node<E> pred, succ;
        if (index == size) {
            succ = null;
            pred = last;
        } else {
            succ = node(index);
            pred = succ.prev;
        }

        for (Object o : a) {
            @SuppressWarnings("unchecked") E e = (E) o;
            Node<E> newNode = new Node<>(pred, e, null);
            if (pred == null)
                first = newNode;
            else
                pred.next = newNode;
            pred = newNode;
        }

        if (succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
        modCount++;
        return true;
    }

    // Удаляет все элементы из списка. Список будет пуст после возврата этого вызова.
    public void clear() {
        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
        modCount++;
    }

    // Операции позиционного доступа
    /* Возвращает элемент в указанной позиции в списке.
    Параметры: index – индекс возвращаемого элемента.
    Исключение: IndexOutOfBoundsException.
    */
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    /* Заменяет элемент в указанной позиции в списке указанным элементом.
    Параметры: index – индекс элемента для замены;
    element – элемент, который будет храниться в указанной позиции.
    Возвращает элемент ранее в указанной позиции.
     */
    public E set(int index, E element) {
        checkElementIndex(index);
        Node<E> x = node(index);
        E oldVal = x.item;
        x.item = element;
        return oldVal;
    }

    /* Вставляет указанный элемент в указанную позицию в списке.
    Сдвигает элемент, находящийся в данный момент в этой позиции (если есть), и любые последующие элементы вправо
    (добавляет единицу к их индексам).
    Параметры: index – индекс, по которому указанный элемент должен быть вставлен;
    element – элемент, который нужно вставить.
     */
    public void add(int index, E element) {
        checkPositionIndex(index);

        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }

    /* Удаляет элемент в указанной позиции в списке.
    Сдвигает любые последующие элементы влево (вычитает единицу из их индексов).
    Возвращает элемент, который был удален из списка.
    Параметры: index – индекс удаляемого элемента.
    Возвращает: элемент ранее в указанной позиции.
     */
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    // Сообщает, является ли аргумент индексом существующего элемента.
    boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    // Сообщает, является ли аргумент индексом допустимой позиции для итератора или операции добавления.
    boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    // Создает подробное сообщение IndexOutOfBoundsException.
    public String outOfBoundsMsg() {
        String index = null;
        return "Index: " + index + ", Size: " + size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg());
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg());
    }

    // Возвращает (ненулевой) узел с указанным индексом элемента.
    Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    // Операции поиска

    /* Возвращает индекс первого появления указанного элемента в списке или -1, если список не содержит элемента.
    Возвращает наименьший индекс i, такой что Objects.equals(o, get(i)), или -1, если такого индекса нет.
    Параметры: o — элемент для поиска.
     */
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null)
                    return index;
                index++;
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        return -1;
    }

    /* Возвращает индекс последнего вхождения указанного элемента в списке или -1, если этот список не содержит элемента.
    Возвращает наивысший индекс i, такой что Objects.equals(o, get(i)), или -1, если такого индекса нет.
    Параметры: o — элемент для поиска.
     */
    public int lastIndexOf(Object o) {
        int index = size;
        if (o == null) {
            for (Node<E> x = last; x != null; x = x.prev) {
                index--;
                if (x.item == null)
                    return index;
            }
        } else {
            for (Node<E> x = last; x != null; x = x.prev) {
                index--;
                if (o.equals(x.item))
                    return index;
            }
        }
        return -1;
    }

    // Queue operations.

    /* Извлекает, но не удаляет заголовок (первый элемент) списка.
    Возвращает: заголовок (первый элемент) этого списка или ноль, если список пуст
     */
    public E peek() {
        final Node<E> f = first;
        return (f == null) ? null : f.item;
    }

    /* Извлекает, но не удаляет заголовок (первый элемент) списка.
    Возвращает: заголовок (первый элемент) списка.
    Исключение: NoSuchElementException – если список пуст.
     */
    public E element() {
        return getFirst();
    }

    /* Извлекает и удаляет заголовок (первый элемент) списка.
    Возвращает: заголовок списка (первый элемент) или ноль, если список пуст.
     */
    public E poll() {
        final Node<E> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }

    /* Извлекает и удаляет заголовок (первый элемент) списка.
    Возвращает: первый элемент списка.
     */
    public E remove() {
        return removeFirst();
    }

    // Добавляет указанный элемент в качестве последнего элемента списка.
    public boolean offer(E e) {
        return add(e);
    }

    // Deque operations

    // Вставляет указанный элемент в начало списка.
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    // Вставляет указанный элемент в конец списка.
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    // Извлекает, но не удаляет первый элемент списка или возвращает null, если список пуст.
    public E peekFirst() {
        final Node<E> f = first;
        return (f == null) ? null : f.item;
    }

    // Извлекает, но не удаляет, последний элемент списка или возвращает null, если список пуст.
    public E peekLast() {
        final Node<E> l = last;
        return (l == null) ? null : l.item;
    }

    // Извлекает и удаляет первый элемент списка или возвращает null, если список пуст.
    public E pollFirst() {
        final Node<E> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }

    // Извлекает и удаляет последний элемент списка или возвращает значение null, если список пуст.
    public E pollLast() {
        final Node<E> l = last;
        return (l == null) ? null : unlinkLast(l);
    }

    // Помещает элемент в стек, представленный списком. Вставляет элемент в начале этого списка.
    // Этот метод эквивалентен addFirst.
    public void push(E e) {
        addFirst(e);
    }

    // Извлекает элемент из стека, представленного списком. Удаляет и возвращает первый элемент списка.
    //Этот метод эквивалентен removeFirst().
    public E pop() {
        return removeFirst();
    }

    /* Удаляет первое вхождение указанного элемента в списке (при обходе списка от начала до конца).
    Если список не содержит элемента, он не изменяется.
    Параметры: o — элемент, который нужно удалить из списка, если он присутствует.
     */
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    /* Удаляет последнее вхождение указанного элемента в списке (при обходе списка от начала до конца).
    Если список не содержит элемента, он не изменяется.
    Параметры: o — элемент, который нужно удалить из списка, если он присутствует
     */
    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            for (Node<E> x = last; x != null; x = x.prev) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = last; x != null; x = x.prev) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    /* Возвращает список-итератор элементов в этом списке (в правильной последовательности),
    начиная с указанной позиции в списке. Соблюдает общий контракт List.listIterator(int).
    Параметры: index — индекс первого элемента, который будет возвращен из списка-итератора (вызовом next).
     */
    public ListIterator<E> listIterator(int index) {
        checkPositionIndex(index);
        return new ListItr(index);
    }

    private class ListItr implements ListIterator<E> {
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;
        private int expectedModCount = modCount;

        ListItr(int index) {
            // assert isPositionIndex(index);
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        public boolean hasNext() {
            return nextIndex < size;
        }

        public E next() {
            checkForComodification();
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }

        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        public E previous() {
            checkForComodification();
            if (!hasPrevious())
                throw new NoSuchElementException();

            lastReturned = next = (next == null) ? last : next.prev;
            nextIndex--;
            return lastReturned.item;
        }

        public int nextIndex() {
            return nextIndex;
        }

        public int previousIndex() {
            return nextIndex - 1;
        }

        public void remove() {
            checkForComodification();
            if (lastReturned == null)
                throw new IllegalStateException();

            Node<E> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = null;
            expectedModCount++;
        }

        public void set(E e) {
            if (lastReturned == null)
                throw new IllegalStateException();
            checkForComodification();
            lastReturned.item = e;
        }

        public void add(E e) {
            checkForComodification();
            lastReturned = null;
            if (next == null)
                linkLast(e);
            else
                linkBefore(e, next);
            nextIndex++;
            expectedModCount++;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            while (modCount == expectedModCount && nextIndex < size) {
                action.accept(next.item);
                lastReturned = next;
                next = next.next;
                nextIndex++;
            }
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    // Адаптер для предоставления нисходящих итераторов через ListItr.previous
    private class DescendingIterator implements Iterator<E> {
        private final ListItr itr = new ListItr(size());

        public boolean hasNext() {
            return itr.hasPrevious();
        }

        public E next() {
            return itr.previous();
        }

        public void remove() {
            itr.remove();
        }
    }

    @SuppressWarnings("unchecked")
    private LinkedList<E> superClone() {
        try {
            return (LinkedList<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    // Возвращает поверхностную копию LinkedList. (Сами элементы не клонируются.)
    public Object clone() {
        LinkedList<E> clone = superClone();

        // Перевести клон в первоначальное состояние
        clone.first = clone.last = null;
        clone.size = 0;
        clone.modCount = 0;

        // Инициализация клона с нашими элементами
        for (Node<E> x = first; x != null; x = x.next)
            clone.add(x.item);

        return clone;
    }

    /* Возвращает массив, содержащий все элементы в списке в правильной последовательности
     (от первого до последнего элемента).
     Этот метод должен выделить новый массив.
     Таким образом, вызывающий объект может изменять возвращаемый массив.
     Этот метод действует как мост между API-интерфейсами на основе массивов и коллекций.
     */
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = first; x != null; x = x.next)
            result[i++] = x.item;
        return result;
    }
}
