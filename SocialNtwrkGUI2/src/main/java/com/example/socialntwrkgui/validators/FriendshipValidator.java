package com.example.socialntwrkgui.validators;

import com.example.socialntwrkgui.domain.Friendship;
import com.example.socialntwrkgui.exceptions.ValidationException;

public class FriendshipValidator implements Validator<Friendship> {

    public void validate(Friendship friendship) throws ValidationException {
        String errors = "";

        if(friendship.getID() <= 0){
            errors += "Id-ul nu este bun.\n";
        }
        if(friendship.getUser1() <= 0){
            errors += "Id-ul user 1 nu este bun.\n";
        }
        if(friendship.getUser2() <= 0){
            errors += "Id-ul user 2 nu este bun.\n";
        }

        if(errors.length()>0)
            throw new ValidationException("\n" + errors);
    }
}
