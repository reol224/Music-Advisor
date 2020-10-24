class Holder<T> {
    private Object value;

    public void set(Object value) {
        this.value = value;
    }

    public Object get() {
        return value;
    }
}

class Main {
    public static void main(String... args) {
        Holder<Integer> holder = new Holder();
        holder.set(256);

        // correct the line to make the code compiled
        Integer value = (Integer) holder.get();

        // do not change
        System.out.println(value);
    }
}