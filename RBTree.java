class RBTree {
    public Node root;
    public static Node nil = new Node(0, false);

    public RBTree(int value) {
        this.root = new Node(value, false);
    }

    public Node find(int value) {
        return this.root.find(value);
    }

    public void insert(int value) {
        Node n = this.find(value);
        if (value < n.value) {
            n.left = new Node(value, true);
            n.left.p = n;
            this.insertFixup(n.left);
        }
        else if (value > n.value) {
            n.right = new Node(value, true);
            n.right.p = n;
            this.insertFixup(n.right);
        }
    }

    private void insertFixup(Node z) {
        Node y;
        while (z.p.color_red) {
            if (z.p == z.p.p.left) {
                y = z.p.p.right;
                if (y.color_red) { // case 1: while repeats only if y.color_red
                    /* if my uncle is red, I change the color
                     * of my parent and uncle to black and
                     * my grandparent's color to red
                     * then, go up 2 levels on the tree
                     */
                    z.p.color_red = false;
                    y.color_red = false;
                    z.p.p.color_red = true;
                    z = z.p.p;
                }
                else { // uncle is black
                    if (z == z.p.right) { // case 2
                        z = z.p;
                        this.leftRotate(z);
                    }
                    // case 3
                    z.p.color_red = false;
                    z.p.p.color_red = true;
                    this.rightRotate(z.p.p);
                }
            }
            else {
                y = z.p.p.left;
                if (y.color_red) { // case 1
                    y.color_red = z.p.color_red = false;
                    z.p.p.color_red = true;
                    z = z.p.p;
                }
                else {
                    if (z == z.p.left) { // case 2
                        z = z.p;
                        this.rightRotate(z);
                    }
                    // case 3
                    z.p.color_red = false;
                    z.p.p.color_red = true;
                    this.leftRotate(z.p.p);
                }
            }
        }
        this.root.color_red = false;
    }

    private void leftRotate(Node x) {
        Node y = x.right;

        x.right = y.left;
        if (y.left != RBTree.nil) y.left.p = x;
        y.p = x.p;

        if (x.p == RBTree.nil) this.root = y;
        else if (x == x.p.left) x.p.left = y;
        else x.p.right = y;

        y.left = x;
        x.p = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;

        x.left = y.right;
        if (y.right != RBTree.nil) y.right.p = x;
        y.p = x.p;

        if (x.p == RBTree.nil) this.root = y;
        else if (x == x.p.left) x.p.left = y;
        else x.p.right = y;

        y.right = x;
        x.p = y;
    }

    public void remove(int value) {
        Node z = this.find(value);
        Node x, y = z;
        boolean yOriginalRed = y.color_red;

        if (z.left == RBTree.nil) {
            x = z.right;
            this.transplant(z, z.right);
        }
        else if (z.right == RBTree.nil) {
            x = z.left;
            this.transplant(z, z.left);
        }
        else {
            y = z.successor();
            yOriginalRed = y.color_red;
            x = y.right;

            if (y.p == z) x.p = y;
            else {
                this.transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            this.transplant(z, y);
            y.left = z.left;
            y.left.p = y;
            y.color_red = z.color_red;
        }

        if (!yOriginalRed) this.remFix(x);
    }

    /* Adjusts v's references to match u's:
     * u.p.x = v and v.p = u.p (if v is not RBTree.nil).
     * Doesn't touch u.p, u.left and u.right. u is
     * still there as though nothing happened.
     */
    private void transplant(Node u, Node v) {
        if (u.p == RBTree.nil) this.root = v;
        else if (u == u.p.left) u.p.left = v;
        else u.p.right = v;
        v.p = u.p;
    }

    private void remFix(Node x) {
        Node w;

        while (x != this.root && !x.color_red) {
            if (x == x.p.left) {
                w = x.p.right;

                if (w.color_red) { // case 1
                    w.color_red = false;
                    x.p.color_red = true;
                    this.leftRotate(x.p);
                    w = x.p.right;
                }
                if (!w.left.color_red && !w.right.color_red) { // case 2
                    w.color_red = true;
                    x = x.p;
                }
                else {
                    if (!w.right.color_red) { // case 3
                        w.left.color_red = false;
                        w.color_red = true;
                        this.rightRotate(w);
                        w = x.p.right;
                    }
                    // case 4
                    w.color_red = x.p.color_red;
                    x.p.color_red = false;
                    w.right.color_red = false;
                    this.leftRotate(x.p);
                    x = this.root;
                }
            }
            else {
                w = x.p.left;

                if (w.color_red) { // case 1
                    w.color_red = false;
                    x.p.color_red = true;
                    this.rightRotate(x.p);
                    w = x.p.left;
                }
                if (!w.left.color_red && !w.right.color_red) { // case 2
                    w.color_red = true;
                    x = x.p;
                }
                else {
                    if (!w.left.color_red) { // case 3
                        w.right.color_red = false;
                        w.color_red = true;
                        this.leftRotate(w);
                        w = x.p.left;
                    }
                    // case 4
                    w.color_red = x.p.color_red;
                    x.p.color_red = false;
                    w.left.color_red = false;
                    this.rightRotate(x.p);
                    x = this.root;
                }
            }
        }
        x.color_red = false;
    }

    // Remove all nodes in the tree.
    public RBTree delete() {
        while (this.root != RBTree.nil) {
            this.remove(this.root.value);
        }
        this.root = null;
        return null;
    }

    public Node minimum() {
        return this.root.minimum();
    }

    public Node maximum() {
        return this.root.maximum();
    }

    public void inorderWalk() {
        this.root.inorderWalk();
    }

    public void graph() {
        System.out.println("digraph RBTree {");
        this.root.graph();
        System.out.println("\tnil [style = filled, fillcolor = black, fontcolor = white];");
        //System.out.println("\tnil -> " + this.root.value + ";");
        System.out.println("}");
    }
}
