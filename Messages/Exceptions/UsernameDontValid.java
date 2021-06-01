package Exceptions;

public class UsernameDontValid extends Exception{
    public UsernameDontValid(String describe){
        super(describe);
    }
    public UsernameDontValid(){
        super();
    }
}
