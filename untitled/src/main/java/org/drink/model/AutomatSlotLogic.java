package org.drink.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dennis Martens
 */
public class AutomatSlotLogic {
    private String message;
    private List<Integer> changeList;

    public AutomatSlotLogic() {
        this.changeList = new ArrayList<>();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getChangeList() {
        return changeList;
    }

    public void setChangeList(List<Integer> changeList) {
        this.changeList = changeList;
    }
}
