/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccnxserver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccnx.ccn.apps.ccnchat.CCNChatNet;


import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;
import org.pl.lecznica.messages.MessageFactory;
import org.pl.lecznica.util.Lecznica;
import org.pl.lecznica.util.Messager;

/**
 *
 * @author Wojtek
 */
public class CCNServer  implements Runnable, CCNChatNet.CCNChatCallback {
    
    // =========================================================
    // Internal methods
    private final static String NAMESPACE = "ccnx:/org/pl/lecznica/";
    protected final CCNServerChat _chat;
    private final Thread _thd;
    protected final LinkedBlockingQueue<String> _queue = new LinkedBlockingQueue<String>();
    protected Lecznica _lecznica;
    protected final String _namespace;    

/**
 * A text-based interface to CCNChat.
 * 
 * Because we don't want to depend on an external curses library,
 * the UI uses a 2-mode interface.  The normal mode is OUTPUT, which
 * means received chat messages are displayed immediately.  If the
 * user pressed ENTER, we switch to INPUT mode.  Received chat messages
 * are queued and only displayed when the user leaves INPUT mode
 * by pressing ENTER again.
 * 
 * System.in is buffered, so we can only get when the user presses ENTER.
 * We will save whatever what typed before and put it as the start of
 * the INPUT line.
 */


	public static void usage() {
		System.err.println("usage: CCNServer <ccn URI>");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			usage();
			System.exit(-1);
		}
		CCNServer client;
		try {
			client = new CCNServer("ccnx:/" + args[0]);
			client.start();
                        
		} catch (MalformedContentNameStringException e) {
			System.err.println("Not a valid ccn URI: " + args[0] + ": " + e.getMessage());
			e.printStackTrace();
		} catch (ConfigurationException e) {
			System.err.println("Configuration exception running ccnChat: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException handling chat messages: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void run() {
            output("CCNServer:"+_namespace+" run \n");
//            try {
//                _chat.sendMessage("CCNXServer start");
//            } catch (IOException ex) {
//                Logger.getLogger(CCNXServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
		while(true) {
                    String message = null;
//                    output("CCNServer:"+_namespace+" run \n");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CCNServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                        try {
                            message = _queue.poll(50, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException e) {
                        }

                        if( null != message ) { 
                            output("CCNServer:"+_namespace+" "+message+" \n");
                            
                            final String name = _lecznica.getMessageName(message);
                            if(name != null){
                                CCNRestrictedServer server = _lecznica.getServerServer4Name(name);
                                if(server == null){
                                    new Thread(new Runnable(){
                                        public void run(){
                                            try { 
                                                CCNRestrictedServer server = new CCNRestrictedServer(_namespace, name);
                                                _lecznica.putRestrictedServer(name, server);
                                                server.start();
                                            } catch (MalformedContentNameStringException ex) {
                                                Logger.getLogger(CCNClientChat.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (ConfigurationException ex) {
                                                Logger.getLogger(CCNServer.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (IOException ex) {
                                                Logger.getLogger(CCNServer.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    }).start();

                                } else {
                                    server.setMode(CCNRestrictedServer.Mode.MODE_READY);
                                }
                            }
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

	public CCNServer(String namespace) throws MalformedContentNameStringException {
		_chat = new CCNServerChat(this, namespace);
		_thd = new Thread(this, "CCNServer:"+ namespace);
                _thd.setDaemon(true);
                _lecznica = new Lecznica();
                _namespace = namespace;
	}

	/**
	 * Called by window thread when when window closes
	 */
	public void stop() throws IOException {
		_chat.shutdown();
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
		_chat.listen();
	}


	public void recvMessage(String message) {
		_queue.add(message);
//                System.out.print("Rec message: "+message );
	}

	public void sendMessage(String message) throws IOException {
            _chat.sendMessage(message);
        }

    /**
     * @return the mainApp
     */
    public Lecznica getMainApp() {
        return _lecznica;
    }

    /**
     * @param mainApp the mainApp to set
     */
    public void setMainApp(Lecznica mainApp) {
        this._lecznica = mainApp;
    }
}
