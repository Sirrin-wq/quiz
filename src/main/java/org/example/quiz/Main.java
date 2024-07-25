//package com.company;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;


class attributes{

    // SCANNER //
    Scanner scanner_One = new Scanner(System.in);


    // DATA BASE CREDENTIALS //
    String url = "jdbc:mysql://localhost:3306/cup";
    String password = "";
    String username = "";



































    // QUIZ ATTRIBUTES //

    String[] menu_Options = {"PLAY QUIZ","CREATE QUESTIONS","SEE ALL QUESTIONS","DELETE ALL QUESTIONS","DELETE SPECIFIC QUESTION", "UPDATE QUESTION", "EXIT"};
    int player_Menu_Select;
    String create_Question;
    String answer_One;
    String answer_Two;
    String answer_Three;
    String real_Answer;
    String query;
    String update_Query = "";

    int select_To_Delete;
    String confirm_Delete;

    int id;

    int numOfTries = 3;
    int playerScore = 0;

}


class main_Quiz extends attributes {

    public void prompt() {
        System.out.println("\n===================");
        System.out.println("\tQUIZ TIME !");
        System.out.println("===================");

        for (int i = 0; i < menu_Options.length; i++) {

            System.out.println((i + 1) + ". " + menu_Options[i]);

        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }


        System.out.println("\nSELECT MODE:");
        player_Menu_Select = scanner_One.nextInt();


        switch (player_Menu_Select) {
            case 1 -> {
                try {

                    Connection connection = DriverManager.getConnection(url,username,password);
                    Statement statement = connection.createStatement();
                    String total_Question_Query = "SELECT id FROM cup.quiz;";
                    ResultSet resultSet = statement.executeQuery(total_Question_Query);
                    int numOfRows = 0;
                    while (resultSet.next()) {
                        //id = resultSet.getInt("id");
                        numOfRows++;
                    }
                    System.out.println("Number of rows:" + numOfRows);
                    if (numOfRows>=5){
                        play();
                        System.out.println("PLAYING");
                    } else {
                        System.out.println("====================================================");
                        System.out.println("-- AT LEAST 5 QUESTIONS REQUIRED TO PLAY QUIZ --");
                        System.out.println("====================================================");
                        prompt();
                    }

                }

                catch (SQLException e){

                    System.out.println(e.getMessage());

                }

//                if (id>=5){
//                    play();
//                    System.out.println("PLAYING");
//                }else {
//                    System.out.println("====================================================");
//                    System.out.println("-- AT LEAST 5 QUESTIONS REQUIRED TO PLAY QUIZ --");
//                    System.out.println("====================================================");
//                    prompt();
//                }
            }
            case 2 -> create();
            case 3 -> see_All();
            case 4 -> delete_All();
            case 5 -> delete();
            case 6 -> update();
            case 7 -> {
                System.out.println("SHUTTING DOWN ...");
                System.exit(0);
            }
            default -> {
                System.out.println("-! INVALID INPUT !-");
                prompt();
            }
        }

    }

    public void delete_All(){

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e){

            System.out.println(e.getMessage());

        }

        System.out.println("ARE YOU SURE YOU WANT TO ERASE ALL DATA FROM THE STORAGE ? [ YES OR NO ]");
        Scanner confirm_Scanner = new Scanner(System.in);
        String confirmation = confirm_Scanner.next();

        switch (confirmation){
            case "yes","YES","y","Y":
                break;
            default:
                System.out.println("====================");
                System.out.println("BACK TO MAIN MENU");
                System.out.println("====================");
                prompt();
        }

        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            String query_One_Delete_All = "TRUNCATE TABLE quiz";
            statement.executeUpdate(query_One_Delete_All);

            System.out.println("=======================");
            System.out.println("-- ERASED ALL DATA --");
            System.out.println("=======================");
            connection.close();
            statement.close();

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        prompt();

    }

    public void questions_Created(){

        try {

            Connection connection = DriverManager.getConnection(url,username,password);

            Statement statement = connection.createStatement();

            String total_Question_Query = "SELECT id FROM cup.quiz;";

            ResultSet resultSet = statement.executeQuery(total_Question_Query);

            while (resultSet.next()) {

                id = resultSet.getInt("id");

                System.out.println("QUESTIONS NUMBERS CREATED: '" + id + "'");

            }

        }

        catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    public void create() {
        System.out.println("ENTER YOUR QUESTION: ");
        scanner_One.nextLine();
        create_Question = scanner_One.nextLine();

        System.out.println("ENTER ANSWER CHOICE ONE: ");
        answer_One = scanner_One.nextLine();
        if (answer_One.equals(create_Question)) {
            System.out.println("============================================");
            System.out.println("ERROR: !! QUESTION SAME AS THE OPTION !!");
            System.out.println("============================================");
            prompt();
        }

        System.out.println("ENTER ANSWER CHOICE TWO: ");
        answer_Two = scanner_One.nextLine();
        if (answer_Two.equals(answer_One)) {
            System.out.println("============================================");
            System.out.println("ERROR: !! CANNOT ENTER SAME OPTION TWICE !!");
            System.out.println("============================================");
            prompt();
        }

        if (answer_Two.equals(create_Question)) {
            System.out.println("============================================");
            System.out.println("ERROR: !! QUESTION SAME AS THE OPTION !!");
            System.out.println("============================================");
            prompt();
        }

        System.out.println("ENTER ANSWER CHOICE THREE: ");
        answer_Three = scanner_One.nextLine();

        if (Objects.equals(answer_Three, answer_Two)) {
            System.out.println("============================================");
            System.out.println("ERROR: !! CANNOT ENTER SAME OPTION TWICE !!");
            System.out.println("============================================");
            prompt();
        }

        if (Objects.equals(answer_Three, create_Question)) {
            System.out.println("============================================");
            System.out.println("ERROR: !! CANNOT QUESTION SAME AS THE OPTION !!");
            System.out.println("============================================");
            prompt();
        }

        do {
            // Change to real_Answer.equals(answer_One)
            System.out.println("CHOOSE REAL ANSWER OF THE QUESTION FROM THE ABOVE ANSWERS: ");
            real_Answer = scanner_One.nextLine();
            if (!real_Answer.equals(answer_One) && !Objects.equals(real_Answer, answer_Two) && Objects.equals(real_Answer, answer_Three)) {
                System.out.println("============================================================");
                System.out.println("!! REAL ANSWER DOES NOT MATCH THE ANSWER GIVEN ABOVE !! ");
                System.out.println("============================================================\n");
            }
        } while (!Objects.equals(real_Answer, answer_One) && !Objects.equals(real_Answer, answer_Two) && !Objects.equals(real_Answer, answer_Three));


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            query = "INSERT INTO quiz(question,answer_one,answer_two,answer_three,real_answer) VALUES ('" + create_Question + "','" + answer_One + "','" + answer_Two + "','" + answer_Three + "', '" + real_Answer + "')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("===========================================");
            System.out.println("-- ADDED TO THE DATABASE SUCCESSFULLY --");
            System.out.println("===========================================");
            questions_Created();
            System.out.println("===========================================");

            connection.close();
            statement.close();

            prompt();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
    }

    public void update() {
        System.out.println("Select the 'ID' of the question you would like to update: ");
        int updateId = scanner_One.nextInt();
        scanner_One.nextLine();

        System.out.println("Which part of the question would you like to update? (Enter a number)");
        System.out.println("\n1. Question");
        System.out.println("2. Answer one");
        System.out.println("3. Answer two");
        System.out.println("4. Answer three");
        System.out.println("5. Real answer");

        int updateElement = scanner_One.nextInt();
        scanner_One.nextLine();


        switch (updateElement) {
            case 1:

                System.out.println("Enter the updated value for 'Question':");
                String new_Create_Question = scanner_One.nextLine();
                update_Query = "UPDATE quiz\nSET question='"+new_Create_Question+"' WHERE id = " + updateId + " ";
                break;

            case 2:

                System.out.println("Enter the updated value for 'Answer one':");
                String new_Answer_One = scanner_One.nextLine();
                update_Query = "UPDATE quiz\nSET answer_one='"+new_Answer_One+"' WHERE id = " + updateId + " ";
                break;

            case 3:

                System.out.println("Enter the updated value for 'Answer two':");
                String new_Answer_Two = scanner_One.nextLine();
                update_Query = "UPDATE quiz\nSET answer_two='"+new_Answer_Two+"' WHERE id = " + updateId + " ";
                break;

            case 4:

                System.out.println("Enter the updated value for 'Answer three':");
                String new_Answer_Three = scanner_One.nextLine();
                update_Query = "UPDATE quiz\nSET answer_three='"+ new_Answer_Three +"' WHERE id = " + updateId + " ";
                break;

            case 5:

                System.out.println("Enter the updated value for 'Real answer':");
                String new_Real_Answer = scanner_One.nextLine();
                update_Query = "UPDATE quiz\nSET real_answer='"+ new_Real_Answer +"' WHERE id = " + updateId + " ";
                break;

            default:

                System.out.println("\n=================");
                System.out.println("- Invalid input -");
                System.out.println("=================");
                prompt();

        }

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            Connection new_Connection = DriverManager.getConnection(url, username, password);

            Statement new_Statement = new_Connection.createStatement();

            new_Statement.executeUpdate(update_Query);

            System.out.println("\n=====================");
            System.out.println("  UPDATE SUCCESSFUL");
            System.out.println("=====================");


            new_Connection.close();
            new_Statement.close();
        }
        catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        prompt();

    }

    public void play(){

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        }
        catch (ClassNotFoundException e){

            System.out.println(e.getMessage());

        }

        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            String local_Query = "select * from quiz";
            ResultSet resultSet = statement.executeQuery(local_Query);

            while (resultSet.next()){
                String question = resultSet.getString("question");
                String answer_one = resultSet.getString("answer_one");
                String answer_two = resultSet.getString("answer_two");
                String answer_three = resultSet.getString("answer_three");
                String real_answer = resultSet.getString("real_answer");

                Scanner scanner_One = new Scanner(System.in);
                int guess;
                String player_Choice = "";
                do {
                    System.out.println("[Player Score: " + playerScore + "]");
                    System.out.println("[Tries remaining: " + numOfTries + "]");
                    System.out.println("QUESTION: " + question);
                    System.out.println("1. " + answer_one);
                    System.out.println("2. " + answer_two);
                    System.out.println("3. " + answer_three);
                    System.out.println("\nSELECT YOUR ANSWER: [ ENTER NUMBER ] ");
                    guess = scanner_One.nextInt();

                    switch (guess){
                        case 1:
                            player_Choice=answer_one;
                            break;
                        case 2:
                            player_Choice=answer_two;
                            break;
                        case 3:
                            player_Choice=answer_three;
                            break;
                        default:
                            System.out.println("\n========================");
                            System.out.println("-- INVALID INPUT --");
                            System.out.println("========================");
                            play();
                    }
                    if (!player_Choice.equals(real_answer)) {
                        System.out.println("===================================");
                        System.out.println("-- INCORRECT GUESS, TRY AGAIN --");
                        System.out.println("===================================");
                        numOfTries--;
                        if (numOfTries == 0) {
                            System.out.println("Sorry you have run out of tries. The game is over");
                            System.exit(0);
                        }
                    }
                } while (!player_Choice.equals(real_answer));

                if (player_Choice.equalsIgnoreCase(real_answer)){
                    System.out.println("======================");
                    System.out.println("-- CORRECT GUESS --");
                    System.out.println("======================");
                    playerScore += 5;
                }
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public void see_All(){
        try {

            Connection connection = DriverManager.getConnection(url,username,password);
            query = "SELECT * FROM cup.quiz";
            Statement statement = connection.createStatement();
            ResultSet result_Set =  statement.executeQuery(query);
            System.out.println("--------------------");
            System.out.println("LIST OF QUESTIONS : ");
            System.out.println("--------------------");
            while (result_Set.next()) {
                int id = result_Set.getInt("id");
                String question = result_Set.getString("question");
                String answerOne = result_Set.getString("answer_one");
                String answerTwo = result_Set.getString("answer_two");
                String answerThree = result_Set.getString("answer_three");
                System.out.printf(
                        """
                        ==============================================================================================================================================================================
                          ID -> %d   |      QUESTION -> %s      |      OPTION ONE -> %s      |      OPTION TWO -> %s      |        OPTION THREE -> %s\s
                        ===============================================================================================================================================================================
                       \s""",
                        id, question, answerOne, answerTwo, answerThree);
            }

            System.out.println();
            prompt();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(){

        System.out.println("ENTER THE QUESTION 'ID' YOU WANT TO DELETE: ");
        select_To_Delete = scanner_One.nextInt();

        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            String query_New = "SELECT * FROM cup.quiz WHERE id="+select_To_Delete+" ";
            Statement statement = connection.createStatement();
            ResultSet result_Set_Two =  statement.executeQuery(query_New);
            System.out.println("====================================================================================================================================");
            while (result_Set_Two.next()) {
                int id = result_Set_Two.getInt("id");
                String question = result_Set_Two.getString("question");
                String answerOne = result_Set_Two.getString("answer_one");
                String answerTwo = result_Set_Two.getString("answer_two");
                String answerThree = result_Set_Two.getString("answer_three");
                System.out.printf(
                        """
                        ID: %d   |   QUESTION: %s   |    OPTION ONE: %s    |    OPTION TWO: %s    |    OPTION THREE: %s
                        ====================================================================================================================================
                        """,
                        id, question, answerOne, answerTwo, answerThree);

            }

            System.out.println("ARE YOU SURE YOU WANT TO DELETE THIS ? [ YES | NO ]");
            confirm_Delete = scanner_One.next();

            if (confirm_Delete.equalsIgnoreCase("yes")){
                String query_To_Confirm = "DELETE FROM quiz WHERE id = "+select_To_Delete+" ";
                statement.executeUpdate(query_To_Confirm);
                System.out.println("========================");
                System.out.println("-- CONFIRMED DELETE --");
                System.out.println("========================\n");
                prompt();
            }
            else {
                System.out.println("======================");
                System.out.println("-- DELETE FAILED --");
                System.out.println("======================\n");
                prompt();
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}


public class Main {
    public static void main(String[] args) {
        main_Quiz mq = new main_Quiz();
        mq.prompt();
    }
}


// create table quiz(id INT PRIMARY KEY AUTO_INCREMENT,question VARCHAR(255) NOT NULL,
// answer_one VARCHAR(30) NOT NULL,answer_two VARCHAR(30) NOT NULL,
// answer_three VARCHAR(30) NOT NULL, real_answer VARCHAR(30));
// Create method to create a connection so you dont have to keep creating one for each method
// Create update
// Add score feature
// Add number of tries
// Replace id check with count of questions use (resultset size)