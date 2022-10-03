package Ruf_Ki_Zo.RecipeBook;
import java.util.ArrayList;

public class Recipes {
	public ArrayList<Recipes> recipe;
	private String name;
	private String description;
	private String ingredientList;
	private String instructions;
	private int recipeID;
	private static int objCounter;

	public Recipes(String n, String d, String iL, String ins) {
		this.name = n;
		this.description = d;
		this.ingredientList = iL;
		this.instructions = ins;
		this.setRecipeID(objCounter++);
	}

	public Recipes() {
		super();
	}

	public String getRecipeName() {
		return name;
	}

	public void setRecipeName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIngredientList() {
		return ingredientList;
	}

	public void setIngredientList(String ingredientList) {
		this.ingredientList = ingredientList;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public static int getObjCounter() {
		return objCounter;
	}

	public static void setObjCounter(int objCounter) {
		Recipes.objCounter = objCounter;
	}

	public static void resetObjCounter() {
		Recipes.objCounter = 0;
	}

	public int getRecipeID() {
		return recipeID;
	}

	public void setRecipeID(int objCounter) {
		this.recipeID = objCounter;
	}

}
