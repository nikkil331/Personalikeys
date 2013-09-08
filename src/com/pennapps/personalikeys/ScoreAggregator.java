package com.pennapps.personalikeys;

import java.util.Iterator;
import java.util.StringTokenizer;


public class ScoreAggregator {
	public NgramIterator two_iter;
	public NgramIterator one_iter;
	
	public ScoreAggregator(String s){
		one_iter = new NgramIterator(1, s);
		two_iter = new NgramIterator(2, s);
	}
	
	public ScoreAggregator(){
	}
	
	public void setString(String s){
		one_iter = new NgramIterator(1, s);
		two_iter = new NgramIterator(2, s);
	}
	
	public static class NgramIterator implements Iterator<String> {

	    String[] words;
	    int pos = 0, n;

	    public NgramIterator(int n, String str) {
	        this.n = n;
	        words = str.split(" ");
	    }

	    public boolean hasNext() {
	        return pos < words.length - n + 1;
	    }

	    public String next() {
	        StringBuilder sb = new StringBuilder();
	        for (int i = pos; i < pos + n; i++)
	            sb.append((i > pos ? " " : "") + words[i]);
	        pos++;
	        return sb.toString();
	    }

	    public void remove() {
	        throw new UnsupportedOperationException();
	    }
	}
	public static void main(String[] args){
		ScoreAggregator sa = new ScoreAggregator("This is a sentence");
		while(sa.two_iter.hasNext()){
			System.out.println(sa.two_iter.next());
		}
	}
}
