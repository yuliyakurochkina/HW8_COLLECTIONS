package ru.skypro;

import java.util.*;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import jdk.internal.access.SharedSecrets;
import jdk.internal.util.ArraysSupport;

//Задание3 ArrayList
/*Реализация интерфейса List с изменяемым размером массива. Реализует все необязательные операции со списками
и разрешает все элементы, включая null. В дополнение к реализации интерфейса List этот класс предоставляет методы
для управления размером массива, который используется внутри для хранения списка.
Каждый экземпляр ArrayList имеет емкость. Емкость — это размер массива, используемого для хранения элементов списка.
Он всегда больше размера списка. По мере добавления элементов в список ArrayList его емкость автоматически увеличивается.
 */
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 8683452581122892189L;

    // Начальная емкость по умолчанию.
    private static final int DEFAULT_CAPACITY = 10;

    // Общий пустой экземпляр массива, используемый для пустых экземпляров.
    private static final Object[] EMPTY_ELEMENTDATA = {};

    // Общий пустой экземпляр массива, используемый для пустых экземпляров размера по умолчанию.
    // Отличается от EMPTY_ELEMENTDATA, чтобы знать, насколько он будет увеличиваться при добавлении первого элемента.
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /* Буфер массива, в котором хранятся элементы ArrayList. Емкость ArrayList равна длине этого буфера массива.
    Любой пустой ArrayList с elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA будет расширен до DEFAULT_CAPACITY
    при добавлении первого элемента.
     */
    public Object[] elementData;

    //Размер списка ArrayList (количество содержащихся в нем элементов).
    private int size;

    /* Создает пустой список с указанной начальной емкостью.
    Параметры: initialCapacity — начальная емкость списка.
    Исключение: IllegalArgumentException – если указанная начальная емкость отрицательна.
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    // Создает пустой список с начальной емкостью десять.
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /* Создает список, содержащий элементы указанной коллекции, в том порядке, в котором они возвращаются
    итератором коллекции.
    Параметры: c – коллекция, элементы которой должны быть помещены в этот список.
    Исключение:  NullPointerException — если указанная коллекция имеет значение null.
     */
    public ArrayList(Collection<? extends E> c) {
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            if (c.getClass() == java.util.ArrayList.class) {
                elementData = a;
            } else {
                elementData = Arrays.copyOf(a, size, Object[].class);
            }
        } else {
            // replace with empty array.
            elementData = EMPTY_ELEMENTDATA;
        }
    }

    /* Сокращает емкость этого экземпляра ArrayList до текущего размера списка.
     Приложение может использовать эту операцию, чтобы минимизировать объем хранилища экземпляра ArrayList.
     */
    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            elementData = (size == 0)
                    ? EMPTY_ELEMENTDATA
                    : Arrays.copyOf(elementData, size);
        }
    }

    /* При необходимости увеличивает емкость этого экземпляра ArrayList, чтобы гарантировать,
    что он может содержать по крайней мере количество элементов, указанное аргументом минимальной емкости.
    Параметры: minCapacity – желаемая минимальная емкость.
     */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length
                && !(elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
                && minCapacity <= DEFAULT_CAPACITY)) {
            modCount++;
            grow(minCapacity);
        }
    }

    /* Увеличивает емкость, чтобы гарантировать, что он может содержать по крайней мере количество элементов,
    указанное аргументом минимальной емкости.
    Параметры: minCapacity – желаемая минимальная емкость.
    Исключение: OutOfMemoryError — если minCapacity меньше нуля.
     */
    private Object[] grow(int minCapacity) {
        int oldCapacity = elementData.length;
        if (oldCapacity > 0 || elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            int newCapacity = ArraysSupport.newLength(oldCapacity,
                    minCapacity - oldCapacity, /* минимальная */
                    oldCapacity >> 1           /* предпочтительная */);
            return elementData = Arrays.copyOf(elementData, newCapacity);
        } else {
            return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    // Возвращает количество элементов в этом списке.
    public int size() {
        return size;
    }

    // Возвращает true, если этот список не содержит элементов.
    public boolean isEmpty() {
        return size == 0;
    }

    /* Возвращает true, если этот список содержит указанный элемент.
     Параметры: o — элемент, наличие которого в списке нужно проверить.
     */
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    // Возвращает индекс первого выбора элемента в этом списке или -1, если список не содержит элемент.
    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);
    }

    int indexOfRange(Object o, int start, int end) {
        Object[] es = elementData;
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    // Возвращает индекс последнего вхождения указанного элемента в этом списке или -1, если список не содержит элемента.
    public int lastIndexOf(Object o) {
        return lastIndexOfRange(o, 0, size);
    }

    int lastIndexOfRange(Object o, int start, int end) {
        Object[] es = elementData;
        if (o == null) {
            for (int i = end - 1; i >= start; i--) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = end - 1; i >= start; i--) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    // Возвращает созданную копию этого ArrayList. (Сами элементы не копируются.)
    public Object clone() {
        try {
            java.util.ArrayList<?> v = (java.util.ArrayList<?>) super.clone();
            elementData = Arrays.copyOf(elementData, size);
            modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    /* Возвращает массив, содержащий все элементы в списке в правильной последовательности (от первого до последнего
    элемента).
    Возвращенный массив будет "безопасным" в том смысле, что этот список не поддерживает никаких ссылок на него.
    (Этот метод должен выделить новый массив). Таким образом, вызывающий объект может изменять возвращаемый массив.
    Этот метод действует как мост между API-интерфейсами на основе массивов и коллекций.
     */
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    /* Возвращает массив, содержащий все элементы в этом списке в правильной последовательности
    (от первого до последнего элемента);
    тип возвращаемого массива во время выполнения совпадает с типом указанного массива.
    Если список помещается в указанный массив, он возвращается в нем.
    В ином случае выделяется новый массив с типом времени выполнения указанного массива и размером этого списка.
    Если список помещается в указанный массив с лишним местом (т. е. в массиве больше элементов, чем в списке),
    элемент в массиве, следующий сразу за концом коллекции, устанавливается равным нулю.
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    // Операции позиционного доступа

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    @SuppressWarnings("unchecked")
    static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
    }

    /* Возвращает элемент в указанной позиции в списке.
    Параметры: index – индекс возвращаемого элемента.
    Исключение: IndexOutOfBoundsException.
     */
    public E get(int index) {
        Objects.checkIndex(index, size);
        return elementData(index);
    }

    /* Заменяет элемент в указанной позиции в списке указанным элементом.
    Параметры: index – индекс элемента для замены;
    element – элемент, который будет храниться в указанной позиции.
    Исключение: IndexOutOfBoundsException.
    */
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    /* Этот вспомогательный метод отделен от add(E), чтобы размер байт-кода метода не превышал 35.
     */
    private void add(E e, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = e;
        size = s + 1;
    }

    /* Добавляет указанный элемент в конец списка.
    Параметры: e – элемент, который будет добавлен к списку.
     */
    public boolean add(E e) {
        modCount++;
        add(e, elementData, size);
        return true;
    }

    /* Вставляет указанный элемент в указанную позицию в списке. Сдвигает элемент, находящийся в данный момент
    в этой позиции (если есть), и любые последующие элементы вправо (добавляет единицу к их индексам).
    Параметры: index – индекс, по которому указанный элемент должен быть вставлен;
    element – элемент, который нужно вставить.
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        modCount++;
        final int s;
        Object[] elementData;
        if ((s = size) == (elementData = this.elementData).length)
            elementData = grow();
        System.arraycopy(elementData, index,
                elementData, index + 1,
                s - index);
        elementData[index] = element;
        size = s + 1;
    }

    /* Удаляет элемент в указанной позиции всписке. Сдвигает любые последующие элементы влево
    (вычитает единицу из их индексов).
    Параметры: index – индекс удаляемого элемента.
    Возвращает элемент, который был удален из списка.
     */
    public E remove(int index) {
        Objects.checkIndex(index, size);
        final Object[] es = elementData;

        @SuppressWarnings("unchecked") E oldValue = (E) es[index];
        fastRemove(es, index);

        return oldValue;
    }

    /* Сравнивает указанный объект сo списком на предмет равенства.
    Возвращает true, когда указанный объект также является списком, оба списка имеют одинаковый размер
    и все соответствующие пары элементов в двух списках равны.
    Другими словами, два списка считаются равными,
    если они содержат одни и те же элементы в одном и том же порядке.
    Это определение гарантирует правильную работу метода equals в различных реализациях интерфейса List.
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof List)) {
            return false;
        }

        final int expectedModCount = modCount;
        // ArrayList может быть подклассом и иметь произвольное поведение
        boolean equal = (o.getClass() == java.util.ArrayList.class)
                ? equalsArrayList((java.util.ArrayList<?>) o)
                : equalsRange((List<?>) o, 0, size);

        checkForComodification(expectedModCount);
        return equal;
    }

    boolean equalsRange(List<?> other, int from, int to) {
        final Object[] es = elementData;
        if (to > es.length) {
            throw new ConcurrentModificationException();
        }
        var oit = other.iterator();
        for (; from < to; from++) {
            if (!oit.hasNext() || !Objects.equals(es[from], oit.next())) {
                return false;
            }
        }
        return !oit.hasNext();
    }

    private boolean equalsArrayList(java.util.ArrayList<?> other) {
        final int otherModCount = modCount;
        final int s = size;
        boolean equal;
        if (equal = (s == size)) {
            final Object[] otherEs = elementData;
            final Object[] es = elementData;
            if (s > es.length || s > otherEs.length) {
                throw new ConcurrentModificationException();
            }
            for (int i = 0; i < s; i++) {
                if (!Objects.equals(es[i], otherEs[i])) {
                    equal = false;
                    break;
                }
            }
        }
        checkForComodification(otherModCount);
        return equal;
    }

    private void checkForComodification(final int expectedModCount) {
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    public int hashCode() {
        int expectedModCount = modCount;
        int hash = hashCodeRange(0, size);
        checkForComodification(expectedModCount);
        return hash;
    }

    int hashCodeRange(int from, int to) {
        final Object[] es = elementData;
        if (to > es.length) {
            throw new ConcurrentModificationException();
        }
        int hashCode = 1;
        for (int i = from; i < to; i++) {
            Object e = es[i];
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }

    /* Удаляет первое вхождение в список элементов из этого списка, если он встречается.
    Если список не содержит элементов, он не обнаруживается.
    Возвращает true, если этот список содержит указанный элемент.
    Параметры: o — элемент, который необходимо удалить из этого списка, если он встречается.
     */
    public boolean remove(Object o) {
        final Object[] es = elementData;
        final int size = this.size;
        int i = 0;
        found:
        {
            if (o == null) {
                for (; i < size; i++)
                    if (es[i] == null)
                        break found;
            } else {
                for (; i < size; i++)
                    if (o.equals(es[i]))
                        break found;
            }
            return false;
        }
        fastRemove(es, i);
        return true;
    }

    // Частный метод удаления, который пропускает проверку границ и не возвращает удаленное значение.
    private void fastRemove(Object[] es, int i) {
        modCount++;
        final int newSize;
        if ((newSize = size - 1) > i)
            System.arraycopy(es, i + 1, es, i, newSize - i);
        es[size = newSize] = null;
    }

    // Удаляет все элементы из списка. Список будет пуст после возврата этого вызова.
    public void clear() {
        modCount++;
        final Object[] es = elementData;
        for (int to = size, i = size = 0; i < to; i++)
            es[i] = null;
    }

    /* Добавляет все элементы указанной коллекции в конец списка в том порядке,
    в котором они возвращаются итератором указанной коллекции.
    Поведение этой операции не определено, если указанная коллекция изменяется во время выполнения операции.
    Параметры: c – коллекция, содержащая элементы, которые нужно добавить в список.
    Возвращает true, если этот список изменился в результате вызова.
    Исключение: NullPointerException — если указанная коллекция имеет значение null.
     */
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        modCount++;
        int numNew = a.length;
        if (numNew == 0)
            return false;
        Object[] elementData;
        final int s;
        if (numNew > (elementData = this.elementData).length - (s = size))
            elementData = grow(s + numNew);
        System.arraycopy(a, 0, elementData, s, numNew);
        size = s + numNew;
        return true;
    }

    /* Вставляет все элементы указанной коллекции в список, начиная с указанной позиции.
    Сдвигает элемент, находящийся в данный момент в этой позиции (если есть), и любые последующие элементы вправо
    (увеличивает их индексы). Новые элементы появятся в списке в том порядке, в котором они возвращаются указанным
    итератором коллекции.
    Параметры: index – индекс, по которому нужно вставить первый элемент из указанной коллекции;
    c – коллекция, содержащая элементы, которые нужно добавить в этот список.
    Возвращает true, если этот список изменился в результате вызова.
    Исключения: IndexOutOfBoundsException - выход индекса за пределы.
    NullPointerException — если указанная коллекция имеет значение null.
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);

        Object[] a = c.toArray();
        modCount++;
        int numNew = a.length;
        if (numNew == 0)
            return false;
        Object[] elementData;
        final int s;
        if (numNew > (elementData = this.elementData).length - (s = size))
            elementData = grow(s + numNew);

        int numMoved = s - index;
        if (numMoved > 0)
            System.arraycopy(elementData, index,
                    elementData, index + numNew,
                    numMoved);
        System.arraycopy(a, 0, elementData, index, numNew);
        size = s + numNew;
        return true;
    }

    /* Удаляет из списка все элементы, индекс которых находится между fromIndex, включительно, и toIndex, исключая.
    Сдвигает все последующие элементы влево (уменьшает их индекс).
    Исключение: IndexOutOfBoundsException – если fromIndex или toIndex вне допустимого диапазона
    (fromIndex < 0 || toIndex > size() || toIndex < fromIndex).
     */
    protected void removeRange(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException(
                    outOfBoundsMsg(fromIndex, toIndex));
        }
        modCount++;
        shiftTailOverGap(elementData, fromIndex, toIndex);
    }

    // Стирает разрыв от lo до hi, скользя вниз по следующим элементам.
    private void shiftTailOverGap(Object[] es, int lo, int hi) {
        System.arraycopy(es, hi, es, lo, size - hi);
        for (int to = size, i = (size -= hi - lo); i < to; i++)
            es[i] = null;
    }

    // Версия rangeCheck, используемая add и addAll.
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    // Создает подробное сообщение IndexOutOfBoundsException.
    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    // Версия, используемая при проверке условия (fromIndex > toIndex).
    private static String outOfBoundsMsg(int fromIndex, int toIndex) {
        return "From Index: " + fromIndex + " > To Index: " + toIndex;
    }

    /*Удаляет из списка все его элементы, содержащиеся в указанной коллекции.
    Параметры: c – коллекция, содержащая элементы, которые нужно удалить из списка
    Возвращает true, если этот список изменился в результате вызова.
    Исключения:
    ClassCastException — если класс элемента этого списка несовместим с указанной коллекцией;
    NullPointerException — если список содержит нулевой элемент,
    а указанная коллекция не допускает нулевых элементов или если указанная коллекция является нулевой.
     */
    public boolean removeAll(Collection<?> c) {
        return batchRemove(c, false, 0, size);
    }

    /* Сохраняет в списке только те элементы, которые содержатся в указанной коллекции,
    то есть, удаляет из списка все его элементы, не содержащиеся в указанной коллекции.
    Параметры: c – коллекция, содержащая элементы, которые необходимо сохранить в этом списке.
    Возвращает true, если этот список изменился в результате вызова
    Исключения:
    ClassCastException — если класс элемента списка несовместим с указанной коллекцией;
    NullPointerException — если список содержит нулевой элемент,
    а указанная коллекция не допускает нулевых элементов или если указанная коллекция является нулевой.
     */
    public boolean retainAll(Collection<?> c) {
        return batchRemove(c, true, 0, size);
    }

    boolean batchRemove(Collection<?> c, boolean complement,
                        final int from, final int end) {
        Objects.requireNonNull(c);
        final Object[] es = elementData;
        int r;
        // Оптимизация для начального запуска
        for (r = from; ; r++) {
            if (r == end)
                return false;
            if (c.contains(es[r]) != complement)
                break;
        }
        int w = r++;
        try {
            for (Object e; r < end; r++)
                if (c.contains(e = es[r]) == complement)
                    es[w++] = e;
        } catch (Throwable ex) {
            // Сохранить поведенческую совместимость с AbstractCollection, даже если c.contains() выдает исключение.
            System.arraycopy(es, r, es, w, end - r);
            w += end - r;
            throw ex;
        } finally {
            modCount += end - w;
            shiftTailOverGap(es, w, end);
        }
        return true;
    }

    /* Сохраняет состояние экземпляра ArrayList в поток (то есть сериализует его).
    Параметры: с - поток.
    Исключение: java.io.IOException — если возникает ошибка ввода-вывода.
     */
    @java.io.Serial
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        // Записать количество элементов
        int expectedModCount = modCount;
        s.defaultWriteObject();

        // Записать размер как емкость для поведенческой совместимости с clone().
        s.writeInt(size);

        // Выписать все элементы в правильном порядке.
        for (int i = 0; i < size; i++) {
            s.writeObject(elementData[i]);
        }

        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    /* Восстанавливает экземпляр ArrayList из потока (то есть, десериализует его).
    Параметры: с - поток.
    Исключения:
    ClassNotFoundException — если не удалось найти класс сериализованного объекта;
    java.io.IOException — если возникает ошибка ввода-вывода.
     */
    @java.io.Serial
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {

        // Чтение по размеру
        s.defaultReadObject();

        // Читать по емкости
        s.readInt(); // игнорируется

        if (size > 0) {
            // как clone(), выделять массив на основе размера, а не емкости
            SharedSecrets.getJavaObjectInputStreamAccess().checkArray(s, Object[].class, size);
            Object[] elements = new Object[size];

            // Прочитать все элементы в правильном порядке.
            for (int i = 0; i < size; i++) {
                elements[i] = s.readObject();
            }

            elementData = elements;
        } else if (size == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new java.io.InvalidObjectException("Invalid size: " + size);
        }
    }

    /* Возвращает итератор списка по элементам в этом списке (в правильной последовательности),
    начиная с указанной позиции в списке. Указанный индекс указывает первый элемент,
    который будет возвращен первоначальным вызовом next.
    Первоначальный вызов предыдущего вернет элемент с указанным индексом минус один.
    Возвращаемый итератор списка является отказоустойчивым.
    Исключение: IndexOutOfBoundsException.
     */
    public ListIterator<E> listIterator(int index) {
        rangeCheckForAdd(index);
        return new ListItr(index);
    }

    /* Возвращает итератор списка по элементам в списке (в правильной последовательности).
    Возвращаемый итератор списка является отказоустойчивым.
     */
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    /* Возвращает итератор по элементам в списке в правильной последовательности.
    Возвращаемый итератор отказоустойчив.
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    // Оптимизированная версия AbstractList.Itr
    private class Itr implements Iterator<E> {
        int cursor;       // индекс следующего возвращаемого элемента
        int lastRet = -1; // индекс последнего возвращенного элемента; -1 если нет такого
        int expectedModCount = modCount;
        private int size;

        // предотвратить создание синтетического конструктора
        Itr() {
        }

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = new Object[0];
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        private void remove(int lastRet) {
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            final int size = this.size;
            int i = cursor;
            if (i < size) {
                final Object[] es = elementData;
                if (i >= es.length)
                    throw new ConcurrentModificationException();
                for (; i < size && modCount == expectedModCount; i++)
                    action.accept(elementAt(es, i));
                // update once at end to reduce heap write traffic
                cursor = i;
                lastRet = i - 1;
                checkForComodification();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    // Оптимизированная версия AbstractList.ListItr
    private class ListItr extends Itr implements ListIterator<E> {
        private Object[] elementData;

        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        private void set(int lastRet, E e) {
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        private void add(int i, E e) {
        }
    }
}
