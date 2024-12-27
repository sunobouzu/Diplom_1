package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;
import static org.mockito.Mockito.when;


public class BurgerTest {
    private Burger burger;
    private Bun bunMock;





    @Before
    public void init() {
        bunMock = Mockito.mock(Bun.class);
        when(bunMock.getName()).thenReturn("Флюоресцентная булка R2-D3");
        when(bunMock.getPrice()).thenReturn(988.0f);

        burger = new Burger();
        burger.setBuns(bunMock);
    }

    // Метод для создания моков ингредиентов
    private Ingredient createIngredientMock(String name, IngredientType type, float price) {
        Ingredient ingredientMock = Mockito.mock(Ingredient.class);
        when(ingredientMock.getType()).thenReturn(type);
        when(ingredientMock.getName()).thenReturn(name);
        when(ingredientMock.getPrice()).thenReturn(price);
        return ingredientMock;
    }

    @Test
    public void addIngredientTest() {
        // Arrange
        Ingredient ingredientMockFilling = createIngredientMock("Кетчуп", IngredientType.FILLING, 1.0f);

        // Act
        burger.addIngredient(ingredientMockFilling);

        // Assert
        Assert.assertTrue(burger.ingredients.contains(ingredientMockFilling));
    }

    @Test
    public void removeIngredientTest() {
        // Arrange
        Ingredient ingredientMockFilling = createIngredientMock("Соус Spicy-X", IngredientType.FILLING, 1.0f);
        burger.addIngredient(ingredientMockFilling);
        int sizeBeforeRemoval = burger.ingredients.size();

        // Act
        burger.removeIngredient(0);

        // Assert
        Assert.assertEquals(sizeBeforeRemoval - 1, burger.ingredients.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeNonExistentIngredientTest() {
        // Act
        burger.removeIngredient(0); // Пытаемся удалить из пустого списка
    }

    @Test
    public void moveIngredientTest() {
        // Arrange
        Ingredient ingredientMockFilling = createIngredientMock("Соус Spicy-X", IngredientType.FILLING, 1.0f);
        burger.addIngredient(ingredientMockFilling);
        int originalIndex = burger.ingredients.indexOf(ingredientMockFilling);
        int newIndex = 0;

        // Act
        burger.moveIngredient(originalIndex, newIndex);

        // Assert
        Assert.assertEquals(newIndex, burger.ingredients.indexOf(ingredientMockFilling));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void moveIngredientThrowsExceptionForInvalidIndex() {
        // Act
        burger.moveIngredient(5, 0); // Неверный индекс
    }

    @Test
    public void getPriceTest() {
        // Arrange
        float expectedPrice = bunMock.getPrice() * 2 + 90.0f; // 2 булки + цена соуса
        Ingredient sauceMock = createIngredientMock("Соус Spicy-X", IngredientType.SAUCE, 90.0f);
        burger.addIngredient(sauceMock);

        // Act
        float actualPrice = burger.getPrice();

        // Assert
        Assert.assertEquals(expectedPrice, actualPrice, 0.01f);
    }

    @Test
    public void getReceiptTest() {
        // Arrange
        Ingredient ingredientMockFilling = createIngredientMock("Соус Spicy-X",IngredientType.SAUCE, 90.0f);
        burger.addIngredient(ingredientMockFilling);
        String actualReceipt = burger.getReceipt();
        String expectedReceipt = String.format(
                "(==== %s ====)%n" +
                        "= %s %s =%n" +
                        "(==== %s ====)%n" +
                        "%nPrice: %f%n",
                bunMock.getName(),
                IngredientType.SAUCE.toString().toLowerCase(), "Соус Spicy-X",
                bunMock.getName(), burger.getPrice()
        );

        // Act & Assert
        Assert.assertEquals(expectedReceipt, actualReceipt);
    }
}
