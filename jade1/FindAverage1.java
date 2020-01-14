package jade1;
import jade.core.AID;

import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;

import java.text.DecimalFormat;
import java.util.*;

public class FindAverage1 extends TickerBehaviour {

    private final DefaultAgent1 agent;
    private State step = State.Send;

        FindAverage1(DefaultAgent1 agent, long period) {
        super(agent, period);
        this.setFixedPeriod(true);
        this.agent = agent;
    }

    @Override
    protected void onTick() {
        switch (step) {
            case Send:
                send();
                break;
            case Receive:
                receive();
                break;
            case Stop:
                stop();
                break;
            default:
                block();
        }
    }

    public enum State {
        Send,
        Receive,
        Stop;
    }
    private void send() {

        System.out.println("Агент " + agent.getAID().getLocalName() + " отправляет " +
                Utils.mapToString(agent.newInfo) + " агентам: " + Arrays.toString(agent.linkedAgents));

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        for (String linkedAgent : agent.linkedAgents) {
            msg.addReceiver(new AID(linkedAgent, AID.ISLOCALNAME));
            // сериализация
            msg.setContent(Utils.serializeToString(agent.newInfo));
            agent.send(msg);

            agent.currentInfo.putAll(agent.newInfo);
            agent.newInfo.clear();

            step = State.Receive;
            }
        }

    private void receive() {
        for (int i = 0; i < agent.linkedAgents.length; i++) {
            ACLMessage msg = agent.receive();
            if (msg != null) {
                // десериализация
                @SuppressWarnings("unchecked") HashMap<String, Integer> map = (HashMap<String, Integer>)
                        Utils.deserializeFromString(msg.getContent());

                System.out.println("Агент - " + agent.getAID().getLocalName() +  "получает: " + Utils.mapToString(map));

                map.keySet().removeAll(agent.currentInfo.keySet());
                agent.newInfo.putAll(map);
            } else
                block();
        }
        step = agent.newInfo.size() > 0
                ? State.Send
                : State.Stop;
    }

    public void stop() {
        String id = agent.getAID().getLocalName();

        if (id.equals(agent.currentInfo.keySet().stream().max(Comparator.naturalOrder()).orElse(id))) {
        // result
        int sum = agent.currentInfo.values().stream().mapToInt(i -> i).sum();
        double average = (double) sum / agent.currentInfo.size();

        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println( "Агент - " +id + " вычислил среднее арифметическое, равное - " + df.format(average));
        // terminate agent's work
            jade.wrapper.AgentContainer container = agent.getContainerController();
            agent.doDelete();
            new Thread(() -> {
                try {
                    container.kill();
                } catch (StaleProxyException ignored) {
                }
            }).start();;
            }
        }
}