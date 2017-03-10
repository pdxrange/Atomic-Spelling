	//package arraylist;
	import java.util.ArrayList;
	import java.util.List;
	import java.io.File;
	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.util.regex.PatternSyntaxException;
	import java.util.Arrays;

	public class AtomicSpelling{
		private static resultObject resObject = new resultObject();

		public static void main(String args[]){
			File file;
			List<String> elementList;
			String word;

			AtomicSpelling obj = new AtomicSpelling();
			file = obj.loadFile("elements.txt");
			elementList = obj.makeListFromFile(file);

			//for each word, find the elements in the word
			for(int i = 0; i < args.length; i++){
				word = args[i];
				obj.findElementAbbreviationsInWord(word, elementList);
			}
			obj.printResultsToScreen();
		}

		private File loadFile(String fileName){
			return new File(fileName);
		}

		private List makeListFromFile(File file){
			List<String> elementsList = new ArrayList<>(); 
			BufferedReader reader = null;
			try {
			    reader = new BufferedReader(new FileReader(file));
			    String line = null;
			  	while ((line = reader.readLine()) != null) {
			        elementsList.add(line);
			    }
			} catch (FileNotFoundException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			} finally {
			    try {
			        if (reader != null) {
			            reader.close();
			        }
			    } catch (IOException e) {
			    }
			}

			return elementsList;
		}

		private String[] arrayFromString(String line){
			String[] elementArray  = new String[6];
			try{
				elementArray = line.split("\\s+");
			}catch(PatternSyntaxException e){
				e.printStackTrace();
			}
			return elementArray;
	 	}

	 	private void printList(List<String> list){
	 		System.out.println(Arrays.toString(list.toArray()));
	 	}

	 	private void findElementAbbreviationsInWord(String word, List<String> elementsList){
	 		String[] arr;
	 		int pointerStart = 0;
	 		int pointerEnd;
	 		String wordToken;
	 		String newString = "";

	 		do{
	 			boolean hasMatch = false;

	 			//First try one letter
	 			pointerEnd = pointerStart + 1;
	 			wordToken = word.substring(pointerStart,pointerEnd);
				for(int i=0;i<elementsList.size();i++){
	 				arr = arrayFromString(elementsList.get(i));
	 				hasMatch = addMatchingResultsToList(wordToken, arr);
	 				if(hasMatch){
	 					pointerStart += 1;
	 					break;
	 				}
	 			}
	 			//then two
	 			if(!hasMatch){
		 			if(word.length() == pointerStart + 1){
		 				pointerEnd = pointerStart + 1;
		 			}else{
		 				 pointerEnd = pointerStart + 2;
		 			}
		 			wordToken = word.substring(pointerStart,pointerEnd);

		 			for(int i=0;i<elementsList.size();i++){
		 				arr = arrayFromString(elementsList.get(i));
		 				hasMatch = addMatchingResultsToList(wordToken, arr);
		 				if(hasMatch){
		 					pointerStart += 2;
		 					break;
		 				}
		 			}
		 		} 			
	 		}
	 		while(pointerEnd < word.length());

	 	}

	 	private boolean addMatchingResultsToList(String token, String[] arr){
	 		List<String> allElementNamesList = new ArrayList<>();
	 		if(token.toLowerCase().equals(arr[1].toLowerCase())){
				resObject.setString(arr[1]);
				resObject.addItemToList(arr[0]); 
				return true;
	 		}

	 		return false;
	 	}

	 	private String getNthTwoLettersOfWord(int n, String word){
	 		return word.substring(n-1,n+1);
	 	}

	 	private void printResultsToScreen(){
		    System.out.println(resObject.elementName);
		    System.out.println(resObject.elementsList);
		}
	}

	class resultObject{
		public List<String> elementsList = new ArrayList<String>();
		String elementName = "";

		public resultObject(){

		}

		public void addItemToList(String item){
			elementsList.add(item);
		}

		public void setString(String string){
			elementName += string;
		}
	}