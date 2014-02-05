package datastruct;

import java.util.Comparator;


public class HuffToken extends Node implements Comparable<HuffToken>{
	private String text;
	private int count;
	private String code;
	
	// constructor used when combining two children into a combined node
	public HuffToken(HuffToken a, HuffToken b){
		text = a.getText() + b.getText();
		count = a.getCount() + b.getCount();
		setLeftChild(a);
		setRightChild(b);
		code = "";
	}
	
	public HuffToken(String txt){
		text = txt;
		count = 1;
		code = "";
		leftChild = null;
		rightChild = null;
		parent = null;
	}
	
	public int getCount(){
		return count;
	}
	
	public String getCode(){
		return code;
	}

	public String getText(){
		return text;
	}
	
	public void incCount(){
		count += 1;
	}
	
	public void setParentNode(HuffToken p){
		this.setParent(p);
	}
	
	public void setLeftNode(HuffToken l){
		this.setParent(l);
	}
	
	public void setRightNode(HuffToken r){
		this.setParent(r);
	}
	
	public HuffToken getLeft(){
		return (HuffToken) this.getLeftChild();
	}

	public HuffToken getRight(){
		return (HuffToken) this.getRightChild();
	}
	
	public void printSubtree(){
		if (isLeaf()){
			System.out.println("LEAF : '" + getText() + "' : " + getCount());
		}else{
			System.out.println("NODE : '" + getText() + "' : " + getCount());
			if(getLeft() == null){
				System.out.println("left is null");
			}else{
				getLeft().printSubtree();
			}
			if(getRight() == null){
				System.out.println("right is null");
			}else{
				getRight().printSubtree();
			}
		}
	}

	public int compareTo(HuffToken other){
		return this.getCount() - other.getCount();
	}
	
	//use Collections.sort() to sort HuffTokens
	public static Comparator<HuffToken> HtComparator = new Comparator<HuffToken>(){
		public int compare(HuffToken a, HuffToken b){
			return a.getCount() - b.getCount();
		}
	};
}