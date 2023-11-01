package RoundTrip.controller;

import RoundTrip.model.Comment;
import RoundTrip.repository.CommentRepository;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Controller
@ServerEndpoint(value = "/comment/{username}")
public class CommentSocket {
    // cannot autowire static directly (instead we do it by the below
    // method
    private static CommentRepository commentRepo;

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

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(CommentSocket.class);

//    @OnMessage
//    public void onRatingMessage(Session session, String message) throws IOException {
//        // Handle new comments
//        logger.info("Entered into Comment: Got Comment:" + message);
//        String username = sessionUsernameMap.get(session);
//
//        // Check if the user has already submitted a rating
//        if (usernameHasSubmittedRating(username)) {
//            // No rating required for subsequent comments
//            saveComment(username, message, null);
//        } else {
//            // Extract rating and comment text from the message
//            String[] messageParts = message.split(" ");
//            int rating = Integer.parseInt(messageParts[0]); // Assumes that the rating is the first part
//            String commentText = String.join(" ", Arrays.copyOfRange(messageParts, 1, messageParts.length));
//
//            // Save the comment and the rating
//            saveComment(username, commentText, rating);
//        }
//    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {

        logger.info("Entered into Open");

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        //Send chat history to the newly connected user
        sendMessageToPArticularUser(username, getCommentHistory());

        // broadcast that new user joined
        String message = "User:" + username + " Welcome to this recipe's comment section. " +
                "\nPlease provide a rating for this recipe from 1 to 10 before commenting!";
        broadcast(message);
    }


//    @OnMessage
//    public void onMessage(Session session, String message) throws IOException {
//
//        // Handle new comments
//        logger.info("Entered into Comment: Got Comment:" + message);
//        String username = sessionUsernameMap.get(session);
//
//        // Direct message to a user using the format "@username <message>"
//        if (message.startsWith("@")) {
//            String destUsername = message.split(" ")[0].substring(1);
//
//            // send the message to the sender and receiver
//            sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
//            sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
//
//        }
//        else { // broadcast
//            broadcast(username + ": " + message);
//        }
//
//        // Saving chat history to repository
//        commentRepo.save(new Comment(username, message));
//    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        logger.info("Entered into Comment: Got Comment: " + message);
        String username = sessionUsernameMap.get(session);

        if (message.startsWith("@")) {
            // Handle direct messages
            String destUsername = message.split(" ")[0].substring(1);
            sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
            sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
        } else if (message.matches("\\d+ .*")) {
            // Handle messages that include ratings
            if (usernameHasSubmittedRating(username)) {
                // No rating required for subsequent comments
                saveComment(username, message, null);
            } else {
                String[] messageParts = message.split(" ");
                int rating = Integer.parseInt(messageParts[0]);
                String commentText = String.join(" ", Arrays.copyOfRange(messageParts, 1, messageParts.length));
                saveComment(username, commentText, rating);
            }
        } else {
            // Handle regular chat messages
            broadcast(username + ": " + message);
            commentRepo.save(new Comment(username, message));
        }
    }



    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        // remove the user connection information
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // broadcase that the user disconnected
        String message = username + " disconnected";
        broadcast(message);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }


    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
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
    private String getCommentHistory() {
        List<Comment> comments = commentRepo.findAll();

        // convert the list to a string
        StringBuilder sb = new StringBuilder();
        if(comments != null && comments.size() != 0) {
            for (Comment comment : comments) {
                sb.append(comment.getUserName() + ": " + comment.getContent() + "\n");
            }
        }
        return sb.toString();
    }

    private void saveComment(String username, String content, Integer rating) {
        // Direct message to a user using the format "@username <message>"
        if (content.startsWith("@")) {
            // ...
        } else { // broadcast
            broadcast(username + ": " + content);
        }

        // Saving chat history to repository
        commentRepo.save(new Comment(username, content, rating));
    }

    private boolean usernameHasSubmittedRating(String username) {
        List<Comment> userComments = commentRepo.findByUserName(username);

        // Check if any of the user's comments have a rating
        return userComments.stream().anyMatch(comment -> comment.getRating() != null);
    }

}
