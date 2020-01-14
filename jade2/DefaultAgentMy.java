package jade2;

import jade.core.Agent;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DefaultAgentMy extends Agent {

    String[] linkedAgents;
    private Double Num;

    Double get() {

        return this.Num;
    }

    void set(double Num) {

        this.Num = Num;
    }

    @Override
    protected void setup() {
        Num = Double.valueOf((String) getArguments()[0]);
        String id = getAID().getLocalName();
        System.out.println("Агент " + id +" готов,"+ " моё число = " + Num);
        linkedAgents = Arrays.copyOfRange(getArguments(), 1,
                getArguments().length, String[].class);
        addBehaviour(new FindAverageMy(this, TimeUnit.SECONDS.toMillis(1)));
    }
}
