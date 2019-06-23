package br.ufes.inf.nemo.checkantipattern;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import RefOntoUML.parser.OntoUMLParser;
import net.menthor.antipattern.MultipleModelProcesser;
import net.menthor.antipattern.Antipattern;
import net.menthor.common.file.TimeHelper;
import net.menthor.antipattern.AntipatternOccurrence;;

public class checkAntiPatern {

	public static void main(String[] args) {
		OntoUMLParser parser;
		BufferedReader br;
		List<String> settings = new ArrayList<String>();
		String line;
		String fileName;
		String antiPatternsChecked = "";
		
		try {
			br = new BufferedReader(new FileReader(args[0]));
			while ((line = br.readLine()) != null) {
			      settings.add(line);
			}
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		fileName = settings.get(0);
		
		for(String s : settings) {
			   if(settings.indexOf(s)>0) {
				   antiPatternsChecked += s.toUpperCase();
			   }
		}
		
		if(antiPatternsChecked.isEmpty()) {
			System.out.println("ERROR: At least one antipattern must be checked");
			return;
		}
		
		System.out.println(TimeHelper.getTime()+" - "+fileName+": Loading parser...");
		
		try {
			parser = new OntoUMLParser(fileName);
			
		}catch(Exception e){
			System.out.println(TimeHelper.getTime()+" - "+fileName+": Parser not loaded!");
			System.out.println(e);
			return;
		}
		
		List<Antipattern<?>> apList = MultipleModelProcesser.createApList(parser,antiPatternsChecked);
		List<AntipatternOccurrence> ocurrenceList = new ArrayList<AntipatternOccurrence>();
		for (Antipattern<?> ap : apList) {
			System.out.println(TimeHelper.getTime()+" - "+fileName+": identifying "+ap.info().acronym+"...");
			for(AntipatternOccurrence apo : ap.identify()) {
				ocurrenceList.add(apo);
			}
			System.out.println(TimeHelper.getTime()+" - "+fileName+": "+ap.getOccurrences().size()+" occurrences of "+ap.info().acronym+" identified!");
		}
		System.out.println();
		
		 

		for (Antipattern<?> ap : apList) {
			if(ap.getOccurrences().toString() != "[]") {
				System.out.println("Antipattern:");
				System.out.println(ap.info().getName());
				System.out.println();
				System.out.println("Description of the antipattern:");
				System.out.println(ap.info().getDescription());
				System.out.println();
				System.out.println("Involved classes:");
				for(AntipatternOccurrence apo : ap.getOccurrences()) {
					System.out.println(apo);
				}
				System.out.println();
					
			}
		}
	}

}
