package datastruct;

public class Node {

	Node leftChild;
	Node rightChild;
	Node parent;
	
	public Node(){
		leftChild = null;
		rightChild = null;
		parent = null;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public Node getLeftChild(){
		return leftChild;
	}
	
	public Node getRightChild(){
		return rightChild;
	}
	
	public void setParent(Node n){
		parent = n;
	}
	
	public void setLeftChild(Node n){
		leftChild = n;
	}
	
	public void setRightChild(Node n){
		rightChild = n;
	}
	
	public boolean isLeaf(){
		if (leftChild == null && rightChild == null){
			return true;
		}
		return false;
	}
}
