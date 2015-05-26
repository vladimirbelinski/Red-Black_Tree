class Counter {
    private Integer i;

    public Counter(int i) {
        this.i = i;
    }

    public String toString() {
        return this.i.toString();
    }

    public void setValue(int i) {
        this.i = i;
    }
    public int getValue() {
        return this.i;
    }

    public void increment() {
        this.i++;
    }
    public void decrement() {
        this.i--;
    }
}
