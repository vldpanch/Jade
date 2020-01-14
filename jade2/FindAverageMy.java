package jade2;
import jade.core.AID;

import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;


import java.text.DecimalFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

//public class FindAverage2 extends TickerBehaviour {
//    //  chance of 3-> 5 connection break
//    private final static double CONNECTION_BREAK = 0.3;
//    //  connection DELAY
//    private final static double NETWORK_DELAY = 0.15;
//    // ERROR in values
//    private final static int MAX_DELAY = 10;
//    private final static double ERROR = 0.05;
//    // Agent
//    private final DefaultAgent2 agent;
//    private State currentStep = State.SEND;
//    private int counter = 0;
//        public enum State {
//            SEND,
//            RECEIVE,
//            OFF;
//        }
//    FindAverage2(DefaultAgent2 agent, long period) {
//            super(agent, period);
//            this.setFixedPeriod(true);
//            this.agent = agent;
//
//        }
//        @Override
//        protected void onTick() {
//            counter ++;
//            switch (currentStep) {
//                case SEND:
//                    send();
//                    break;
//                case RECEIVE:
//                    receive();
//                    break;
//                case OFF:
//                    off();
//                    break;
//                default:
//                    block();
//            }
//        }
//    private double generateNoise() {
//        double noise = 0.1*Math.sin(Instant.now().toEpochMilli());
//        return (agent.getMyNumber() - noise);
//    }
//
//    private void send () {
//        double noisyContent=generateNoise();
//        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
//
//        for (String linkedAgent : agent.linkedAgents) {
//            //Генерируем возможность обрыва связи.
//            if (agent.getAID().getLocalName().equals("6")
//                    && linkedAgent.equals("3")) {
//                double connectionExistParam = Math.random();
//                if (connectionExistParam > CONNECTION_BREAK) {
//                    continue;
//                }
//            }
//            msg.addReceiver(new AID(linkedAgent, AID.ISLOCALNAME));
//            //if 8->4 network delay = 0.15
//            if (agent.getAID().getLocalName().equals("8")
//                    && linkedAgent.equals("4")) {
//                double networkDelay = Math.random();
//                if (networkDelay > NETWORK_DELAY) {
//                    //Random delay
//                    int delay = (int) (Math.random() * MAX_DELAY);
//
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(delay);
//                    } catch (InterruptedException e) {
//                        System.out.println(e.toString());
//                    }
//                }
//            }
//        }
//        msg.setContent(String.valueOf(noisyContent));
//                agent.send(msg);
//                currentStep = State.RECEIVE;
//                }
//
//    private void receive () {
//            double res = 0;
//            Set<String> processed = new HashSet<>();
//            double AgentNumber = agent.getMyNumber();
//            while ((agent.receive()) != null) {
//                ACLMessage msg = agent.receive();
//                if (msg != null) {
//                    if (processed.isEmpty() || !processed.contains(msg.getSender().getLocalName())) {
//                        double NumberReceived = Double.parseDouble(msg.getContent());
//
//                        System.out.println("Agent Id: "+ agent.getAID().getLocalName()+". Step "+
//                                counter + ") Received " + NumberReceived);
//
//                        res += NumberReceived - AgentNumber;
//                        processed.add(msg.getSender().getLocalName());
//
//                    }
//                    else{
//                        if(processed.size()==agent.linkedAgents.length){
//                            break;
//                        }
//                    }
//                }
//            }
//            agent.setMyNumber(agent.getMyNumber()+ERROR*res);
//
//            if (counter >= 50) {
//                off();
//            }
//            currentStep = State.SEND;
//        }
//
//    private void off() {
//        String name = agent.getAID().getLocalName();
//        DecimalFormat df = new DecimalFormat("#.######");
//
//        System.out.println("The end! " + counter + ") " + "Agent Id: " + name
//                + ". Count average : " + df.format(agent.getMyNumber()));
//
////End container
//        jade.wrapper.AgentContainer container = agent.getContainerController();
//        agent.doDelete();
//        new Thread(() -> {
//            try {
//                container.kill();
//            } catch (StaleProxyException ignored) {
//            }
//        }).start();
//    }
public class FindAverageMy extends TickerBehaviour {

    // 1->3 break of connection
    private final static double con_break = 0.2;
    // 6->9 Connection network delay
    private final static double def_del = 0.15;
    // for rand del
    private final static int max_del = 10;
    // error
    private static final double error = 0.3;

    private final DefaultAgentMy agent;
    private int counter = 0;
    private State step = State.Send;

        FindAverageMy(DefaultAgentMy agent, long period) {
        super(agent, period);
        this.setFixedPeriod(true);
        this.agent = agent;
    }
    @Override
    protected void onTick() {
        counter++;

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
    private double Noise() {
        double noise = 0.1*Math.sin(Instant.now().toEpochMilli());
        return (agent.get() - noise);
    }



    public enum State {
        Send,
        Receive,
        Stop;
    }
    private void send() {
        double noisyContent=Noise();

        System.out.println("Агент " + agent.getAID().getLocalName() + " отправляет " + noisyContent +
                " агенту " + Arrays.toString(agent.linkedAgents));

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        for (String linkedAgent : agent.linkedAgents) {
            //3 - 6 connection break
            if (agent.getAID().getLocalName().equals("1")
                    && linkedAgent.equals("2")) {
                double connectionSuccess = Math.random();
                if (connectionSuccess > con_break) {
                    continue;
                }
            }
            msg.addReceiver(new AID(linkedAgent, AID.ISLOCALNAME));

            //if 6->9 network delay = 0.15
            if (agent.getAID().getLocalName().equals("6")
                    && linkedAgent.equals("9")) {
                double rand_del = Math.random();
                if (rand_del > def_del) {
                    //Random delay
                    int true_delay = (int) (Math.random() * max_del);

                    try {
                        TimeUnit.MILLISECONDS.sleep(true_delay);
                    } catch (InterruptedException e) {
                        System.out.println(e.toString());
                    }
                }
            }
        }
        msg.setContent(String.valueOf(noisyContent));
        agent.send(msg);
        step = State.Receive;
    }

    private void receive() {
        double sum = 0;
        Set<String> processed = new HashSet<>();
        double Prev_Num = agent.get();
        while ((agent.receive()) != null) {
            ACLMessage msg = agent.receive();
            if (msg != null) {
                if (processed.isEmpty() || !processed.contains(msg.getSender().getLocalName())) {
                    double Received_num = Double.parseDouble(msg.getContent());
                    System.out.println("Агент "+ agent.getAID().getLocalName()+  " получает " + Received_num);
                    sum += Received_num - Prev_Num;
                    processed.add(msg.getSender().getLocalName());
                }
                else{
                    if(processed.size()==agent.linkedAgents.length){
                        break;
                    }
                }
            }
        }
        agent.set(Prev_Num + error * sum);

        if (counter >= 100) {
            Stop();
        }
        step = State.Send;
    }
    private void Stop() {
        // end of counting
        String id = agent.getAID().getLocalName();
        DecimalFormat df = new DecimalFormat("#.######");
        // result
        System.out.println("Счетчик достиг -  " + counter +  ", Результат,вычисленный агентом -  " + id
                + " равен -  " + df.format(agent.get()));
        // terminate agents's work
        jade.wrapper.AgentContainer container = agent.getContainerController();
        agent.doDelete();
        new Thread(() -> {
            try {
                container.kill();
            } catch (StaleProxyException ignored) {
            }
        }).start();
    }
}