package ChatRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: ZhangXinLe
 * @CreateTime: 2018-11-30 23:08
 */
class Myserver {
    public static List<Socket> socketList = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(3000);
        while (true) {
            Socket s = ss.accept();
            socketList.add(s);
            new Thread(new ServerThread(s)).start();
        }

    }

    public static class ServerThread implements Runnable {
        Socket s = null;
        BufferedReader br = null;

        public ServerThread(Socket s) throws IOException {
            this.s = s;
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }

        @Override
        public void run() {
            try {
                String content = null;
                while ((content = readFromClient()) != null) {
                    for (Socket socket : Myserver.socketList) {
                        if (socket != s) {
                            PrintStream ps = new PrintStream(socket.getOutputStream());

                            ps.println(content);
                        }
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        private String readFromClient() {
            try {
                return br.readLine();
            } catch (IOException e) {
                Myserver.socketList.remove(s);
            }
            return null;
        }
    }

}
