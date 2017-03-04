import java.util.ArrayList;
import java.util.List;

public class JsonMessages {
    private final List<Message> list;

    public JsonMessages(List<Message> sourceList, int fromIndex, String to, String room) {
        this.list = new ArrayList<>();
        Message result = null;

        for(int i = fromIndex; i < sourceList.size(); i++){
            result = sourceList.get(i);
            if(result != null){
                if(result.getTo().equals(Constants.TO_ALL) || (result.getTo().equals(to))){
                    if(result.getRoom().equals(Constants.MAIN_ROOM)||result.getRoom().equals(room)){
                        list.add(result);
                    }
                }else{
                    list.add(null);
                }
            }
        }
    }
}
