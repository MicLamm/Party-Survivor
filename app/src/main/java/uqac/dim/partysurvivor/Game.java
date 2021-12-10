package uqac.dim.partysurvivor;

import java.io.Serializable;

public class Game implements Serializable {
    private String details;
    private String gagner;
    private String gameName;
    private String imageUrl;
    private String regle;
    private String resume;

    public Game(String details, String gagner, String gameName, String imageUrl, String regle, String resume) {
        this.details = details;
        this.gagner = gagner;
        this.gameName = gameName;
        this.imageUrl = imageUrl;
        this.regle = regle;
        this.resume = resume;
    }

    public Game() {
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getGagner() {
        return gagner;
    }

    public void setGagner(String gagner) {
        this.gagner = gagner;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRegle() {
        return regle;
    }

    public void setRegle(String regle) {
        this.regle = regle;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
