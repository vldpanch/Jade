package jade2;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import java.util.ArrayList;
import java.util.Arrays;

//class MainController {
//    private static final int numberOfAgents = 10;
//    void initAgents() {
//
//// Retrieve the singleton instance of the JADE Runtime
//        Runtime rt = Runtime.instance();
////Create a container to host the Default Agent
//        Profile p = new ProfileImpl();
//        p.setParameter(Profile.MAIN_HOST, "localhost");
//        p.setParameter(Profile.MAIN_PORT, "10098");
//        ContainerController cc = rt.createMainContainer(p);
//        HashMap<Integer,String>	neighbors = new	HashMap<Integer, String>();
//        neighbors.put(1, "2, 4, 8");
//        neighbors.put(2, "1, 3, 6");
//        neighbors.put(3, "1, 4, 5");
//        neighbors.put(4, "1, 3, 8");
//        neighbors.put(5, "4, 3, 7, 10");
//        neighbors.put(6, "3, 2, 1, 9");
//        neighbors.put(7, "6, 2, 10");
//        neighbors.put(8, "6, 7, 5, 4");
//        neighbors.put(9, "1, 6, 4, 3");
//        neighbors.put(10, "9, 7, 5, 6");
//        try {
//            for(int i=1; i <= MainController.numberOfAgents; i++) {
//                AgentController	agent = cc.createNewAgent(Integer.toString (i),
//                        "ru.spbu.mas.DefaultAgent2", new Object[]{neighbors.get(i)});
//                agent.start();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
class MainController {

    private static final ArrayList<String[]> parameters = new ArrayList<>(Arrays.asList(
                    //1
                    new String[]{"1", "3", "5", "7"},
                    //2
                    new String[]{"11", "1", "4", "5"},
                    //3
                    new String[]{"22", "1", "2", "6"},
                    //4
                    new String[]{"33", "5", "3", "1"},
                    //5
                    new String[]{"44", "3", "6", "7"},
                    //6
                    new String[]{"55", "2", "1", "4", "9"},
                    //7
                    new String[]{"66", "2", "6", "10"},
                    //8
                    new String[]{"77", "6", "7", "1", "5"},
                    //9
                    new String[]{"88", "1", "3", "4", "5","8"},
                    //10
                    new String[]{"99", "2", "4", "6", "9"}));

    void initAgents() {
        // Retrieve the singleton instance of the JADE Runtime
        Runtime rt = Runtime.instance();
        // Create a container to host the Default Agent
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.MAIN_PORT, "10098");
        ContainerController cc = rt.createMainContainer(p);
        int i = 0;

        try {
            for (String[] agentParameter : parameters) {
                i++;// for agent name
                AgentController agent = cc.createNewAgent(Integer.toString(i), "DefaultAgentMy", agentParameter);

                agent.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
