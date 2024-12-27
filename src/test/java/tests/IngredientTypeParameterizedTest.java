package tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.IngredientType;

@RunWith(Parameterized.class)
public class IngredientTypeParameterizedTest {
    private final String ingredientTypeName;

    public IngredientTypeParameterizedTest(String ingredientTypeName) {
        this.ingredientTypeName = ingredientTypeName;
    }

    @Parameterized.Parameters
    public static Object[][] dataForTest() {
        return new Object[][]{  { "SAUCE"   },
                { "FILLING" } };
    }

    @Test
    public void checkThatIngredientTypeExists() {
        Assert.assertNotNull(IngredientType.valueOf(ingredientTypeName));
    }
}