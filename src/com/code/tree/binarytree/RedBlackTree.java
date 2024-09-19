package com.code.tree.binarytree;

public class RedBlackTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    //将黑叶子节点设置为特殊值
    private final RBNode<K, V> Nil;

    public RedBlackTree() {
        super();
        Nil = new RBNode<>();
        root = Nil;
    }

    /**
     * 插入一个键值对
     *
     * 先将键值对(默认为红色)正常插入到相应位置
     * 然后判断是否被破坏性质：
     * 1. 插入的根节点：直接变黑就行了
     * 2. 插入节点的叔叔是红色：叔、父、爷变色，爷爷节点变成插入节点
     * 3. 插入节点的叔叔是黑色：对爷爷节点(LL, LR, RR, RL)旋转，然后变色
     *
     * @param key 键，非空
     * @param val 值，非空
     */
    @Override
    public void insert(K key, V val) {
        RBNode<K, V> newNode = new RBNode<>(key, val);
        newNode.setLeft(Nil);
        newNode.setRight(Nil);

        RBNode<K, V> current = (RBNode<K, V>) root;
        RBNode<K, V> parent = null;

        //寻找插入位置
        while (current != Nil) {
            parent = current;

            int cmp = current.getKey().compareTo(newNode.getKey());
            if (cmp < 0) {
                current = (RBNode<K, V>) current.getRight();
            } else if (cmp > 0) {
                current = (RBNode<K, V>) current.getLeft();
            } else {
                return;
            }
        }

        newNode.setParent(parent);

        if (parent == null) {
            root = newNode;
        } else if (parent.getKey().compareTo(newNode.getKey()) < 0) {
            parent.setRight(newNode);
        } else {
            parent.setLeft(newNode);
        }

        //检测红黑树的性质是否被破坏(根叶黑、不红红)
        //违反根叶黑
        if (newNode.getParent() == null) {
            newNode.setColor(BLACK);
            return;
        }

        //无爷爷节点，则不需要调整
        if (newNode.getParent().getParent() == null) {
            return;
        }

        //判断不红红
        fixInsert(newNode);
    }

    /**
     * 插入新节点后修复红黑树的性质
     *
     * 如果该节点的父亲为红色，则需要进行修复(违反了不红红)，判断叔叔节点的颜色决定修复方案
     * 1. 叔叔节点为红色：叔父爷翻转颜色，爷爷成为插入节点
     * 2. 叔叔节点为黑色：(LL, LR, RR, RL)旋转，然后变色
     *
     * @param current 需要修复的节点
     */
    private void fixInsert(RBNode<K, V> current) {
        RBNode<K, V> uncle = null;

        //不断修复违反不红红性质的节点
        while (current.getParent().getColor() == RED) {
            if (current.getParent().getParent().getLeft() == current.getParent()) {
                uncle = (RBNode<K, V>) current.getParent().getParent().getRight();

                if (uncle.getColor() == RED) {
                    uncle.flipColor();
                    current.getParent().flipColor();
                    current.getParent().getParent().flipColor();

                    current = current.getParent().getParent();
                } else {
                    if (current.getParent().getRight() == current) {    //LR型
                        current = current.getParent();
                        leftRotate(current);
                    }
                    //LR左旋后变成LL型，对相应节点进行变色后右旋
                    current.getParent().flipColor();
                    current.getParent().getParent().flipColor();
                    rightRotate(current.getParent().getParent());
                }
            } else {
                uncle = (RBNode<K, V>) current.getParent().getParent().getLeft();

                if (uncle.getColor() == RED) {
                    uncle.flipColor();
                    current.getParent().flipColor();
                    current.getParent().getParent().flipColor();

                    current = current.getParent().getParent();
                } else {
                    if (current.getParent().getLeft() == current) {     //RL型
                        current = current.getParent();
                        rightRotate(current);
                    }
                    //RL右旋后变成RR型，对相应节点进行变色后左旋
                    current.getParent().flipColor();
                    current.getParent().getParent().flipColor();
                    leftRotate(current.getParent().getParent());
                }
            }

            if (current == root) {
                break;
            }

            //修复根叶黑
            ((RBNode<K, V>) root).setColor(BLACK);
        }

    }

    /**
     * 删除一个键值对
     *
     * 通过key迭代寻找删除节点
     * 1. 不存在，返回
     * 2. 有一个子节点(只可能红色为孩子，黑色为父亲)：提上来，再进行变色
     * 4. 有两个子节点，把左子树最大的节点提上来/右子树最小的节点提上来，然后问题转化为删除提上来的节点(最终转化为了删除没有孩子的节点)
     * 5. 没有孩子的节点：
     *      a. 为红节点：删除后无需调整
     *      b. 为黑节点(变成双黑节点)：
     *          1)兄弟是黑色
     *              a). 兄弟至少有一个红孩子：(LL, LR, RR, RL)变色+旋转且双黑变单黑
     *              b). 兄弟孩子都是黑色：兄弟变红，双黑上提
     *          2)兄弟是红色：兄父变色，朝双黑旋转
     *
     * @param key 键，非空
     */
    @Override
    public void delete(K key) {
        deleteHelper((RBNode<K, V>) root, key);
    }

    /**
     * 用于删除红黑树中指定键的辅助方法
     * 该方法通过迭代来定位要删除的节点，并调整树以维持红黑树的性质
     *
     * @param current 当前遍历到的树节点，从根节点开始
     * @param key     要删除的节点所包含的键
     */
    private void deleteHelper(RBNode<K, V> current, K key) {
        // 寻找要删除的节点
            while (current != Nil) {
            int cmp = current.getKey().compareTo(key);
            if (cmp < 0) {
                current = (RBNode<K, V>) current.getRight();
            } else if (cmp > 0) {
                current = (RBNode<K, V>) current.getLeft();
            } else {
                // 找到了要删除的节点
                break;
            }
        }

        // 如果当前节点是Nil，说明要删除的节点不存在，直接返回
        if (current == Nil) {
            return;
        }

        // 以下是删除节点并维持红黑树性质的逻辑

        RBNode<K, V> child = null;
        RBNode<K, V> temp = current;
        boolean tempColor = temp.getColor();

        // 如果当前节点的左子节点是Nil，说明当前节点只有一个右子节点
        if (current.getLeft() == Nil) {
            child = (RBNode<K, V>) current.getRight();
            transplant(current, (RBNode<K, V>) current.getRight());
        }
        // 如果当前节点的右子节点是Nil，说明当前节点只有一个左子节点
        else if (current.getRight() == Nil) {
            child = (RBNode<K, V>) current.getLeft();
            transplant(current, (RBNode<K, V>) current.getLeft());
        }
        // 如果当前节点既有左子节点又有右子节点，需要找到当前节点的后继节点（即右子树中键值最小的节点）
        else {
            temp = (RBNode<K, V>) findMin(current.getRight());
            tempColor = temp.getColor();
            child = (RBNode<K, V>) temp.getRight();

            // 如果后继节点是当前节点的直接右子节点，简化调整过程
            if (temp.getParent() == current) {
                child.setParent(temp);
            } else {
                // 否则，需要先将后继节点的右子树替换为其自身的右子树，再进行后续调整
                transplant(temp, (RBNode<K, V>) temp.getRight());
                temp.setRight(current.getRight());
                temp.getParent().setParent(temp);
            }

            // 用后继节点替换当前节点，并将当前节点的左子树和颜色赋给后继节点
            transplant(current, temp);
            temp.setLeft(current.getLeft());
            ((RBNode<K, V>) temp.getLeft()).setParent(temp);
            temp.setColor(current.getColor());
        }

        // 如果删除的是黑色节点，那么就破坏了红黑树的性质，需要进行修复
        if (tempColor == BLACK) {
            fixDelete(child);
        }
    }

    /**
     * 修复删除黑色节点后被破坏的红黑树性质
     *
     * @param current 需要修复的节点
     */
    private void fixDelete(RBNode<K, V> current) {
        RBNode<K, V> sibling;
        while (current.getColor() == BLACK && current != root) {
            if (current == current.getParent().getLeft()) {
                sibling = (RBNode<K, V>) current.getParent().getRight();

                if (sibling.getColor() == RED) {
                    sibling.flipColor();
                    current.getParent().flipColor();
                    leftRotate(current.getParent());
                    sibling = (RBNode<K, V>) current.getParent().getRight();
                }

                if (((RBNode<K, V>)sibling.getLeft()).getColor() == BLACK && ((RBNode)sibling.getRight()).getColor() == BLACK) {
                    sibling.setColor(RED);
                    current = current.getParent();
                } else {
                    if (((RBNode<K, V>) sibling.getRight()).getColor() == BLACK) {
                        ((RBNode<K, V>) sibling.getLeft()).setColor(BLACK);
                        sibling.flipColor();
                        rightRotate(sibling);
                        sibling = (RBNode<K, V>) current.getParent().getRight();
                    }
                    sibling.setColor(current.getParent().getColor());
                    current.getParent().setColor(BLACK);
                    ((RBNode<K, V>) sibling.getRight()).setColor(BLACK);
                    leftRotate(current.getParent());
                    current = (RBNode<K, V>) root;
                }

            } else {
                sibling = (RBNode<K, V>) current.getParent().getLeft();

                if (sibling.getColor() == RED) {
                    sibling.flipColor();
                    current.getParent().flipColor();
                    rightRotate(current.getParent());
                    sibling = (RBNode<K, V>) current.getParent().getLeft();
                }

                if (((RBNode<K, V>)sibling.getRight()).getColor() == BLACK && ((RBNode)sibling.getLeft()).getColor() == BLACK) {
                    sibling.setColor(RED);
                    current = current.getParent();
                } else {
                    if (((RBNode<K, V>) sibling.getLeft()).getColor() == BLACK) {
                        ((RBNode<K, V>) sibling.getRight()).setColor(BLACK);
                        sibling.flipColor();
                        leftRotate(sibling);
                        sibling = (RBNode<K, V>) current.getParent().getLeft();
                    }
                    sibling.setColor(current.getParent().getColor());
                    current.getParent().setColor(BLACK);
                    ((RBNode<K, V>) sibling.getLeft()).setColor(BLACK);
                    rightRotate(current.getParent());
                    current = (RBNode<K, V>) root;
                }
            }

        }
        ((RBNode<K, V>) root).setColor(BLACK);
    }

    /**
     * 用一个子树替换另一个子树
     * 这个方法主要用于在删除或插入节点后，维持红黑树的性质
     * 它通过重新连接节点与其父节点的关系，来实现子树的替换
     *
     * @param node 要被替换的子树的根节点
     * @param newNode 替换后的子树的根节点
     */
    private void transplant(RBNode<K, V> node, RBNode<K, V> newNode) {
        // 如果要替换的节点是根节点，则直接将根节点替换为新节点
        if (node.getParent() == null) {
            root = newNode;
        }
        // 如果要替换的节点是其父节点的左子节点，则将父节点的左指针指向新节点
        else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(newNode);
        }
        // 如果要替换的节点是其父节点的右子节点，则将父节点的右指针指向新节点
        else {
            node.getParent().setRight(newNode);
        }

        // 设置新节点的父节点为被替换节点的父节点，以维护树的结构
        newNode.setParent(node.getParent());
    }


    /**
     * 查找以给定节点为根的子树中的最小节点
     *
     * @param current 当前节点，为查找的起始点
     * @return 返回以给定节点为根的子树中的最小节点
     * @throws BinaryTreeException 如果树为空，则抛出此异常
     */
    @Override
    public Node<K, V> findMin(Node<K, V> current) {
        // 检查树是否为空，如果为空则抛出异常
        if (isEmpty()) throw new BinaryTreeException("The tree is empty!");

        // 如果当前节点的左子节点为Nil，说明当前节点为最小节点
        if (current.getLeft() == Nil) return current;

        // 递归地在当前节点的左子树中查找最小节点
        return findMin(current.getLeft());
    }


    /**
     * 实现一个节点的左旋转
     * 1. 先将节点(node)的右孩子(x)的左孩子(T2)设置为该节点的右孩子
     * 2. 判断该设置的右孩子是否为空。如果不为空就更新其父亲节点
     * 3. 更新右孩子的父亲节点(如果旋转节点为根节点则将右孩子更新为根节点，旋转节点为右孩子则......)
     * 4. 更新旋转节点的父亲节点，更新右孩子的左孩子
     *
     * @param node 需要旋转的节点
     */
    private void leftRotate(RBNode<K, V> node) {
        RBNode<K, V> x = (RBNode<K, V>) node.getRight();
        RBNode<K, V> T2 = (RBNode<K, V>) x.getLeft();

        node.setRight(T2);
        if (T2 != Nil) {
            T2.setParent(node);
        }

        if (node.getParent() == null) {
            root = x;
        } else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(x);
        } else {
            node.getParent().setRight(x);
        }

        x.setParent(node.getParent());
        node.setParent(x);
        x.setLeft(node);
    }

    /**
     * 实现一个节点的右旋转
     * 1. 先将节点(node)的左孩子(x)的右孩子(T2)设置为该节点的左孩子
     * 2. 判断该设置的左孩子是否为空。如果不为空就更新其父亲节点
     * 3. 更新左孩子的父亲节点(如果旋转节点为根节点则将左孩子更新为根节点，旋转节点为左孩子则......)
     * 4. 更新旋转节点的父亲节点，更新左孩子的右孩子
     *
     * @param node 需要旋转的节点
     */
    private void rightRotate(RBNode<K, V> node) {
        RBNode<K, V> x = (RBNode<K, V>) node.getLeft();
        RBNode<K, V> T2 = (RBNode<K, V>) x.getRight();

        node.setLeft(T2);
        if (T2 != Nil) {
            T2.setParent(node);
        }

        if (node.getParent() == null) {
            root = x;
        } else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(x);
        } else {
            node.getParent().setRight(x);
        }

        x.setParent(node.getParent());
        node.setParent(x);
        x.setRight(node);
    }

    @Override
    public void InOrderTraversal() {
        if (isEmpty()) throw new BinaryTreeException("The tree is empty!");
        InOrderTraversal((RBNode<K, V>) root);
    }

    private void InOrderTraversal(RBNode<K, V> current) {
        if (current == Nil) return;

        InOrderTraversal((RBNode<K, V>) current.getLeft());
        System.out.print(current.getVal() + " ");
        InOrderTraversal((RBNode<K, V>) current.getRight());
    }

//    /**
//     * 实现一个节点的左旋转。
//     *
//     * 将该节点旋转成为右孩子节点的左孩子。
//     * 如果该节点与右孩子节点的左孩子冲突，则需要将其右孩子的左孩子变成该节点的右孩子并更新各节点的父节点
//     *
//     * @param node 需要左旋转的节点，且node非空
//     * @return 实现左旋转之后该位置的节点
//     */
//    private RBNode<K, V> leftRotate(RBNode<K, V> node) {
//        RBNode<K, V> x = (RBNode<K, V>) node.getRight();
//        RBNode<K, V> T2 = (RBNode<K, V>) x.getLeft();
//
//        node.setRight(T2);
//        if (T2 != Nil) {
//            T2.setParent(node);
//        }
//
//        x.setParent(node.getParent());
//        node.setParent(x);
//        x.setRight(node);
//
//        return x;
//    }
//
//    /**
//     * 实现一个节点的右旋转。
//     *
//     * 将该节点旋转成为左孩子节点的右孩子。
//     * 如果该节点与左孩子节点的右孩子冲突，则需要将其左孩子的右孩子变成该节点的左孩子并更新各节点的父节点
//     *
//     * @param node 需要左旋转的节点，且非空
//     * @return 实现左旋转之后该位置的节点
//     */
//    private RBNode<K, V> rightRotate(RBNode<K, V> node) {
//        RBNode<K, V> x = (RBNode<K, V>) node.getLeft();
//        RBNode<K, V> T2 = (RBNode<K, V>) x.getRight();
//
//        node.setLeft(T2);
//        if (T2 != Nil) {
//            T2.setParent(node);
//        }
//
//        x.setParent(node.getParent());
//        node.setParent(x);
//        x.setRight(node);
//
//        return x;
//    }
}
