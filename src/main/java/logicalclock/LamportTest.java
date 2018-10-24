/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalclock;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Ian Gortan
 */
public class LamportTest {

    public static List<List<String>> eventsList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        System.out.println("starting ....");
        MessageBuffer buffer = new MessageBuffer(10);
        (new Thread(new LamportClient(buffer, 0))).start();
        (new Thread(new LamportClient(buffer, 1))).start();
        (new Thread(new LamportClient(buffer, 2))).start();

        while (eventsList.size() < 3) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        for (int i = 0; i < 10; i++) {
            System.out.printf("%-30s %-30s %-30s\n", eventsList.get(0).get(i), eventsList.get(1).get(i), eventsList.get(2).get(i));
        }
       // (new Thread(new TestReceiver(buffer, 1))).start();
        //(new Thread(new TestReceiver(buffer, 2))).start();
        //(new Thread(new testSender(buffer, 0))).start();
      }
}
    

