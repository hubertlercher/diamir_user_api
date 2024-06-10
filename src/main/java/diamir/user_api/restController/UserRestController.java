package diamir.user_api.restController;

import diamir.user_api.model.User;
import diamir.user_api.respositories.UserRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utilities.LogUtils;


import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private static final Logger logger = LogManager.getLogger(UserRestController.class);
    private static final String className = "UserRestController";

    @Autowired
    UserRepository userRepository;

    @PostMapping()
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        boolean error = false;
        String errorMessage = "";

        if(!error) {
            error = bindingResult.hasErrors();
            errorMessage = bindingResult.toString();
        }

        if(!error) {
            try {
                userRepository.save(user);
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
                errorMessage = e.toString();
            }
        }

        ResponseEntity<?> result;
        if(!error) {
            result = new ResponseEntity<User>(user, HttpStatus.OK);
        }
        else {
            result = new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    @PostMapping("/{userId}/uploadProfilePicture")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) {
        try {
            byte[] profilePictureBytes = file.getBytes();
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            // Skalieren des Bildes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(file.getInputStream())
                    .size(200, 200)
                    .outputFormat("jpg")
                    .toOutputStream(outputStream);
            byte[] scaledImageBytes = outputStream.toByteArray();

            // Konvertieren in Base64
            String base64Image = Base64.getEncoder().encodeToString(scaledImageBytes);
            user.setProfilePicture(base64Image);
            userRepository.save(user); // Speichern des Users mit dem neuen Profilbild


            return new ResponseEntity<User>(user, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<String>(e.getMessage() + "error processing file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "{userId}")
    public ResponseEntity<?> getByIdPV(@PathVariable Integer userId) {
        logger.info(LogUtils.info(className, "getByIdPV", String.format("(%d)", userId)));
        ResponseEntity<?> result;
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = optUser.get();
            result = new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(String.format("User mit der Id = %d nicht vorhanden", userId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }





}
