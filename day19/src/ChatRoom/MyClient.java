package ChatRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @Author: ZhangXinLe
 * @CreateTime: 2018-11-30 23:09
 */
public class MyClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 3000);
        new Thread(new ClientThread(s)).start();
        PrintStream ps = new PrintStream(s.getOutputStream());
        String line = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while ((line = br.readLine()) != null) {
            ps.println(line);
        }
    }
}

class ClientThread implements Runnable {

    private Socket s;
    BufferedReader br = null;

    public ClientThread(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String contect = null;
            while ((contect = br.readLine()) != null) {
                System.out.println(contect);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
