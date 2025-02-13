package model;

public class JournalApp {
    JournalApp journalApp; 

    public JournalApp(){
        journalApp = new JournalApp();
    }

    // EFFECT: display options on what the user can do
    //         if there's no entry yet, prompt the user to create a new entry 
    //         if there are entries, user will see a list of entries
    //         user can view past journal entry and stats, create a new entry
    //         delete a journal entry...
    //         display instruction as well 
    //         user can use prompt "create new entry" to go to the next step
    //         user can use prompt "delete entry xxxx-xx-xx" to delete
    //         user can use prompt "view entry xxxx-xx-xx" to view 
    //         user can use prompt "edit entry xxxx-xx-xx" to edit 
    public void displayMenu(){
        

    }

    // EFFECT: if user types "create new entry"... user can create a new entry 
    //         question - how to save it? ....
    //         if user types "delete  entry xxxx-xx-xx" user can delete the entry 
    //         with a confirmation window 
    //         user can use prompt "view entry xxxx-xx-xx" to view 
    //         user can use prompt "edit entry xxxx-xx-xx" to enter the write mode and edit 
    //         maybe we need to look into how to exit it out of a writing mode and then save 
    //         look into vim? 
    //         for each user input - we need to have a verification process 
    //         maybe we need a currentJournal thing like the currentPuppy... 
    public void handleUserInput(){
        

    }
}
