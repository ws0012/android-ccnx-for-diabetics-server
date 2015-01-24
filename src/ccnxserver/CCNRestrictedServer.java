/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccnxserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;
import org.pl.lecznica.messages.MessageFactory;
import org.pl.lecznica.util.Messager;

/**
 *
 * @author Wojtek
 */
public class CCNRestrictedServer extends CCNServer {
    private String _name;
    private Mode _mode;

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
            MODE_READY,
            MODE_DONE,    
            MODE_LISTEN,
            MODE_CLOSE
    }

    public CCNRestrictedServer(String namespace, String restrictedName) throws MalformedContentNameStringException {
        super(namespace + restrictedName);
        _name = restrictedName;
        _mode = Mode.MODE_READY;

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
        output("CCNServer:"+_namespace+" run \n");
        Messager messager = new Messager();
        while(true) {        
            switch(getMode()) {
            case MODE_READY:
                String textMessage = messager.getStringMessage(_name, MessageFactory.READY_MESSAGE);
                try {
                sendMessage(textMessage);
                } catch (IOException ex) {
                    Logger.getLogger(CCNServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            setMode(Mode.MODE_LISTEN);
            break;
            case MODE_DONE:
                String doneMessage = messager.getStringMessage(_name, MessageFactory.DONE_MESSAGE);
                try {
                sendMessage(doneMessage);
                } catch (IOException ex) {
                    Logger.getLogger(CCNServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                setMode(Mode.MODE_LISTEN);
                new Thread(new Runnable(){
                        public void run(){
                            try {
                                Thread.sleep(10000);
                                setMode(Mode.MODE_CLOSE);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(CCNRestrictedServer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }}).start();
                
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
                    output("CCNServer:"+_namespace+": "+ message+" \n");
                try {
                    String result = _lecznica.processMessage(message);
                    if(result != null){
                        if((MessageFactory.INIT_MESSAGE).equals(result)) _mode = Mode.MODE_READY;
                        else if((MessageFactory.CLOSE_MESSAGE).equals(result)) _mode = Mode.MODE_CLOSE;
                        else sendMessage(result);
                    }
                    
                } catch (Exception ex) {
                    Logger.getLogger(CCNRestrictedServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                break;
            }

        }
    }
    
    @Override
    public void stop() throws IOException {
	super.stop();
        getMainApp().removeRestrictedServer(_name);
	}
    
    
}
