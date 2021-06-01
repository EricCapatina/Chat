package Server;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.sql.*;

import org.apache.commons.lang3.StringUtils;

public class ServerWorker extends Thread
{
    private final Socket _clientSocket;
    private final Server _server;
    private String _login = null;
    private OutputStream _outputStream;
    private HashSet<String> topicSet = new HashSet<>();

    public ServerWorker(Server server, Socket clientSocket)
    {
        _server = server;
        _clientSocket = clientSocket;
    }

    @Override
    public void run()
    {
        try
        {
            handleClientSocket();
        } catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException
    {
        InputStream inputStream = _clientSocket.getInputStream();
        this._outputStream = _clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ( (line = reader.readLine()) != null)
        {
            String[] tokens = StringUtils.split(line);
            if (tokens != null && tokens.length > 0)
            {
                String cmd = tokens[0];
                if ("logoff".equals(cmd) || "quit".equalsIgnoreCase(cmd))
                {
                    handleLogoff();
                    break;
                }
                else if ("login".equalsIgnoreCase(cmd))
                {
                    handleLogin(_outputStream, tokens);
                }
                else if ("msg".equalsIgnoreCase(cmd))
                {
                    String[] tokensMsg = StringUtils.split(line, null, 3);
                    handleMessage(tokensMsg);
                }
                else if ("join".equalsIgnoreCase(cmd))
                {
                    handleJoin(tokens);
                }
                else if ("leave".equalsIgnoreCase(cmd))
                {
                    handleLeave(tokens);
                }
                else
                {
                    String msg = "unknown " + cmd + "\n";
                    _outputStream.write(msg.getBytes());
                }
            }
        }

        _clientSocket.close();
    }

    private void handleLeave(String[] tokens)
    {
        if(tokens.length > 1)
        {
            String topic = tokens[1];
            topicSet.remove(topic);
        }
    }

    public boolean isMemberOfTopic(String topic)
    {
        return topicSet.contains(topic);
    }

    private void handleJoin(String[] tokens)
    {
        if(tokens.length > 1)
        {
            String topic = tokens[1];
            topicSet.add(topic);
        }
    }

    private void handleMessage(String[] tokens) throws IOException
    {
        String send = tokens[1];
        String msgBody = tokens[2];

        boolean isTopic = send.charAt(0) == '#';

        List<ServerWorker> workerList = _server.getWorkerList();
        for(ServerWorker worker : workerList)
        {
            if(isTopic)
            {
                if(worker.isMemberOfTopic(send))
                {
                    String msg = "msg " + send + ": " + _login + " " + msgBody + "\n";
                    worker.send(msg);
                }
            }
            else
            {
                if (send.equalsIgnoreCase(worker.getLogin()))
                {
                    String msg = "msg " + _login + " " + msgBody + "\n";
                    worker.send(msg);
                }
            }
        }
    }

    private void handleLogoff() throws IOException
    {
        _server.removeWorker(this);
        List<ServerWorker> workerList = _server.getWorkerList();
        String msg = "offline " + _login + "\n";
        for(ServerWorker worker : workerList)
        {
            if(!_login.equals(worker.getLogin()))
            {
                worker.send(msg);
            }
        }
        _clientSocket.close();
    }

    private Connection connectToDB()
    {
        String url = "jdbc:sqlite:C:/Users/Краб/IdeaProjects/Chat/src/Server/Database/Chat.db";
        Connection conn = null;
        try
        {
            System.out.println("Connection succesful");
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException
    {
        if (tokens.length == 3)
        {
            String login = tokens[1];
            String password = tokens[2];
            String sql = "SELECT name, password FROM users WHERE name LIKE '" + login + "' AND password LIKE '" + password + "'";
            try (Connection conn = this.connectToDB();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql))
            {
                if(rs.next())
                {
                    if ((login.equals(rs.getString("name")) && password.equals(rs.getString("password"))))
                    {
                        String msg = "ok login\n";
                        outputStream.write(msg.getBytes());
                        this._login = login;
                        System.out.println("User logged in succesfully: " + login);

                        List<ServerWorker> workerList = _server.getWorkerList();

                        for(ServerWorker worker : workerList)
                        {
                            if (worker.getLogin() != null)
                            {
                                if (!login.equals(worker.getLogin()))
                                {
                                    String msg2 = "online " + worker.getLogin() + "\n";
                                    send(msg2);
                                }
                            }
                        }

                        String onlineMsg = "online " + login + "\n";
                        for(ServerWorker worker : workerList)
                        {
                            if (!login.equals(worker.getLogin()))
                            {
                                worker.send(onlineMsg);
                            }
                        }

                    }
                    else
                    {
                        String msg = "error login\n";
                        outputStream.write(msg.getBytes());
                        System.err.println("Login failed for " + login);
                    }

                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }

        }
    }

    private void send(String msg) throws IOException
    {
        if(_login != null)
        {
            _outputStream.write(msg.getBytes());
        }
    }

    public String getLogin()
    {
        return _login;
    }
}
