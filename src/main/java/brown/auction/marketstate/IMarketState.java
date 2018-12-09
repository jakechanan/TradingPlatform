package brown.auction.marketstate;

import java.util.List;
import java.util.Map;

import brown.auction.prevstate.library.PrevStateInfo;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.library.Order;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.TradeMessage;
import brown.platform.messages.library.TradeRequestMessage;

/**
 * stores the internal state of a market as 
 * bidding is occurring. Consists of a series of getters 
 * and setters for fields of the market.
 * 
 * @author acoggins
 */
public interface IMarketState {

    // part of Market, not state!
    public Integer getID(); 
  
    // part of Market, not state!
    public List<ITradeable> getTradeables();
    
    // part of Market, not state!
    public void addBid(TradeMessage bid);
    public void clearBids();
    public List<TradeMessage> getBids(); 
    
    // Move to Market -- b/c static across ALL Markets!
    public Map<ITradeable, Double> getIncrement(); 
    public void setIncrement(Map<ITradeable, Double> increment);
    public Double getFlatIncrement();
    public void setFlatIncrement(Double increment);    
    
    // Allocation rule
    public Map<Integer,List<ITradeable>> getAllocation();
    public void setAllocation(Map<Integer,List<ITradeable>> allocation);
    
    // Payment rule
    public void setPayments(List<Order> payment);
    public List<Order> getPayments();
    
    // orders (this is from the payment rule)
    // delete this !!! just use set Payments
    public void clearOrders();
    
    // Query rule
    public TradeRequestMessage getTRequest(); 
    public void setTRequest(TradeRequestMessage t);
    
    // Activity rule
    public boolean getAcceptable();
    public void setAcceptable(boolean b);
    
    public Map<ITradeable,Double> getReserve();
    public void setReserve(Map<ITradeable,Double> o); 
    
    public Map<ITradeable, List<Integer>> getAltAlloc();
    public void setAltAlloc(Map<ITradeable, List<Integer>> o);
    
    // IR policy 
    public Map<Integer, List<GameReportMessage>> getReport();
    public void setReport(Map<Integer,List<GameReportMessage>> gameReport);

    // Termination condition
    public long getTime();
   
    public void tick();
    public int getTicks();  
   
    public boolean isOpen();
    public void close(); 
    
    public boolean getOver();
    public void setOver(boolean b);
    
}
