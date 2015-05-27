class Node {
    public int value;
    public boolean color_red; // if the node is red "color_red" is true; else is false;
    public Node p, left, right;

    public Node(int value, boolean color_red) {
        this.value = value;
        this.color_red = color_red;
        this.p = this.left = this.right = RBTree.nil;
    }

    public Node find(int value) {
        if (value < this.value && this.left != RBTree.nil) return this.left.find(value);
        else if (value > this.value && this.right != RBTree.nil) return this.right.find(value);
        else return this;
    }

    public void find50(Counter c, int first, RBTree result) {
        if (c.getValue() >= 50) return;

        if (this.left != RBTree.nil) {
            this.left.find50(c, first, result);
        }

        if (this.value > first && c.getValue() < 50) {
            result.insert(this.value);
            c.increment();
        }

        if (this.right != RBTree.nil) {
            this.right.find50(c, first, result);
        }
    }

    public Node minimum() {
        if (this.left != RBTree.nil) return this.left.minimum();
        else return this;
    }

    public Node maximum() {
        if (this.right != RBTree.nil) return this.right.maximum();
        else return this;
    }

    public Node successor() { // used in remotion
        if (this.right != RBTree.nil) return this.right.minimum();
        else return this;
    }

    public void inorderWalk() {
        if (this.left != RBTree.nil) this.left.inorderWalk();
        System.out.println(this.value);
        if (this.right != RBTree.nil) this.right.inorderWalk();
    }

    public void graph() {
        if (this.color_red) {
            System.out.println("\t" + this.value + " [style = filled, fillcolor = red];");
        } else {
            System.out.println("\t" + this.value + " [style = filled, fillcolor = black, fontcolor = white];");
        }

        if (this.left != RBTree.nil) {
            System.out.println("\t" + this.value + " -> " + this.left.value + " [label = \" left\"];");
            this.left.graph();
        }
        else {
            System.out.println("\t" + this.value + " -> nil [label = \" left\"];");
        }

        if (this.right != RBTree.nil) {
            System.out.println("\t" + this.value + " -> " + this.right.value + " [label = \" right\"];");
            this.right.graph();
        }
        else {
            System.out.println("\t" + this.value + " -> nil [label = \" right\"];");
        }
    }
}
