import datastruct.HuffToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import java.io.IOException;
import java.io.FileNotFoundException;

public class Huffman {

	public static BufferedReader input;
	public static HashMap<String, HuffToken> tokenData;
	public static HuffToken root;
	public static HashMap<String,String> codes; 
	public static StringBuilder in;
	
	public static void init(String fileName){
		try{
			tokenData = new HashMap <String, HuffToken>();
			codes = new HashMap<String,String>();
			input = new BufferedReader(new FileReader(fileName));
			in = new StringBuilder();
			String line = "";
			while (input.ready()){
				line = input.readLine();
				//split into chars
				String[] tokens = line.split("");
				for (String t:tokens){
					if (t.length() >= 1){
						in.append(t);
						//increment the count for a previously found token or create a new one;
						if (tokenData.containsKey(t)){
							 tokenData.get(t).incCount();
						}else{
							HuffToken newHt = new HuffToken(t);
							tokenData.put(t, newHt);	
						}
					}
				}
			}
			
			//Create tree of results until we are left with one root node
			List<HuffToken> results = new ArrayList<HuffToken>(tokenData.values());
			Collections.sort(results);
			while(results.size() > 1){
				  HuffToken a = results.get(0);
				  results.remove(0);
				  HuffToken b = results.get(0);
				  results.remove(0);
				  HuffToken combined = new HuffToken(a,b);
				  a.setParent(combined);
				  b.setParent(combined);
				  results.add(combined);
				  Collections.sort(results);
			}
			//remaining node is root
			root = results.get(0);
		}catch (FileNotFoundException ex){
			System.out.println("Input file not found. Exiting");
			System.exit(0);
		}catch (IOException ex){
			System.out.println("IO exception");
			ex.printStackTrace();
		}
	}
	
	//traverse the tree to map individual symbols to their codes
	//the code is the path traversed to a leaf node.
	public static void resolveCodes(HuffToken t, String c){
		if (!t.isLeaf()){
			if (t.getLeft() != null){
				resolveCodes(t.getLeft(), c + "0");
			}
			if (t.getRight() != null){
				resolveCodes(t.getRight(), c + "1");
			}
		}else{
			codes.put(t.getText(), c);
		}
	}
	
	//takes a symbol to code mapping and returns a code to symbol mapping. The huffman algorithm should produce a 1 to 1 relationship.
	//for use in decrypting encoded messages.
	public static HashMap<String,String> reverseCodes(HashMap<String,String> incodes){
		HashMap<String,String> rev = new HashMap<String,String>();		
		for (Entry<String, String> entry :incodes.entrySet()){
			rev.put(entry.getValue(), entry.getKey());
		}
		return rev;
	}
	
	//convert the input using the Huffman codes generated
	public static String encode(StringBuilder msg, HashMap<String,String> map){
		StringBuilder out = new StringBuilder();
		for (int x=0; x < msg.length(); x++){
			out.append(map.get(String.valueOf(msg.charAt(x))));
		}
		return out.toString();
	}
	
	public static String decode(String encrypted, HashMap<String,String> map){
		StringBuilder txt = new StringBuilder();
		StringBuilder buffer = new StringBuilder(encrypted);
		String key;
		String val;
		boolean codeFound = false;
		int cursor = 0;
		//Translate and consume codes until the buffer is gone
		while(buffer.length() > 0){
			codeFound = false;
			cursor = 0;
			key = null;
			//check for an entry in the map with the candidate key. If there isn't one, add the next digit, repeat.
			while (!codeFound){
				val = null;
				key = buffer.substring(0, cursor++);
				val = map.get(key);
				if (val != null){
					// symbol found. consume the code and the translated character to the buffer.
					codeFound = true;
					txt.append(val);
					buffer.delete(0, cursor-1);
				}
			}
		}
		return txt.toString();
	}
	
	public static void main(String[] args) {
		init(args[0]);
		resolveCodes(root,"");
		String output = encode(in,codes);
		HashMap<String,String> undoCodes = reverseCodes(codes);
		System.out.println("INPUT 	   : " + in.toString());
		System.out.println("OUTPUT     : " + output);
		System.out.println("BACK AGAIN : " + decode(output, undoCodes));
	}
}