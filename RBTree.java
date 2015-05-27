class RBTree {
    public Node root;
    public static Node nil = new Node(0, false);

    public RBTree() {
        this.root = RBTree.nil;
    }

    public RBTree(int value) {
        this.root = new Node(value, false);
    }

    public Node find(int value) {
        return this.root.find(value);
    }

	public RBTree find50(int first) {
        Counter c = new Counter(0);
        RBTree t50 = new RBTree();

        this.root.find50(c, first, t50);

        return t50;
    }

    public void insert(int value) {
		if (this.root == RBTree.nil) {
            this.root = new Node(value, false);
        }
			else {
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
	}

    private void insertFixup(Node z) {
        Node y;
        while (z.p.color_red) { // while my father is red...
            if (z.p == z.p.p.left) { // if my father is the left child of my grandfather...
                y = z.p.p.right; // my uncle will be the right child of my granfather;
                if (y.color_red) { // case 1: while repeats only if y.color_red; if my uncle is red:
                    z.p.color_red = y.color_red = false; // my father and uncle become black;
                    z.p.p.color_red = true; // my grandfather become red;
                    z = z.p.p; // I go up 2 levels on the tree;
                }
                else { // uncle is black:
                    if (z == z.p.right) { // case 2: if I am the righ child of my father:
                        z = z.p; // I go up 1 level on the tree;
                        this.leftRotate(z); // then I perform a left rotation;
                    }
                    // case 3:
                    z.p.color_red = false; // my father become black;
                    z.p.p.color_red = true; // my grandfather become red;
                    this.rightRotate(z.p.p); // my grandfather perform a right rotation;
                }
            }
            else { // if my father is the right child of my grandfather... repeat the code above changing right/left;
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

        x.right = y.left; // set y
        if (y.left != RBTree.nil) y.left.p = x; // turn y's left subtree into x's right subtree
        y.p = x.p; // link x's parent to y

        if (x.p == RBTree.nil) this.root = y;
        else if (x == x.p.left) x.p.left = y;
        else x.p.right = y;

        y.left = x; // put x on y's left
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
        boolean yOriginalcolor_red = y.color_red;

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
            yOriginalcolor_red = y.color_red;
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

        if (!yOriginalcolor_red) this.removeFixup(x);
    }

    private void transplant(Node u, Node v) {
        if (u.p == RBTree.nil) this.root = v;
        else if (u == u.p.left) u.p.left = v;
        else u.p.right = v;
        v.p = u.p;
    }

    private void removeFixup(Node x) {
        Node w; // w is my sibling

        while (x != this.root && !x.color_red) {
            if (x == x.p.left) { // if I am the left child of my father
                w = x.p.right; // my sibling will be the right child

                if (w.color_red) { // case 1: x's sibling is red
                    w.color_red = false;
                    x.p.color_red = true;
                    this.leftRotate(x.p);
                    w = x.p.right;
                }
                if (!w.left.color_red && !w.right.color_red) { // case 2: my sibling and nephews are black
                    w.color_red = true;
                    x = x.p;
                }
                else {
                    if (!w.right.color_red) { // case 3: my sibling and right nephew are black, but my left nephew is red
                        w.left.color_red = false;
                        w.color_red = true;
                        this.rightRotate(w);
                        w = x.p.right;
                    }
                    // case 4: my sibling is black and my right nephew is red
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
                if (!w.right.color_red && !w.left.color_red) { // case 2
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
        x.color_red = false; // x color now is black
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

	public Node search(int k){
        Node aux = root.find(k);
        if(aux.value != k) return null;
        else return aux;
    }

    public void graph() {
        System.out.println("digraph RBTree {");
        this.root.graph();
        System.out.println("\tnil [style = filled, fillcolor = black, fontcolor = white];");
        System.out.println("}");
    }
}
