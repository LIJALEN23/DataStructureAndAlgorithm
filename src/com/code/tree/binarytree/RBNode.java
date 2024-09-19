package com.code.tree.binarytree;

public class RBNode<K extends Comparable<K>, V> extends TreeNode<K, V> {
    private boolean color;

    private RBNode<K, V> parent;


    public RBNode(K key, V val) {
        super(key, val);
        color = true;
        parent = null;
    }

    public RBNode() {
        super(null, null);
        color = false;
        parent = null;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean getColor() {
        return color;
    }

    public RBNode<K, V> getParent()  {
        return parent;
    }

    public void setParent(RBNode<K, V> parent) {
        this.parent = parent;
    }

    public void flipColor() {
        color = (color == true ? false : true);
    }
}
