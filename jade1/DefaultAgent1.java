package jade1;

import jade.core.Agent;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DefaultAgent1 extends Agent {

    String[] linkedAgents;
    HashMap<String, Integer> currentInfo = new HashMap<>();
    HashMap<String, Integer> newInfo = new HashMap<>();

    @Override
    protected void setup() {
        int Num = Integer.parseInt((String) getArguments()[0]);
        String id = getAID().getLocalName();
        newInfo.put(id, Num);
        System.out.println("Агент " + id +" готов,"+ " моё число = " + Num);
        linkedAgents = Arrays.copyOfRange(getArguments(), 1,
                getArguments().length, String[].class);
        addBehaviour(new FindAverage1(this, TimeUnit.SECONDS.toMillis(1)));
    }
}
