package ignorelist.shrtnr.api.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import de.disk0.dbutil.api.IdGenerator;

public class IdGeneratorFastShort implements IdGenerator {
	
	private static SplittableRandom sr = new SplittableRandom();
	private Character[] alphabet = null;
	
	public IdGeneratorFastShort() {
		List<Character> chars = new ArrayList<>();
		for(int i=48;i<58;i++) {
			chars.add((char)i);
		}
		for(int i=65;i<91;i++) {
			chars.add((char)i);
		}
		for(int i=97;i<123;i++) {
			chars.add((char)i);
		}
		chars.add((char)95);
		chars.add((char)45);
		alphabet = chars.toArray(new Character[chars.size()]);
		System.err.println(alphabet.length);
	}
	
	@Override
	public String generateId() {
		char[] x = new char[8];
		int l=0;
		l=sr.nextInt();
		x[0] = (char)alphabet[l>>0 &0x1F];
		x[1] = (char)alphabet[l>>5 &0x1F];
		x[2] = (char)alphabet[l>>10 &0x1F];
		x[3] = (char)alphabet[l>>15 &0x1F];
		l=sr.nextInt();
		x[4] = (char)alphabet[l>>0 &0x1F];
		x[5] = (char)alphabet[l>>5 &0x1F];
		x[6] = (char)alphabet[l>>10 &0x1F];
		x[7] = (char)alphabet[l>>15 &0x1F];
		return new String(x);
	}
	
	public static void main(String[] args) {
		new IdGeneratorFastShort();
	}

}
