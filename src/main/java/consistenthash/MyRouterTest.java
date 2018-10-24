package consistenthash;

import consistenthash.sample.MyServiceNode;

import java.util.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class MyRouterTest {

  public static final int NUM_STRING = 10000;

  public static void main(String[] args) {
    Map<Node, Integer> nodeMap = new HashMap<>();
    List<String> stringList = new ArrayList<>();
    for (int i = 0; i < NUM_STRING; i++) {
      stringList.add(randomAlphanumeric(11));
    }

    //initialize 5 service node
    MyServiceNode node1 = new MyServiceNode("IDC1","127.0.0.1",8080);
    MyServiceNode node2 = new MyServiceNode("IDC1","127.0.0.1",8081);
    MyServiceNode node3 = new MyServiceNode("IDC1","127.0.0.1",8082);
    MyServiceNode node4 = new MyServiceNode("IDC1","127.0.0.1",8084);
    MyServiceNode node5 = new MyServiceNode("IDC2","127.0.0.1",8080);

    nodeMap.put(node1, 0);
    nodeMap.put(node2, 0);
    nodeMap.put(node3, 0);
    nodeMap.put(node4, 0);
    nodeMap.put(node5, 0);

    // Tested with different number of virtual nodes, turns out more virtual nodes will produce more uniform distribution
    ConsistentHashRouter<MyServiceNode> consistentHashRouter = new ConsistentHashRouter<>(
        Arrays.asList(node1,node2,node3,node4,node5),10);//10 virtual node
    goRoute(consistentHashRouter, stringList, nodeMap);

    for (Map.Entry<Node, Integer> entry: nodeMap.entrySet()) {
      System.out.println(entry.getKey() + " -> " + entry.getValue());
    }
  }

  private static void goRoute(
      ConsistentHashRouter<MyServiceNode> consistentHashRouter,
      List<String> stringList,
      Map<Node, Integer> nodeMap) {
    for (String requestIp : stringList) {
      Node pNode = consistentHashRouter.routeNode(requestIp);
      nodeMap.put(pNode, nodeMap.get(pNode) + 1);
    }
  }
}
