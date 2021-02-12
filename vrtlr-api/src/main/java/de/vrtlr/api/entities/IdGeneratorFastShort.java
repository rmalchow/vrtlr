package de.vrtlr.api.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import org.springframework.util.StringUtils;

import de.disk0.dbutil.api.IdGenerator;

public class IdGeneratorFastShort implements IdGenerator {
	
	private static SplittableRandom sr = new SplittableRandom();
	private char[] alphabet = null;
	
	public IdGeneratorFastShort() {
		String s = "";
		for(int i=48;i<58;i++) {
			s = s +(char)i;
		}
		for(int i=65;i<91;i++) {
			s = s +(char)i;
		}
		for(int i=97;i<123;i++) {
			s = s +(char)i;
		}
		s = s +(char)95;
		s = s +(char)45;
		alphabet = s.toCharArray();
		System.err.println(alphabet);
	}
	
	@Override
	public String generateId() {
		char[] x = new char[8];
		int l=0;
		l=sr.nextInt();
		String s = "";
		s = s + (char)alphabet[l>>0 &0x3F];
		s = s + (char)alphabet[l>>6 &0x3F];
		s = s + (char)alphabet[l>>12 &0x3F];
		l=sr.nextInt();
		s = s + (char)alphabet[l>>0 &0x3F];
		s = s + (char)alphabet[l>>6 &0x3F];
		s = s + (char)alphabet[l>>12 &0x3F];
		return s;
	}
	
	public static void main(String[] args) {
		new IdGeneratorFastShort();
	}

}
