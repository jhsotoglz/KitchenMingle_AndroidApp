package RoundTrip.websocket;

import RoundTrip.NotFoundException;
import RoundTrip.model.Comment;
import RoundTrip.model.Recipe;
import RoundTrip.model.Users;
import RoundTrip.repository.CommentRepository;
import RoundTrip.repository.RecipeRepository;
import RoundTrip.repository.UsersRepository;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;

@Controller
@ServerEndpoint(value = "/comment/{userId}/{recipeId}")
public class CommentSocket {
    // cannot autowire static directly (instead we do it by the below
    // method
    private static CommentRepository commentRepo;

    private static UsersRepository usersRepository;

    private static RecipeRepository recipeRepository;
    /*
     * Grabs the Comment Repository singleton from the Spring Application
     * Context.  This works because of the @Controller annotation on this
     * class and because the variable is declared as static.
     * There are other ways to set this. However, this approach is
     * easiest.
     */
    @Autowired
    public void setCommentRepository(CommentRepository repo) {
        commentRepo = repo;  // we are setting the static variable
    }

    @Autowired
    public void setUsersRepository(UsersRepository repo) {
        usersRepository = repo;  // we are setting the static variable
    }

    @Autowired
    public void setRecipeRepository(RecipeRepository repo){
        recipeRepository = repo;
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, Users> sessionUsernameMap = new Hashtable<>();
    private static Map<Users, Session> usernameSessionMap = new Hashtable<>();

    private static Map<Session, Recipe> sessionRecipeMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(CommentSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId, @PathParam("recipeId") Long recipeId)
            throws IOException {

        Optional<Users> findUser = usersRepository.findById(userId);
        Optional<Recipe> findRecipe = recipeRepository.findById(recipeId);

        if(findUser.isPresent() && findRecipe.isPresent()){
            Users user = findUser.get();
            Recipe recipe = findRecipe.get();

            logger.info("Entered into Open");

            // store connecting user information
            sessionUsernameMap.put(session, user);
            usernameSessionMap.put(user, session);
            sessionRecipeMap.put(session, recipe);

            //Send chat history to the newly connected user
            sendMessageToParticularUser(user, getCommentHistory(recipeId));

            // broadcast that new user joined
            String message = "User:" + user.getUsername() + " Welcome to this recipe's comment section. " +
                    "\nPlease provide a rating for this recipe from 1 to 10 before commenting!";
            broadcast(message);
        }else{
            throw new NotFoundException("User " + userId + " or Recipe " + recipeId +" not found.");
        }

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        logger.info("Entered into Comment: Got Comment: " + message);
        Users user = sessionUsernameMap.get(session);
        String username = user.getUsername();
        Recipe recipe = sessionRecipeMap.get(session);

        if (message.matches("\\d+ .*")) {
            // Handle messages that include ratings
            String[] messageParts = message.split(" ");
            int rating = Integer.parseInt(messageParts[0]);
            String commentText = String.join(" ", Arrays.copyOfRange(messageParts, 1, messageParts.length));
            saveComment(user, recipe, commentText, rating);
        } else {
            // Handle regular chat messages
            broadcast(username + ": " + message);
            commentRepo.save(new Comment(user, username, message, null, recipe));
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        // remove the user connection information
        Users user = sessionUsernameMap.get(session);
        String username = user.getUsername();
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(user);
        sessionRecipeMap.remove(session);

        // broadcast that the user disconnected
        String message = user.getUsername() + " disconnected";
        broadcast(message);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }


    private void sendMessageToParticularUser(Users user, String message) {

        try {
            usernameSessionMap.get(user).getBasicRemote().sendText(message);
        }
        catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }


    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }


    // Gets the Chat history from the repository
    private String getCommentHistory(Long recipeId) {
        List<Comment> comments = commentRepo.findByRecipeId(recipeId);

        StringBuilder sb = new StringBuilder();
        if(comments != null && !comments.isEmpty()) {
            for (Comment comment : comments) {
                sb.append(comment.getUserName()).append(": ").append(comment.getContent()).append("\n");
            }
        }
        return sb.toString();
    }


    private void saveComment(Users user, Recipe recipe, String content, Integer rating) {
        // Direct message to a user using the format "@username <message>"
        if (content.startsWith("@")) {
            // ...
        } else { // broadcast
            broadcast(user.getUsername() + ": " + content);
        }

        // Saving chat history to repository
        commentRepo.save(new Comment(user, user.getUsername(), content, rating, recipe));
    }

}
