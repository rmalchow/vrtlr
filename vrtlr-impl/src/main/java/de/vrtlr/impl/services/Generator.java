package de.vrtlr.impl.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class Generator {

	@Value("classpath:words/zero")
	private Resource zero;
	
	@Value("classpath:words/first")
	private Resource first;
	
	@Value("classpath:words/second")
	private Resource second;
	
	private List<String> zs = new ArrayList<String>();
	private List<String> fs = new ArrayList<String>();
	private List<String> ss = new ArrayList<String>();
	
	@PostConstruct
	public void load() throws IOException {
		{
			String s;
			Set<String> set = new TreeSet<>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(zero.getInputStream()));
			while((s = reader.readLine()) != null) {
				set.add(s);
			}
			zs.addAll(set);
		}
		{
			String s;
			Set<String> set = new TreeSet<>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(first.getInputStream()));
			while((s = reader.readLine()) != null) {
				set.add(s);
			}
			fs.addAll(set);
		}
		{
			String s;
			Set<String> set = new TreeSet<>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(first.getInputStream()));
			while((s = reader.readLine()) != null) {
				set.add(s);
			}
			ss.addAll(set);
		}
		System.err.println(fs.size()*ss.size()*zs.size());
		for(int i=0; i < 10;i++) {
			System.err.println(generate());
		}
	}
	
	
	public String generate() {
		int a = (int)Math.round(Math.random()*(zs.size()-1));
		int b = (int)Math.round(Math.random()*(fs.size()-1));
		int c = (int)Math.round(Math.random()*(ss.size()-1));
		return zs.get(a)+"_"+fs.get(b)+"_"+ss.get(c);
	}
	
	
	
}
