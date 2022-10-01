import java.util.ArrayList;
import java.util.Scanner;

public class RecipeBook {
	static ArrayList<Recipes> recipe = new ArrayList<Recipes>();

	public static void main(String[] args) {
		mainMenu();
	}

	private static void mainMenu() {
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

	private static void addRecipes() {
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
		System.out.print("Ingredients ");
		ingredientList = s.nextLine();
		System.out.print("Instructions: ");
		instructions = s.nextLine();
		for (int i = 0; i < lineOne.length(); i++) {
			System.out.print("-");
		}
		Recipes addingRecipess = new Recipes(name.toLowerCase(), description, ingredientList, instructions);
		recipe.add(addingRecipess);
		System.out.println("\nSaved recipe successfully... \nPress Enter to go back to the Main Window");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

	private static void searchRecipes() {

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

					System.out.println(String.format("%-3s%-26s\n", c.getRecipeID() + 1, "|" + c.getRecipeName()));
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

	private static void exploreRecipe(int recipeID) {
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
				System.out.println(
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

	private static void displayInstructions(int recipeID) {
		String lineTwo_aa = "\nMain Window --> Recipe Instructions";
		System.out.println(lineTwo_aa);
		for (int i = 0; i < lineTwo_aa.length(); i++) {
			System.out.print("=");
		}
		System.out.println();
		System.out.printf("%-4s%-25s%-45s\n", "ID |", "Name", "|Instructions");
		for (int i = 0; i < 130; i++) {
			System.out.print("-");
		}
		System.out.println();
		for (Recipes c : recipe) {
			if (c.getRecipeID() == recipeID) {
				System.out.println(String.format("%-3s%-26s%-45s\n", c.getRecipeID() + 1, "|" + c.getRecipeName(),
						"|" + c.getInstructions()));
			}

		}
		for (int i = 0; i < 130; i++) {
			System.out.print("-");
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
			System.out.println(String.format("%-3s%-26s\n", c.getRecipeID() + 1, "|" + c.getRecipeName()));
		}
		for (int i = 0; i < 30; i++) {
			System.out.print("-");
		}

	}

}
