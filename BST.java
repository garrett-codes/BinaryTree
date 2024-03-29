import java.util.*;

public class BST<T extends Comparable<T>> {

	// top node/ head of the tree
	private TreeNode root = null;

	public BST(T data) {
		this.root = new TreeNode(data);
	}

	// inner class of BinaryTree that consists of the nodes of the tree
	class TreeNode {
		TreeNode(T data) {
			this.data = data;
		}
		T data;
		TreeNode left, right = null;
	}

	// appends the node to the endd of a leaf
	private void append(T newData, TreeNode node) {
		// tersts whether the data gets passed to the right or left
		if (newData.compareTo(node.data) > 0) {
			if (node.right != null) {
				append(newData, node.right);
			}
			else {
				node.right = new TreeNode(newData);
			}
		}
		else if ((newData.compareTo(node.data) < 0)) {
			if (node.left != null) {
				append(newData, node.left);
			}
			else {
				node.left = new TreeNode(newData);
			}
		}
		// incase the data is equal to the node then have to send it as close as possible
		else {
			if (node.left != null) {
				TreeNode curr = node.left;
				while (curr.right != null) {
					curr = curr.right;
				}
				curr.right = new TreeNode	(newData);
			}
			else {
				node.left = new TreeNode(newData);
			}
		}
	}

	// encapsulates the append code
	public void append(T newData) {
		append(newData, root);
	}

	private TreeNode find(T target, TreeNode node) {
		if (target.compareTo(node.data) > 0) {
			if (node.right != null) {
				return find(target, node.right);
			}
			else {
				return null;
			}
		}
		else if ((target.compareTo(node.data) < 0)) {
			if (node.left != null) {
				return find(target, node.left);
			}
			else {
				return null;
			}
		}
		// incase the data is equal
		return node;
	}

	// deletes the target node in the tree
	private TreeNode delete(T target, TreeNode targetNode, TreeNode prev) {
		if (target.compareTo(targetNode.data) > 0) {
			return delete(target, targetNode.right, targetNode);
		}
		else if (target.compareTo(targetNode.data) < 0) {
			return delete(target, targetNode.left, targetNode);
		}
		else {
			// when the node is found 
			if (prev.left == targetNode) {
				if (targetNode.right == null && targetNode.left == null) {
					prev.left = null;
				}
				TreeNode temp = targetNode;
				TreeNode trail = prev;
				while (temp.right != null) {
					trail = temp;
					temp = temp.right;
				}
				if (temp.left == null) {
					temp.left = targetNode.left;
					return targetNode;
				}
				else {
					TreeNode leftTemp = temp.left;
					while (leftTemp.right != null) {
						leftTemp = leftTemp.right;
					}
					leftTemp.right = targetNode.left;
				}
				temp.right = targetNode.right;
				prev.left = temp;
				trail.right = temp.left;	
				targetNode.left = null;
				targetNode.right = null;
				return targetNode;
			}
			else if (prev.right == targetNode) {

				if (targetNode.right == null && targetNode.left == null) {
					prev.left = null;
				}
				if (targetNode.right == null ) {
					prev.right = prev.left;
					return targetNode;
				}
				else if (targetNode.left != null) {
					TreeNode trail = targetNode;
					TreeNode temp = targetNode.left;
					while (temp.right != null) {
						trail = temp;
						temp = temp.right; 
					}
					temp.right = targetNode.right;
					prev.right = temp;
					trail.right = temp.left;
					temp.left = targetNode.left;
					targetNode.left = null;
					targetNode.right = null;
					return targetNode;
				}
			}
			return null;
		}
	}

	// finds the target node in the tree
	public TreeNode find(T target) {
		return find(target, root);
	}

	// returns the target node in the tree
	public TreeNode get(T target) {
		return find(target, root);
	} 

	// public caller for delete
	public TreeNode delete(T target) {
		if (target.compareTo(root.data) > 0) {
			return delete(target, root.right, root);
		}
		else if (target.compareTo(root.data) < 0) {
			return delete(target, root.left, root);
		}
		else {
			TreeNode oldRoot = root;
			if (root.right == null && root.left == null) {
				root = null;
				return oldRoot;
			}
			else if (root.right == null) {
				root = root.left;
				return oldRoot;
			}
			TreeNode temp = root.right;
			TreeNode trail = oldRoot;
			while (temp.left != null) {
				temp = temp.left;
			} 
			temp.right = root.right;
			temp.left = root.left;
			root = temp;
			return oldRoot;
		}
	}


	//prefix traversal
	private void preOrder(TreeNode node, ArrayList<T> list) {
		if (node == null) return;
		list.add(node.data);
		preOrder(node.left, list);
		preOrder(node.right, list);
	}

	//caller for prefix
	public ArrayList<T> preTraverse() {
		ArrayList<T> list = new ArrayList<>();
		preOrder(root, list);
		return list;
	}

	//post order traversal
	private void postOrder(TreeNode node, ArrayList<T> list) {
		if (node == null) return;
		preOrder(node.left, list);
		preOrder(node.right, list);
		list.add(node.data);
	}

	//caller for postfix
	public ArrayList<T> postTraverse() {
		ArrayList<T> list = new ArrayList<>();
		postOrder(root, list);
		return list;
	}

	// inorder traversal
	private void inOrder(TreeNode node, ArrayList<T> list) {
		if (node == null)  return;
		inOrder(node.left, list);
		list.add(node.data);
		inOrder(node.right, list);
	}

	// caller for inorder traversal
	public ArrayList<T> traverse() {
		ArrayList<T> list = new ArrayList<>();
		inOrder(root, list);
		return list;
	}

	// returns max in the BST
	public T max() {
		TreeNode curr = root;
		while(curr.right != null) {
			curr = curr.right;
		}
		return curr.data;
	}

	// returns min in the BST
	public T min() {
		TreeNode curr = root;
		while(curr.left != null) {
			curr = curr.left;
		}
		return curr.data;
	}

	public boolean isBalanced() {
		int left = 0;
		int right = 0;
		TreeNode rNode = root;
		TreeNode lNode = root;
		if (root.right != null) {
			rNode = root.right;
		}	
		if (root.left != null) {
			lNode = root.left;
		}	
		while (rNode.right != null) {
			rNode = rNode.right;
			++right;
		}
		while (lNode.left != null) {
			lNode = lNode.left;
			++left;
		}

		if (left - right > -2 || left - right < 2) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSymmetrical() {
		ArrayList<T> list = postTraverse();

		int j = list.size() - 1;
		for (int i = 0; i < list.size() / 2; ++i) {
			if (list.get(i) != list.get(j--)) {
				return false;
			}
		}
		return true;
	}

	private void sum(TreeNode node, ArrayList<T> list) {
		if (node.right == null) {
			list.add(node.data);
		}
		else if (node.left == null) {
			list.add(node.data);
		}
		else {
			sum(node.right, list);
			sum(node.left, list);
		} 
	}

	public ArrayList<T> sumLeafNodes() {
		ArrayList<T> list = new ArrayList<>();
		sum(root, list);
		return list;
	}

	public ArrayList<T> nonRecursivePreOrderTraversal() {
		Stack<TreeNode> stack = new Stack<>();
		ArrayList<T> list = new ArrayList<>();
		stack.push(root);
		while (!stack.empty()) {
			TreeNode curr = stack.pop();
			if (curr != null) {
				list.add(curr.data);
				stack.push(curr.right);
				stack.push(curr.left);
			}
		}
		return list;
	}

	public TreeNode findTheKthNode(int target) {
		Stack<TreeNode> stack = new Stack<>();
		int count = 0;
		TreeNode curr = root;
		while (curr != null || !stack.empty()) {
			if (curr != null) {
				stack.push(curr);
				curr = curr.left;
			}
			else {
				curr = stack.pop();
				if (++count == target) {
					return curr;
				}
				curr = curr.right;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		BST<Integer> tree = new BST<>(4);
		tree.append(3);
		tree.append(6);
		tree.append(7);
		System.out.println(tree.find(1));
		System.out.println(tree.find(6));
		System.out.println("Deleted Node: " + tree.delete(3).data);
		System.out.println("Find deleted Node: " + tree.find(3));
		System.out.println("Other Nodes: " + tree.find(6).data);
		System.out.println("Other Nodes: " + tree.find(7).data);
		System.out.println("Root Node: " + tree.find(4));
		tree.append(1);
		tree.append(2);
		tree.append(5);
		System.out.println("Prefix: " + tree.preTraverse());
		System.out.println("Postfix: " + tree.postTraverse());
		System.out.println("In Order: " + tree.traverse());
		System.out.println("max: " + tree.max());
		System.out.println("min: " + tree.min());
		System.out.println("is balanced: " + tree.isBalanced());
		System.out.println("is symmetrical(false): " + tree.isSymmetrical());
		BST<Integer> sym = new BST<>(1);
		System.out.println("is symmetrical(true): " + sym.isSymmetrical());
		System.out.println("Sum: " + tree.sumLeafNodes());
		System.out.println("nonRecursivePreFixTraversal: " + tree.nonRecursivePreOrderTraversal());
		System.out.println("Find the kth node: " + tree.findTheKthNode(5).data);
	}
}