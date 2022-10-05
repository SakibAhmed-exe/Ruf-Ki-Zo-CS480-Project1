package Ruf_Ki_Zo.RecipeBook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RecipeBook {
	
	static JSONArray book = new JSONArray();
	static ArrayList<Recipes> recipe = new ArrayList<Recipes>();

	public static void main(String[] args) throws Exception {
		
		BasicConfigurator.configure();

		ObjectMapper mapper = new ObjectMapper();
    	try {
        	File jsonInputFile = new File("recipes.txt");
        	Recipes r = mapper.readValue(jsonInputFile, Recipes.class);
        	System.out.println(r);
		} 
		catch (IOException e) {
        	e.printStackTrace();
   		}	
		
		viewJsonRecipes();

		mainMenu();
	}
	
	private static void mainMenu() throws Exception {
		Scanner sc = new Scanner(System.in);
		int user_input;
		do {
			System.out.println("\n\n          Main Window");
			System.out.println("====================================");
			System.out.println("Choose one of the following options:\n");
			System.out.println("(1) Recipe Creation.\n");
			System.out.println("(2) Recipe Retrieval.\n");
			System.out.println("(3) Quit\n");
			System.out.print("Enter Your Choice: ");

			user_input = sc.nextInt();

			switch (user_input) {

			case 1:
				addRecipes();
				break;

			case 2:
				searchRecipes();
				break;

			case 3:
				try (FileWriter file = new FileWriter("RecipeBook")) {
		            file.write(book.toJSONString());
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				System.out.println("You are now exiting. Thank you!\n");
				System.exit(0);
				break;

			default:
				System.out.println("Sorry, that is not a valid option!");
				break;
			}
		} while (user_input != 3);
		sc.nextLine();
		sc.close();

	}

	private static void addRecipes() throws Exception {
		Scanner s = new Scanner(System.in);
		String name, description, ingredientList, instructions;
		String lineOne = "\nMain Window --> Add a new recipe: (Please enter the following information)";
		System.out.println(lineOne);
		for (int i = 0; i < lineOne.length(); i++) {
			System.out.print("=");
		}
		System.out.print("\nName: ");
		name = s.nextLine();
		System.out.print("Description: ");
		description = s.nextLine();
		System.out.print("Ingredients: ");
		ingredientList = s.nextLine();
		System.out.print("Instructions: ");
		instructions = s.nextLine();
		for (int i = 0; i < lineOne.length(); i++) {
			System.out.print("-");
		}
		Recipes addingRecipess = new Recipes(name.toLowerCase(), description, ingredientList, instructions);
		recipe.add(addingRecipess);
		writeJsonRecipe(name.toLowerCase(), description, ingredientList, instructions);
		System.out.println("\nSaved recipe successfully... \nPress Enter to go back to the Main Window");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

	private static void searchRecipes() throws Exception {

		Scanner s = new Scanner(System.in);
		int userChoice;
		String lineTwo = "\nMain Window --> Search for Recipes: (Choose one of the following options)";
		System.out.println(lineTwo);
		for (int i = 0; i < lineTwo.length(); i++) {
			System.out.print("=");
		}
		System.out.print("\n(1) Search by Recipe Name ");
		System.out.print("\n(2) Display All Recipe Names ");
		System.out.println();
		System.out.print("\nEnter Your Choice: ");
		userChoice = s.nextInt();
		System.out.println();

		if (userChoice == 1) { // Search by Recipe Name
			int selection;
			String name;
			String lineTwo_a = "\nMain Window --> Search for Recipe: Search by Name";
			System.out.println(lineTwo_a);
			for (int i = 0; i < lineTwo_a.length(); i++) {
				System.out.print("=");
			}
			s.nextLine();
			System.out.print("\n(1) Enter Name: ");
			name = s.nextLine();
			System.out.println();
			System.out.println("Search Results: ");
			for (int i = 0; i < 30; i++) {
				System.out.print("-");
			}
			System.out.println();
			System.out.printf("%-4s%-25s\n", "ID |", "Name");
			for (int i = 0; i < 30; i++) {
				System.out.print("-");
			}
			System.out.println();
			name.toLowerCase();
			for (Recipes c : recipe) {
				if (name.equals(c.getRecipeName()) || (c.getRecipeName().contains(name))) {

					System.out.print(String.format("%-3s%-26s\n", c.getRecipeID() + 1, "|" + c.getRecipeName()));
				}

			}

			for (int i = 0; i < 30; i++) {
				System.out.print("-");
			}
			System.out.println("\nChoose one of these options: ");
			System.out.print("\n(1) Display All");
			System.out.print("\n(2) Explore Recipe");
			System.out.print("\n(3) Back to main Window");
			System.out.println();
			System.out.print("\nEnter Your Choice: ");
			selection = s.nextInt();
			s.nextLine();

			if (selection == 1) { // display all recipe names
				displayRecipes();
				
			}

			if (selection == 2) { // explore this specific recipe
				int recipeID;
				for (Recipes c : recipe) {
					if (name.equals(c.getRecipeName()) || (c.getRecipeName().contains(name))) {
						recipeID = c.getRecipeID();
						exploreRecipe(recipeID);
					}

				}
			}

			if (selection == 3) {
				mainMenu();
			}

		}

		if (userChoice == 2) { // Display all recipe names
			displayRecipes();
		}
	}

	private static void exploreRecipe(int recipeID) throws Exception {
		Scanner sc = new Scanner(System.in);
		String userChoice;
		String lineTwo_aa = "\nMain Window --> Explore Recipe";
		System.out.println(lineTwo_aa);
		for (int i = 0; i < lineTwo_aa.length(); i++) {
			System.out.print("=");
		}
		System.out.println();
		System.out.printf("%-4s%-25s%-30s%-40s%-45s\n", "ID |", "Name", "|Description ", "|Ingredients",
				"|Instructions");
		for (int i = 0; i < 150; i++) {
			System.out.print("-");
		}
		System.out.println();
		for (Recipes c : recipe) {
			if (c.getRecipeID() == recipeID) {
				System.out.print(
						String.format("%-3s%-26s%-30s%-40s%-45s\n", c.getRecipeID() + 1, "|" + c.getRecipeName(),
								"|" + c.getDescription(), "|" + c.getIngredientList(), "|" + c.getInstructions()));
			}

		}
		for (int i = 0; i < 150; i++) {
			System.out.print("-");
		}

		System.out.println("\nWould you like step-by-step instructions for this recipe? (yes/no): ");
		userChoice = sc.next();
		
		if (userChoice.equals("yes")) {
			displayInstructions(recipeID);
		}

		if (userChoice.equals("no")) {
			mainMenu();
		}

	}

	private static void displayInstructions(int recipeID) throws IOException {
		String lineTwo_aa = "\nMain Window --> Recipe Instructions";
		System.out.println(lineTwo_aa);
		for (int i = 0; i < lineTwo_aa.length(); i++) {
			System.out.print("=");
		}
		System.out.println();
		System.out.println();
		for (Recipes c : recipe) {
			if (c.getRecipeID() == recipeID) {
//				System.out.println(String.format("%-3s%-26s%-45s\n", c.getRecipeID() + 1, "|" + c.getRecipeName(),
//						"|" + c.getInstructions()));
				JSONObject instruct = (JSONObject) book.get(c.getRecipeID());
				JSONArray instructs = (JSONArray) instruct.get("instructions");
	        	Iterator<JSONObject> iterator = instructs.iterator(); 
	        	System.out.println(iterator.next());
	        	while (iterator.hasNext() && System.in.read() != -1) {
	                System.out.println(iterator.next());
	            }
				
			}
			
		}
	}

	private static void displayRecipes() {
		String lineTwo_aa = "\nMain Window --> Display All Recipe Names";
		System.out.println(lineTwo_aa);
		for (int i = 0; i < lineTwo_aa.length(); i++) {
			System.out.print("=");
		}
		System.out.println();
		System.out.printf("%-4s%-25s\n", "ID |", "Name");
		for (int i = 0; i < 30; i++) {
			System.out.print("-");
		}
		System.out.println();
		for (Recipes c : recipe) {
			System.out.print(String.format("%-3s%-26s\n", c.getRecipeID() + 1, "|" + c.getRecipeName()));
		}
		for (int i = 0; i < 30; i++) {
			System.out.print("-");
		}

	}
	
	private static void writeJsonRecipe(String name, String description, String ingredients, String instructions) throws Exception {
		
		JSONObject recipe = new JSONObject();
		recipe.put("name", name);
		recipe.put("description", description);
		
		JSONArray ingredientsArray = new JSONArray();
		String[] ingredientsFilter = ingredients.split(",");
		for(String ingredient : ingredientsFilter) {
			ingredientsArray.add(ingredient);
		}
		recipe.put("ingredients", ingredientsArray);
		
		JSONArray instructionsArray = new JSONArray();
		String[] instructionsFilter = instructions.split(",");
		for(int i = 0; i < instructionsFilter.length; i++) {
			instructionsArray.add("Step " + (i+1) + ": " + instructionsFilter[i]);
		}
		recipe.put("instructions", instructionsArray);	
		
		book.add(recipe);
//		try (FileWriter file = new FileWriter("RecipeBook")) {
//            file.write(book.toJSONString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
		
	}
	
	private static void viewJsonRecipes() throws IOException, ParseException {
		JSONParser parser = new JSONParser();

		try (Reader reader = new FileReader("RecipeBook")) {

			JSONArray recipes = (JSONArray) parser.parse(reader);
			Iterator<JSONObject> iterator = recipes.iterator();     
			while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

		//            String name = (String) recipe.get("name");
		//            System.out.println("Name: " + name);
		//            System.out.println("");
		//
		//            String description = (String) recipe.get("description");
		//            System.out.println("Description: " + description);
		//            System.out.println("");
		//
		//            JSONArray ingredients = (JSONArray) recipe.get("ingredients");
		//            Iterator<String> iterator = ingredients.iterator();
		//            System.out.println("Ingredients:");
		//            while (iterator.hasNext()) {
		//                System.out.println(iterator.next());
		//            }
		//            System.out.println("");
		//            
		//            JSONArray instructions = (JSONArray) recipe.get("instructions");
		//            Iterator<String> iteratorTwo = instructions.iterator();
		//            System.out.println("Instructions:");
		//            while (iteratorTwo.hasNext()) {
		//                System.out.println(iteratorTwo.next());
		//            }

		} catch (IOException e) {
		    e.printStackTrace();
		} catch (ParseException e) {
		    e.printStackTrace();
		}
	}
}
