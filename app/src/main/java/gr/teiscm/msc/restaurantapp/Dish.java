package gr.teiscm.msc.restaurantapp;

import java.io.Serializable;

/**
 * Created by Cooper on 12/3/2016.
 */
public class Dish implements Serializable {
    private int dishPk;
    private String dishName;
    private Double dishPrice;
    private int dishType;
    private int dishCategoryA;
    private int dishCategoryB;
    private String dishImage;
    private String dishDescription;

    public Dish() {
        super();
    }

    public Dish(int dishPk, String dishName, Double dishPrice, int dishType, int dishCategoryA, int dishCategoryB, String dishImage, String dishDescription) {
        this.dishPk = dishPk;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishType = dishType;
        this.dishCategoryA = dishCategoryA;
        this.dishCategoryB = dishCategoryB;
        this.dishImage = dishImage;
        this.dishDescription = dishDescription;
    }

    public int getDishPk() {
        return dishPk;
    }

    public String getDishName() {
        return dishName;
    }

    public Double getDishPrice() {
        return dishPrice;
    }

    public int getDishType() {
        return dishType;
    }

    public int getDishCategoryA() {
        return dishCategoryA;
    }

    public int getDishCategoryB() {
        return dishCategoryB;
    }

    public String getDishImage() {
        return dishImage;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishPk(int dishPk) {
        this.dishPk = dishPk;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setDishPrice(Double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public void setDishType(int dishType) {
        this.dishType = dishType;
    }

    public void setDishCategoryA(int dishCategoryA) {
        this.dishCategoryA = dishCategoryA;
    }

    public void setDishCategoryB(int dishCategoryB) {
        this.dishCategoryB = dishCategoryB;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }
}
