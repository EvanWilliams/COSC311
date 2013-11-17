import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<E extends Comparable<E>> {

	protected Node<E> root;
	boolean addReturn;
	protected E deleteReturn;

	public BinarySearchTree() {
		root = null;
	}

	protected BinarySearchTree(Node<E> root) {
		this.root = root;

	}

	public boolean isEmpty() {
		if (root == null)
			return true;
		else
			return false;
	}

	public BinarySearchTree(E data, BinarySearchTree<E> leftTree,
			BinarySearchTree<E> rightTree) {
		root = new Node<E>(data);
		if (leftTree != null) {
			root.left = leftTree.root;
		} else {
			root.left = null;
		}
		if (rightTree != null) {
			root.right = rightTree.root;
		} else {
			root.right = null;
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		preOrderTraverse(root, 1, sb);
		return sb.toString();
	}

	private void preOrderTraverse(Node<E> node, int depth, StringBuilder sb) {

		for (int i = 1; i < depth; i++) {
			sb.append(" ");
		}
		if (node == null) {
			sb.append("null\n");
		} else {
			sb.append(node.toString());
			sb.append("\n");
			preOrderTraverse(node.left, depth + 1, sb);
			preOrderTraverse(node.right, depth + 1, sb);
		}
	}

	// THIS IS NOT YET WORKING MAKE SURE IT WORKS BEFORE IMPLEMETING

	public void breadthFirstTraversal(Node<E> e) {
		breadthFirstTraversAll(e);

	}

	public void breadthFirstTraversal() {

		breadthFirstTraversAll(root);

	}

	// TODO Make sure that this breadthFirst traversal works correctly and
	// distinguishes what level it is on
	private void breadthFirstTraversAll(Node<E> root) {
		int level = 0;
		boolean DoubleNull = false;
		Node<E> cur = new Node<E>();
		Queue<Node<E>> Queue = new LinkedList<Node<E>>();
		if (root != null) {
			Queue.offer(root);
			Queue.offer(new Node<E>());
		}

		while (!Queue.isEmpty()) {
			if (Queue.peek().data == null) {
				if (DoubleNull == false) {
					Queue.poll();
					System.out.println();
					Queue.offer(new Node<E>(null));
					DoubleNull = true;

				} else if (DoubleNull == true)
					break;
			} else {
				cur = Queue.poll();
				DoubleNull = false;

				// for(int i = 0; i <level; i++){
				// System.out.print("\t");
				// }
				System.out.print(cur.toString());

				if (cur.hasLeft()) {
					Queue.offer(cur.left);
				}
				if (cur.hasRight()) {
					Queue.offer(cur.right);
				}

			}
		}
		System.out.println();

	}

	// Check implementation I may have made it harder on my self to fin numbers
	private void breadthFirstTraverSome(Node<E> root, int a) {

		Node<E> cur = new Node<E>(null);
		int level = 0;
		boolean DoubleNull = false;
		Queue<Node<E>> Queue = new LinkedList<Node<E>>();
		Queue.offer(root);
		Queue.offer(new Node<E>(null));

		for (int i = 0; i < a; i++)
			if (!Queue.isEmpty()) {
				if (Queue.peek() == null) {
					if (DoubleNull == false) {
						level++;
						Queue.offer(new Node<E>(null));
						DoubleNull = true;
						i--;

					}
				}
				DoubleNull = false;
				cur = Queue.poll();
				System.out.println(" ");
				for (int j = 0; j < level; j++)
					System.out.println(cur.toString());

				if (cur.hasLeft()) {
					Queue.offer(cur.left);
				}
				if (cur.hasRight()) {
					Queue.offer(cur.right);
				}

			}

	}

	private E findLargestChild(Node<E> parent) {

		if (parent.right.right == null) {
			E returnValue = parent.right.data;
			parent.right = parent.right.left;
			return returnValue;
		} else {
			return findLargestChild(parent.right);
		}
	}

	public E delete(E target) {
		root = delete(root, target);
		return deleteReturn;
	}

	private Node<E> delete(Node<E> localRoot, E item) {
		if (localRoot == null) {
			deleteReturn = null;
			return localRoot;
		}

		int compResult = item.compareTo(localRoot.data);
		if (compResult < 0) {
			localRoot.left = delete(localRoot.left, item);
			return localRoot;
		} else if (compResult > 0) {
			localRoot.right = delete(localRoot.right, item);
			return localRoot;
		} else {
			deleteReturn = localRoot.data;
			if (localRoot.left == null) {
				return localRoot.right;
			} else if (localRoot.right == null) {
				return localRoot.left;
			} else {
				if (localRoot.left.right == null) {
					localRoot.data = localRoot.left.data;
					localRoot.left = localRoot.left.left;
					return localRoot;
				} else {
					localRoot.data = findLargestChild(localRoot.left);
					return localRoot;
				}

			}
		}
	}

	public E Find(E search) {
		return findE(root, search);
	}
	public Node<E> findNode(E search){
		return findNode(root, search);
	}
	// TODO Make sure the search function works in different respects so that
	// display and retrieve can
	// use it
	private Node<E> findNode(Node<E> localRoot, E item) {

		if (localRoot == null) {
			return null;
		} else if (item.compareTo(localRoot.data) == 0) {
			return localRoot;
		} else if (item.compareTo(localRoot.data) < 0) {
			return findNode(localRoot.left, item);
		} else {
			return findNode(localRoot.right, item);
		}

	}

	private E findE(Node<E> localRoot, E item) {
		Node<E> e = findNode(localRoot, item);
		if (e != null) {
			return e.data;
		}
		return null;
	}

	public void Modify(E search, E replace) {
		Modify(root, search, replace);

	}

	private Node<E> Modify(Node<E> localRoot, E item, E replace) {

		if (localRoot == null) {
			return null;
		} else if (item.compareTo(localRoot.data) == 0) {
			localRoot.data = replace;
			return localRoot;
		} else if (item.compareTo(localRoot.data) < 0) {
			return Modify(localRoot.left, item, replace);
		} else {
			return Modify(localRoot.right, item, replace);
		}

	}

	public boolean add(E item) {
		root = add(root, item);
		return addReturn;
	}

	private Node<E> add(Node<E> localRoot, E item) {
		if (localRoot == null) {
			addReturn = true;
			return new Node<E>(item);
		} else if (item.compareTo(localRoot.data) == 0) {
			addReturn = false;
			return localRoot;
		} else if (item.compareTo(localRoot.data) < 0) {
			localRoot.left = add(localRoot.left, item);
			return localRoot;
		} else {
			localRoot.right = add(localRoot.right, item);
			return localRoot;
		}
	}

	protected static class Node<E> {
		protected E data;

		public E getData() {
			return data;
		}

		public Node<E> getLeft() {
			return left;
		}

		public Node<E> getRight() {
			return right;
		}

		protected Node<E> left;

		protected Node<E> right;

		public Node() {
			this.data = null;
			left = null;
			right = null;
		}

		public Node(E data) {
			this.data = data;
			left = null;
			right = null;
		}

		public boolean hasLeft() {
			if (this.left != null)
				return true;
			else
				return false;
		}

		public boolean hasRight() {
			if (this.right != null)
				return true;
			else
				return false;
		}

		public String toString() {
			return data.toString();
		}
	}

}
