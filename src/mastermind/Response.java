package mastermind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Response {
    private List<Gene> reply = new ArrayList<Gene>();

    public Response(int white, int black){
        for(int i = 0; i < white; i++){
            reply.add(new Gene(Colors.WHITE));
        }
        for(int i = 0; i < black; i++){
            reply.add(new Gene(Colors.BLACK));
        }
    }

    public List<Gene> getReply(){
        return reply;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        Iterator<Gene> it = reply.iterator();
        while(it.hasNext()){
            out.append(it.next().toString());
        }
        return out.toString();
    }
}
