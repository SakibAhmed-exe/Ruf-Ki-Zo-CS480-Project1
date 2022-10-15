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

public class RecipeBook {
	
	static JSONArray book = new JSONArray();
	static ArrayList<Recipes> recipe = new ArrayList<Recipes>();

	public static void main(String[] args) throws Exception {
		
		BasicConfigurator.configure();

		//have 3 pre-created recipes added to json file
		String name1 = "Shrimp Alfredo Pasta";
    	String des1 = "This shrimp Alfredo pasta recipe is the perfect fast weeknight dinner!";
    	String ing1 = "Fettuccine pasta, Shrimp, Butter, Cream cheese, Heavy cream, Chicken broth, Garlic, Parmesan cheese";
    	String ins1 = "Fill a large pot with water, add salt and wait for it to boil. Add the dried pasta. Stir until the water returns to a boil. "
    			+ "Have it ready on the side.;"
    			+ "Add the butter, cream cheese, heavy cream, chicken broth, and garlic to a skillet over medium heat. "
    			+ "Cook it for 5 minutes.;"
    			+ "Stir in the parmesan cheese and let it bubble for about a minute.;"
    			+ "Add the shrimp and cook for 5-6 minutes.;"
    			+ "Season to taste and toss with the pasta, Stir well together and add any seasoning, if you wish. Serve immediately.";
    	
    	String name2 = "Sauteed Spinach";
    	String des2 = "This spinach recipe is one of the best ways to cook spinach.";
    	String ing2 = "Spinach, Extra virgin olive oil, Sliced garlic, Salt";
    	String ins2 = "Clean and prep the spinach.;"
    			+ "Heat 2 tablespoons olive oil in a large skillet on medium high heat. "
    			+ "Add the garlic and saute for about 30 seconds.;"
    			+ "Add the spinach to the pan and cook. Cover the pan and cook for 1 minute. "
    			+ "Uncover and turn the spinach over again. Repeat.;"
    			+ "Remove from heat and drain the excess liquid. "
    			+ "Add a little more olive oil, if you wish. Then, sprinkle with salt to taste. Serve immediately.";
    	
    	String name3 = "Prosciutto And Melon";
    	String des3 = "This appetizer is one of the easiest, amazingly delicious summer dish!";
    	String ing3 = "One cantaloupe, Prosciutto (Italian dry-cured ham), Balsamic glaze or quality honey for drizzling, Optional fresh herb garnish";
    	String ins3 = "Peel the melon, cut in half lengthwise, remove seeds, and slice it.;"
    			+ "Wrap each piece of cantaloupe with a slice of prosciutto.;"
    			+ "Add Balsamic glaze or honey for drizzling on each slice.;"
    			+ "Optional: add fresh herb garnish or basil.;"
    			+ "Arrange on a platter and serve cold.";
				
		Recipes r1 = new Recipes(name1.toLowerCase(),des1,ing1,ins1);
		Recipes r2 = new Recipes (name2.toLowerCase(), des2, ing2, ins2);
		Recipes r3 = new Recipes (name3.toLowerCase(), des3, ing3, ins3);
		recipe.add(r1);
		recipe.add(r2);
		recipe.add(r3);
    	writeJsonRecipe(name1, des1, ing1, ins1 );
    	writeJsonRecipe(name2, des2, ing2, ins2);
    	writeJsonRecipe(name3, des3, ing3, ins3);

		mainMenu();
	}
	
	//display the main menu
	private static void mainMenu() throws Exception {
		Scanner sc = new Scanner(System.in);
		int user_input;
		do {
			System.out.println("\n\t\t\tMain Window");
			for (int i = 0; i < 66; i++) { System.out.print("="); }
			System.out.println("\nChoose one of the following options:\n");
			System.out.println("(1) Recipe Creation\n");
			System.out.println("(2) Recipe Retrieval\n");
			System.out.println("(3) Quit\n");
			System.out.print("Enter Your Choice: ");

			user_input = sc.nextInt();

			switch (user_input) {

			//if the user enters 1, call addRecipes for recipe creation
			case 1:
				addRecipes();
				break;
			//if the user enters 2, call searchRecipes for recipe retrieval
			case 2:
				searchRecipes();
				break;
			//if the user enters 3, quit the program
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

	//recipe creation
	private static void addRecipes() throws Exception {
		Scanner s = new Scanner(System.in);
		String name, description, ingredientList, instructions;
		String lineOne = "\nMain Window --> Add a new recipe: (Please enter the following information)";
		System.out.println(lineOne);
		for (int i = 0; i < lineOne.length(); i++) {
			System.out.print("=");
		}

		//asking the user to enter their own recipes
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

		//adding the new recipe to json file
		Recipes addingRecipess = new Recipes(name.toLowerCase(), description, ingredientList, instructions);
		recipe.add(addingRecipess);
		writeJsonRecipe(name.toLowerCase(), description, ingredientList, instructions);
		System.out.println("\nSaved recipe successfully... \nPress Enter to go back to the Main Window");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

	//recipe retrieval
	private static void searchRecipes() throws Exception {
		Scanner s = new Scanner(System.in);
		int userChoice;
		String lineTwo = "\nMain Window --> Search for Recipes: (Choose one of the following options)";
		System.out.println(lineTwo);
		for (int i = 0; i < lineTwo.length(); i++) {
			System.out.print("=");
		}

		//the user can choose whether to search by recipe names or to display all names
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

			//after the user searches 1 recipe by name will be prompt with the following options
			System.out.println("\nChoose one of these options: ");
			System.out.print("\n(1) Display All Recipe Names");
			System.out.print("\n(2) Explore Recipe");
			System.out.print("\n(3) Back to Main Window");
			System.out.println();
			System.out.print("\nEnter Your Choice: ");
			selection = s.nextInt();
			s.nextLine();

			if (selection == 1) { // display all recipe names
				displayRecipes();
			}

			if (selection == 2) { // explore this specific recipe
				int recipeID;
				//calling the exploreRecipe function for recipe exploration
				for (Recipes c : recipe) {
					if (name.equals(c.getRecipeName()) || (c.getRecipeName().contains(name))) {
						recipeID = c.getRecipeID();
						exploreRecipe(recipeID);
					}
				}
			}

			if (selection == 3) { //going back to the main window
				mainMenu();
			}

		}

		if (userChoice == 2) { // Display all recipe names, will be brought back to the main window after
			displayRecipes();
		}
	}

	//specific recipe exploration
	private static void exploreRecipe(int recipeID) throws Exception {
		Scanner sc = new Scanner(System.in);
		String userChoice;
		String lineTwo_aa = "\nMain Window --> Explore Recipe";
		System.out.println(lineTwo_aa);
		for (int i = 0; i < lineTwo_aa.length(); i++) {
			System.out.print("=");
		}
		System.out.println();
		
		for (int i = 0; i < 150; i++) {
			System.out.print("-");
		}
		System.out.println();
		
		for (Recipes c : recipe) {
			if (c.getRecipeID() == recipeID) {
				int r_ID = c.getRecipeID() + 1;
				String rID = "" + r_ID;
				String[] instructionsFilter = c.getInstructions().split(";");
	        		System.out.println(
							String.format("ID: " + rID + "\nName: " + c.getRecipeName() + "\nDescription: " + c.getDescription() + "\nIngredients: "  
					+ c.getIngredientList() + "\nInstructions: " ));
	        		for(int i = 0; i < instructionsFilter.length; i++) {
						System.out.println(instructionsFilter[i]);
					}
			}
		}
		
		
		
		
		
		for (int i = 0; i < 150; i++) {
			System.out.print("-");
		}
		//asking user if they want to explore instructions step-by-step
		System.out.println("\nWould you like step-by-step instructions for this recipe? (yes/no): ");
		userChoice = sc.next();
		
		if (userChoice.equals("yes")) { //bring to displayInstructions
			displayInstructions(recipeID);
		}

		if (userChoice.equals("no")) { //back to the main window
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

		//iterating over json file to display instructions of the chosen recipe step-by-step
		for (Recipes c : recipe) {
			if (c.getRecipeID() == recipeID) {

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

	//display the recipe
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
	
	//writing recipes to json file
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
		String[] instructionsFilter = instructions.split(";");
		for(int i = 0; i < instructionsFilter.length; i++) {
			instructionsArray.add("Step " + (i+1) + ": " + instructionsFilter[i]);
		}
		recipe.put("instructions", instructionsArray);	
		
		book.add(recipe);
	
	}
	
	//viewing json file
	private static void viewJsonRecipes() throws IOException, ParseException {
		JSONParser parser = new JSONParser();

		try (Reader reader = new FileReader("RecipeBook")) {

			JSONArray recipes = (JSONArray) parser.parse(reader);
			Iterator<JSONObject> iterator = recipes.iterator();     
			while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

		} catch (IOException e) {
		    e.printStackTrace();
		} catch (ParseException e) {
		    e.printStackTrace();
		}
	}
}
