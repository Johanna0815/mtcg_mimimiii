package com.mimimiii.battleTools.server;

import com.mimimiii.application.MTCG;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {



        private ServerSocket serverSocket = null;
        private ServerApp app;
        private int port;

        public Server(int port, ServerApp app) {
            this.port = port;
            this.app = app;

            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }


    public void start() throws IOException {
            if (serverSocket == null) {
                System.out.println("Server can not be started, because the socket is null!");
                return;
            }
            System.out.println("Start http-server...");
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("http-server running at: http://localhost:" + this.port);

            this.run();
        }


        // nimmt den request entgegen von client.
        private void run() {

            while (true) {
                try {

                    // accepts connection to the client.
                    // Socket clientsocket = this.serversocket.accept(); // when it comes back here, one must already be connected
                    Socket clientSocket = this.serverSocket.accept(); // kommt verbindung an
                    RequestHandler requestHandler = new RequestHandler(clientSocket, this.app); // verbindung in requesthandler  // neue instance der klasse
                    Thread thread = new Thread(requestHandler); // neue thread gestartet
                    thread.start();


                } catch (IOException exc) {
                    exc.printStackTrace(); // werdegang bis zur exception
                }
            }


        }
    }


            /*

            clientsocket.close();
        } catch (IOException exc) {
            exc.printStackTrace();
            // hier ende, dann habe ich exception geschmissen
            return;
        }

// The BufferedReader class of Java is used to read the stream of characters
// from the specified source (character-input stream). The constructor of this class accepts an InputStream object as a parameter.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
// bis hier liest 1. zeile header.
            System.out.println(bufferedReader.readLine());
            //     System.out.println(user.Username + "" + user.Password);
            //       System.out.println(bufferedReader.readLine());


    }

    }

             */


