package vg;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class vgData {

    private ObjectProperty<ImageView> image;
    private StringProperty gameTitle;
    private IntegerProperty rating;
    private IntegerProperty hoursPlayed;
    private StringProperty platform;

    public vgData(byte[] img, String title, int rat, int hours, String plat) {
        ImageView imageView = new ImageView();
        imageView.setImage(this.getImageFromBytes(img));
        this.resizeImage(imageView);
        this.image = new SimpleObjectProperty(imageView);
        this.gameTitle = new SimpleStringProperty(title);
        this.rating = new SimpleIntegerProperty(rat);
        this.hoursPlayed = new SimpleIntegerProperty(hours);
        this.platform = new SimpleStringProperty(plat);

    }

    public ImageView getImage() {
        return this.image.get();
    }

    public void setImage(ImageView img) {
        this.image.set(img);
    }

    public String getGameTitle() {
        return this.gameTitle.get();
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle.set(gameTitle);
    }

    public int getRating() {
        return this.rating.get();
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public int getHoursPlayed() {
        return this.hoursPlayed.get();
    }

    public void setHoursPlayed(int hoursPlayed) {
        this.hoursPlayed.set(hoursPlayed);
    }

    public String getPlatform() {
        return this.platform.get();
    }

    public void setPlatform(String platform) {
        this.platform.set(platform);
    }

    public StringProperty gameTitleProperty() {
        return this.gameTitle;
    }

    public IntegerProperty ratingProperty() {
        return this.rating;
    }

    public IntegerProperty hoursPlayedProperty() {
        return this.hoursPlayed;
    }

    public StringProperty PlatformProperty() {
        return this.platform;
    }

    private Image getImageFromBytes(byte[] img) {
        Image image = null;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(img);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    private void resizeImage(ImageView img) {
        img.setFitWidth(150);
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
    }

}
