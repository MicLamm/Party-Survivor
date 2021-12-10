package uqac.dim.partysurvivor;

import java.io.Serializable;

public class Coktail implements Serializable {
    private String coktailName;
    private String detailsCoktail;
    private String imageUrl;
    private String ingredient;
    private String recette;

    public Coktail(String coktailName, String detailsCoktail, String imageUrl,String ingredient, String recette) {
        this.coktailName = coktailName;
        this.detailsCoktail = detailsCoktail;
        this.imageUrl = imageUrl;
        this.ingredient = ingredient;
        this.recette = recette;
    }

    public Coktail() {
    }

    public String getCoktailName() {
        return coktailName;
    }

    public void setCoktailName(String coktailName) {
        this.coktailName = coktailName;
    }

    public String getDetailsCoktail() {
        return detailsCoktail;
    }

    public void setDetailsCoktail(String detailsCoktail) {
        this.detailsCoktail = detailsCoktail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getRecette() {
        return recette;
    }

    public void setRecette(String recette) {
        this.recette = recette;
    }
}
