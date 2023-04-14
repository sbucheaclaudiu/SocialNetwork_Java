package com.example.socialntwrkgui.validators;

import com.example.socialntwrkgui.exceptions.ValidationException;
import com.example.socialntwrkgui.domain.User;

public class UserValidator implements Validator<User>{

    public void validate(User user) throws  ValidationException{
        String errors = "";
        if(user.getFirstName().isEmpty()){
            errors += "First name este empty.\n";
        }
        if(user.getSecondName().isEmpty()){
            errors += "Second name este empty.\n";
        }
        if(user.getID() <= 0){
            errors += "Id-ul nu este bun.\n";
        }
        boolean fname = user.getFirstName().chars().allMatch(Character::isLetter);
        if(!fname){
            errors += "First name trebuie sa contina doar litere.\n";
        }
        boolean sname = user.getFirstName().chars().allMatch(Character::isLetter);
        if(!sname){
            errors += "Second name trebuie sa contina doar litere.\n";
        }

        if(errors.length()>0)
            throw new ValidationException("\n" + errors);
    }
}
