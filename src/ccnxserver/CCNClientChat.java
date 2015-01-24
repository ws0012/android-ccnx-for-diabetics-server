/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccnxserver;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccnx.ccn.apps.ccnchat.CCNChatNet;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;
import org.pl.lecznica.messages.MessageFactory;
import org.pl.lecznica.util.Messager;

/**
 *
 * @author Wojtek
 */
public class CCNClientChat  implements Runnable, CCNChatNet.CCNChatCallback {
    private String _name;
    private String _namespace;
    private Mode _mode = Mode.MODE_LISTEN;
    private CCNClientFrame _parent;
    protected final CCNRestrictedChat _chat;
    private final Thread _thd;
    protected final LinkedBlockingQueue<String> _queue = new LinkedBlockingQueue<String>();
    /**
     * @return the _mode
     */
    public Mode getMode() {
        return _mode;
    }

    /**
     * @param _mode the _mode to set
     */
    public void setMode(Mode _mode) {
        this._mode = _mode;
    }

    public enum Mode {
            MODE_START,
            MODE_SEND,
            MODE_LISTEN,
            MODE_CLOSE
    }

    public CCNClientChat(String namespace, String restrictedName) throws MalformedContentNameStringException {

        _chat = new CCNRestrictedChat(this, namespace, restrictedName);
        _thd = new Thread(this, "CCNClientChat:"+ namespace);
        _thd.setDaemon(true);    
        
        
        _parent = new CCNClientFrame(this, namespace, restrictedName);
        _name = restrictedName;
        _namespace = namespace;
    }
    
    public static void create(String namespace, String restrictedName) {

        CCNServer client = null;
        try {
                client = new CCNRestrictedServer(namespace, restrictedName);
                client.start();


        } catch (MalformedContentNameStringException e) {
                System.err.println("Not a valid ccn URI: " + namespace + ": " + e.getMessage());
                e.printStackTrace();
        } catch (ConfigurationException e) {
                System.err.println("Configuration exception running ccnChat: " + e.getMessage());
                e.printStackTrace();
        } catch (IOException e) {
                System.err.println("IOException handling chat messages: " + e.getMessage());
                e.printStackTrace();
        }
	}
    
    @Override
    public void run() {
        output("CCNClientChat:"+_namespace+_name+" run \n");
        Messager messager = new Messager();
        while(true) {        
            switch(getMode()) {
            case MODE_START:
            {
                    output("CCNClientChat:"+_namespace+_name+"MODE_START");
                    setMode(Mode.MODE_LISTEN);
                    new Thread(new Runnable(){
                        public void run(){
                            try { 
                                _chat.listen();
                            } catch (ConfigurationException ex) {
                                Logger.getLogger(CCNClientChat.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(CCNClientChat.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (MalformedContentNameStringException ex) {
                                Logger.getLogger(CCNClientChat.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }).start();
                    

            }
                
            break;
            case MODE_CLOSE:
                String closeMessage = messager.getStringMessage(_name, MessageFactory.CLOSE_MESSAGE);
                try {
                sendMessage(closeMessage);
                stop();
                } catch (IOException ex) {
                    Logger.getLogger(CCNServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                setMode(Mode.MODE_LISTEN);
            break;  
    
                
            case MODE_LISTEN:

                String message = null;

                try {
                    message = _queue.poll(50, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                }

                if( null != message ) {
                    output("CCNClientChat:"+_namespace+_name+": "+ message+" \n");
                    _parent.chatPanel.setRcvMessage(message+" \n");
                }
                break;
            }

        }
    }
    /**
     * Send a message to the console.  Message should include its
     * own newline, if so desired.
     * @param message
     */
    protected void output(String message) {
            System.out.print(message);
    }    

    /**
     * Called by window thread when when window closes
     */
    public void stop() throws IOException {
            _chat.shutdown();
    }
    public static void usage() {
            System.err.println("usage: CCNClientChat <ccn URI>");
    }
    
    /**
     * This blocks until _chat.shutdown() called.
     * 
     * This is run in the main() thread.
     * 
     * @throws IOException 
     * @throws MalformedContentNameStringException 
     * @throws ConfigurationException 
     */
    public void start() throws ConfigurationException, MalformedContentNameStringException, IOException {
            _thd.start();
            _chat.setLogging(Level.WARNING);

    }


    public void recvMessage(String message) {
            _queue.add(message);
//                System.out.print("Rec message: "+message );
    }

    public void sendMessage(String message) throws IOException {
        _chat.sendMessage(message);
    }

	public static void main(String[] args) {
		if (args.length != 2) {
			usage();
			System.exit(-1);
		}
		CCNClientChat client;
		try {
			client = new CCNClientChat("ccnx:/"+ args[0], args[1]);
			client.start();
                        
		} catch (MalformedContentNameStringException e) {
			System.err.println("Not a valid ccn URI: " + args[0] + args[1] +": " + e.getMessage());
			e.printStackTrace();
		} catch (ConfigurationException e) {
			System.err.println("Configuration exception running ccnChat: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException handling chat messages: " + e.getMessage());
			e.printStackTrace();
		}
	}
       
}
