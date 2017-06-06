package codecheck;

import java.util.*;


public class App {
	
	public static void main(String[] args) {
		HashMap<Character,Integer> rank = new HashMap<Character,Integer>();

		rank.put('(', 3);
		rank.put('*', 2);
		rank.put('/', 2);
		rank.put('+', 1);
		rank.put('-', 1);
		rank.put(')', 0);
		
	    if(args.length < 1){
	    	System.err.println("引数がない");
	    	System.exit(1);
	    }
		String input = String.format(args[0]);
		if(input.length() < 1){
	    	System.err.println("空白のみ");
	    	System.exit(1);
	    }
			
		Deque<Character> stack = new ArrayDeque<Character>();
		String s = input.replaceAll("\\s+", "");
		s = "(" + s + ")";
		int len = s.length();
	
		String ans = "";
		String tmp = "";

		
		for (int i = 0; i < len; i++) {
	        char c = s.charAt(i);
	        if ('0' <= c && c <= '9') {
	            tmp += c;
	        } else {
	        	if(!rank.containsKey(c)){
	        		System.err.println("数値、演算子、括弧以外の文字が式に含まれている");
	        		System.exit(2);
	        	}
	            
	        	if (!tmp.equals("")) {
	                if (ans.length() > 0) {
	                    ans += " ";
	                }
	                
	                ans += tmp;
	                tmp = "";
	            }
	            
	            while (!stack.isEmpty() && rank.get(stack.peek()) >= rank.get(c) && stack.peek() != '(') {
	                if (ans.length() > 0) {
	                    ans += " ";
	                }
	                
	                ans += stack.pop();
	                
	            }
	            if (c == ')') {
	            	if(stack.pop() != '('){
	            		System.err.println("括弧が不整合");
	            		System.exit(3);
	            	}
	                
	            } else {
	                stack.push(c);
	            }
	        }
	    }
		if(!stack.isEmpty()){
			System.err.println("数値または演算子の出現順が中置記法として不正");
			System.exit(4);
		}
		Deque<Integer> que  = new ArrayDeque<Integer>();
		String[] ansArray = ans.split("\\s");
        int a = 0;
        int b = 0;
        for (int i = 0; i < ansArray.length; i++) {
            switch (ansArray[i]) {
            case "+":
                a = que.pollFirst();
                b = que.pollFirst();
                que.addFirst(b + a);
                break;
            case "-":
                a = que.pollFirst();
                b = que.pollFirst();
                que.addFirst(b - a);
                break;
            case "/":
                a = que.pollFirst();
                b = que.pollFirst();
                que.addFirst(b / a);
                break;
            case "*":
                a = que.pollFirst();
                b = que.pollFirst();
                que.addFirst(b * a);
                break;
            default:
                que.addFirst(Integer.parseInt(ansArray[i]));
            }
        }
		System.out.println(ans +" = " + que.pop());
		System.exit(0);
	}
	
}
