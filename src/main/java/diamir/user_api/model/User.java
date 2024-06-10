package diamir.user_api.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;

@Entity
@Table(name = "USER")
public class User {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    @NotNull
    private int id;
    @Column(name = "NICKNAME")
    @NotNull
    private String nickname;
    @Column(name = "FULLNAME")
    @NotNull
    private String fullName;
    @Column(name = "EMAIL")
    @NotNull
    private String email;
    @Column(name = "PROFILE_PICTURE", columnDefinition = "TEXT")
    @Lob
    private String profilePicture;


    //region Getter and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }



    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }



    //endregion


}
