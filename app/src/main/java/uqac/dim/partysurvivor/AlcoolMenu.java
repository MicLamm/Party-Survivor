package uqac.dim.partysurvivor;

import java.io.Serializable;

public class AlcoolMenu implements Serializable {

    private String categoryName;
    private String imageUrl;

    public AlcoolMenu(String categoryName, String imageUrl) {
        this.categoryName = categoryName;
        this.imageUrl= imageUrl;
    }

    public AlcoolMenu() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
