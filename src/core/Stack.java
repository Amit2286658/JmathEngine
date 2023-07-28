package core;

//LIFO Structure
@SuppressWarnings("unchecked")
public final class Stack<T> {
    private Object[] items;
    //internal Stack pointer;
    private int pointer = -1, counter = -1;

    //last pointer state
    int last_point = 0;

    public Stack(T[] list){
        this(list.length);
        pushAll(list);
    }

    public Stack(int size) {
        items = new Object[size];
    }

    public Stack(){
        this(10);
    }

    public void push(T item){
        //increment the pointer
        pointer++;
        counter++;

        if (pointer <= items.length - 1) {
            items[pointer] = item;
            return;
        }
        //no index is free, allocate a new array with increased size;
        var items1 = new Object[items.length + 10];
        //copy the previous array over to the new one.
        System.arraycopy(items, 0, items1, 0, items.length);
        //reassignment
        items = items1;

        items[pointer] = item;
    }

    public void resetToCheckpoint(){
        pointer += (last_point - pointer);
        last_point = 0;
    }

    public void checkpoint(){
        last_point = pointer;
    }

    public void reset(){
        pointer = counter;
    }

    public T pop(){
        if (pointer < 0)
            throw new IndexOutOfBoundsException("pointer is out of bounds");

        T item;
        item = (T) items[pointer];
        //no need to remove the data at the current pointer
        //since the pointer will be decremented, and the next time the push function will be called,
        //the pointer will be incremented and any data at the current pointer location will be replaced.

        //decrement the pointer
        pointer--;

        return item;
    }

    public T peek(){
        if (pointer >= 0)
            return (T) items[pointer];
        throw new IndexOutOfBoundsException("pointer is out of bounds");
    }

    public boolean hasNext(){
        //pointer is only incremented when an item is pushed onto the stack, therefore providing
        //null safety by default, meaning wherever the pointer is located, it's guaranteed to be occupied,
        //by a certain object.
        return pointer >= 0;
    }

    //no pointer modification.
    public boolean contains(T item){
        for(Object op : items){
            if (op != null && op.equals(item)){
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty(){
        return pointer == -1;
    }

    public int getLength(){
        return pointer + 1;
    }

    public int getRawLength(){
        return items.length;
    }

    public int getFreeLength(){
        return items.length - (pointer + 1);
    }

    public int getPointerLocation() {
        return pointer;
    }

    public Stack<T> newClone(){
        Stack<T> stack = new Stack<>(getLength());
        for(int i = 0; i < getLength(); i++){
            stack.push((T)items[i]);
        }
        return stack;
    }

    public Stack<T> reverse() {
        Stack<T> temp = new Stack<>();
        while(hasNext()){
            temp.push(pop());
        }
        return temp;
    }

    public void replace(T item){
        pointer++;
        items[pointer] = item;
        pointer--;
    }

    public Object[] getAsList(){
        T[] list = (T[]) new Object[getLength()];
        int index = 0;
        while(hasNext()){
            list[index] = pop();
            index++;
        }
        reset();
        return list;
    }

    public void pushAll(T[] list){
        for(T obj : list){
            push(obj);
        }
    }
}

