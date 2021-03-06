package brown.platform.managers.library;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

import brown.communication.messageserver.IOfflineMessageServer;
import brown.logging.library.ErrorLogging;
import brown.platform.managers.IAgentManager;
import brown.user.agent.IAgent;
import brown.user.agent.IAgentBackend;
import brown.user.agent.library.OfflineAgentBackend;
import brown.user.main.IAgentConfig;

public class AgentManager implements IAgentManager {

  private boolean lock;
  private List<IAgentConfig> agentConfigs; 
  private List<OfflineAgentRunnable> agentRunnables;

  public AgentManager() {
    this.lock = false;
    this.agentConfigs = new LinkedList<IAgentConfig>(); 
    this.agentRunnables = new LinkedList<OfflineAgentRunnable>();
  }

  @Override
  public void createAgent(IAgentConfig agentConfig) {
    if (!this.lock) {
      this.agentConfigs.add(agentConfig); 
    } else {
      ErrorLogging.log("ERROR: Agent Manager: Cannot add agent runnable, Manager locked"); 
    }
  }
  
  @Override
  public void startAgents(IOfflineMessageServer messageServer) {
    // TODO: include agent name
    // TODO: abstract out agent logic. 
    
    for (IAgentConfig agentConfig : this.agentConfigs) {
      OfflineAgentRunnable agentRunnable = new OfflineAgentRunnable(
          agentConfig.getAgentClass(), agentConfig.getAgentName(), messageServer);
      
      this.agentRunnables.add(agentRunnable); 
    }
    
    for (OfflineAgentRunnable agentRunnable : this.agentRunnables) {
      agentRunnable.run(); 
    }
  }
  
  @Override
  public void lock() {
    this.lock = true;
  }

  private class OfflineAgentRunnable implements Runnable {

    private String agentString;
    private String agentName; 
    private IOfflineMessageServer messageServer;

    public OfflineAgentRunnable(String agentString, String agentName, 
        IOfflineMessageServer messageServer) {
      this.agentName = agentName; 
      this.agentString = agentString;
      this.messageServer = messageServer;
    }

    @Override
    public void run() {
      try {
        Class<?> cl = Class.forName(agentString);
        Constructor<?> cons = cl.getConstructor(String.class);
        IAgent agent = (IAgent) cons.newInstance(this.agentName);
        
        IAgentBackend agentBackend = new OfflineAgentBackend(this.messageServer, agent);
        agent.addAgentBackend(agentBackend);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


}
