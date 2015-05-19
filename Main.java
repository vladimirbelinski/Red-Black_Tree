class Main {
    public static void main(String args[]) {
        Tree t = new Tree(0);
        t.insert(1);

        t.graph();

        t = t.delete();
    }
}
