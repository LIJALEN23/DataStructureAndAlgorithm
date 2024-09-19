package com.code.tree.binarytree;

public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    public AVLTree() {
        super();
    }

    /**
     * 获取一个节点的高度。
     *
     * @param node 需要获取高度的节点
     */
    private int height(AVLNode<K, V> node) {
        return node == null ? 0 : node.getHeight();
    }

    /**
     * 更新节点的高度
     *
     * @param node 需要更新高度的节点*/
    private int updateHeight(AVLNode<K, V> node) {
        return 1 + Math.max(height((AVLNode<K, V>) node.getRight()), height((AVLNode<K, V>) node.getLeft()));
    }

    /**
     * 获取一个节点的平衡因子
     *
     * 平衡因子 = 左子树高度 - 右子树高度(-1, 0 1不需要进行旋转)
     *
     * @param node 需要获取平衡因子的节点
     */
    private int getBalanceFactor(AVLNode<K, V> node) {
        return node == null ? 0 : height((AVLNode<K, V>) node.getLeft()) - height((AVLNode<K, V>) node.getRight());
    }

    /**
     * 实现一个节点的左旋转。
     *
     * 当节点的平衡因子等于-2时，进行左旋转。
     * 将该节点旋转成为右孩子节点的左孩子。
     * 如果该节点与右孩子节点的左孩子冲突，则需要将其右孩子的左孩子变成该节点的右孩子
     *
     * @param node 需要左旋转的节点，且node非空
     * @return 实现左旋转之后该位置的节点
     */
    private AVLNode<K, V> leftRotate(AVLNode<K, V> node) {
        //获取节点
        AVLNode<K, V> x = (AVLNode<K, V>) node.getRight();
        AVLNode<K, V> T2 = (AVLNode<K, V>) x.getLeft();

        //进行旋转
        x.setLeft(node);
        node.setRight(T2);

        //更新节点高度
        x.setHeight(x.getHeight());
        node.setHeight(updateHeight(node));

        return x;
    }

    /**
     * 实现一个节点的右旋转。
     *
     * 当节点的平衡因子等于2时，进行右旋转。
     * 将该节点旋转成为左孩子节点的右孩子。
     * 如果该节点与左孩子节点的右孩子冲突，则需要将其左孩子的右孩子变成该节点的左孩子
     *
     * @param node 需要左旋转的节点，且非空
     * @return 实现左旋转之后该位置的节点
     */
    private AVLNode<K, V> rightRotate(AVLNode<K, V> node) {
        //获取节点
        AVLNode<K, V> x = (AVLNode<K, V>) node.getLeft();
        AVLNode<K, V> T2 = (AVLNode<K, V>) x.getRight();

        //进行旋转
        x.setRight(node);
        node.setLeft(T2);

        //更新高度
        x.setHeight(x.getHeight());
        node.setHeight(updateHeight(node));

        return x;
    }

    /**
     * 插入一个键值对
     *
     * @param key 键，非空
     * @param val 值，非空
     */
    @Override
    public void insert(K key, V val) {
        root = insert((AVLNode<K, V>) root, key, val);
    }

    /**
     * insert的辅助方法。
     *
     * 先正常插入一个节点到相应位置，
     * 然后递归更新每个节点的高度，
     * 同时递归判断每个节点的平衡因子是否需要进行旋转操作。
     * 1. LL：失衡节点BalanceFactor = 2且失衡节点左孩子BalanceFactor = 1(只需要进行一次左旋)
     * 2. LR：失衡节点BalanceFactor = 2且失衡节点左孩子BalanceFactor = -1(先对左旋左孩子，再右旋)
     * 3. RR：失衡节点BalanceFactor = -2且失衡节点右孩子BalanceFactor = -1(只需要进行一次右旋)
     * 4. RL：失衡节点BalanceFactor = -2且失衡节点右孩子BalanceFactor = 1(先对右旋右孩子，再左旋)
     *
     *
     * @param current 递归调用时，当前的节点
     * @param key 键，非空
     * @param val 值，非空
     */
    private AVLNode<K, V> insert(AVLNode<K, V> current, K key, V val) {
        if (current == null) return new AVLNode<>(key, val);

        int cmp = current.getKey().compareTo(key);
        if (cmp > 0) {
            current.setLeft(insert((AVLNode<K, V>) current.getLeft(), key, val));
        } else if (cmp < 0){
            current.setRight(insert((AVLNode<K, V>) current.getRight(), key, val));
        } else {
            return current;
        }

        //更新节点的高度
        current.setHeight(updateHeight(current));

        int balanceFactor = getBalanceFactor(current);

        //平衡树
        //LL
        if (balanceFactor > 1 && key.compareTo(current.getLeft().getKey()) < 0) {
            return rightRotate(current);
        }

        //LR
        if (balanceFactor > 1 && key.compareTo(current.getLeft().getKey()) > 0) {
            current.setLeft(leftRotate((AVLNode<K, V>) current.getLeft()));
            return rightRotate(current);
        }

        //RR
        if (balanceFactor < -1 && key.compareTo(current.getRight().getKey()) > 0) {
            return leftRotate(current);
        }
        //RL
        if (balanceFactor < -1 && key.compareTo(current.getRight().getKey()) < 0) {
            current.setRight(current.getRight());
            return leftRotate(current);
        }

        return current;
    }

    /**
     * 删除一个键值对
     *
     * @param key 键，非空
     */
    @Override
    public void delete(K key) {
        if (isEmpty()) throw new BinaryTreeException("The tree is empty!");
        root = delete((AVLNode<K, V>) root, key);
    }

    /**
     * delete的辅助方法
     *
     * 通过key递归寻找删除节点
     * 1. 不存在，返回
     * 2. 没有子节点：直接删除
     * 3. 有一个子节点：把子节点提上来就行
     * 4. 有两个子节点：把左子树最大的节点提上来/右子树最小的节点提上来，然后递归删除提上来的节点
     * 判断删除后的AVL树性质是否被破坏，进行(LL, LR, RR, RL)旋转操作
     *
     * @param current 递归调用时，当前的节点
     * @param key 键，非空
     */
    private AVLNode<K, V> delete(AVLNode<K, V> current, K key) {
        if (current == null) {
            return null;
        }

        int cmp = current.getKey().compareTo(key);
        if (cmp > 0) {
            current.setLeft(delete((AVLNode<K, V>) current.getLeft(), key));
        } else if (cmp < 0) {
            current.setRight(delete((AVLNode<K, V>) current.getRight(), key));
        } else {
            if (current.getRight() == null && current.getLeft() == null) {  //没有子节点
                return null;
            } else if (current.getRight() != null && current.getLeft() != null) {   //有两个子节点
                var left = current.getLeft();
                current = (AVLNode<K, V>) findMin(current.getRight());
                current.setLeft(left);
                current.setRight(delete((AVLNode<K, V>) current.getRight(), current.getKey()));
            } else {    //只有一个子节点
                current = (AVLNode<K, V>) (current.getLeft() == null ? current.getRight() : current.getLeft());
            }
        }

        current.setHeight(updateHeight(current));

        int balanceFactor = getBalanceFactor(current);

        //LL
        if (balanceFactor > 1 && getBalanceFactor((AVLNode<K, V>) current.getLeft()) >= 0) {
            return rightRotate(current);
        }

        //LR
        if (balanceFactor > 1 && getBalanceFactor((AVLNode<K, V>) current.getLeft()) < 0) {
            current.setLeft(leftRotate((AVLNode<K, V>) current.getLeft()));
            return rightRotate(current);
        }

        //RR
        if (balanceFactor < -1 && getBalanceFactor((AVLNode<K, V>) current.getRight()) <= 0) {
            return leftRotate(current);
        }

        //RL
        if (balanceFactor < -1 && getBalanceFactor((AVLNode<K, V>) current.getRight()) > 0) {
            current.setRight(leftRotate((AVLNode<K, V>) current.getRight()));
            return leftRotate(current);
        }

        return current;
    }
}
