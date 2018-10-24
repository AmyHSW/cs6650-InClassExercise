package socketexample; /**
 *
 * @author Ian Gortan
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SocketClientMultithreaded {
    
    static CyclicBarrier barrier; 
    
    public static void main(String[] args)  {
        String hostName;
        final int MAX_THREADS = 100 ;
        int port;
        
        if (args.length == 2) {
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            hostName= null;
            port = 12031;  // default port in socketexample.SocketServer
        }
        barrier = new CyclicBarrier(MAX_THREADS);

        List<SocketClientThread> threads = new ArrayList<>();
        for (int i = 0; i < MAX_THREADS; i++) {
            threads.add(new SocketClientThread(hostName, port, barrier));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        final long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            //thread.start();
            executorService.execute(thread);
        }
//        executorService.shutdown();
//        try {
//            executorService.awaitTermination(60, TimeUnit.SECONDS);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
        long runtime = System.currentTimeMillis() - startTime;
        System.out.println("Total run time: " + runtime + " ms");

    }

      
}
