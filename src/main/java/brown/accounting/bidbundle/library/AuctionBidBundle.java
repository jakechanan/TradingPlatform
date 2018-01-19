package brown.accounting.bidbundle.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.accounting.bid.AuctionBid;
import brown.accounting.bidbundle.IBidBundle;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;

/**
  * The built-in BidBundle is called SimpleBidBundle,
  * and holds one double. 
  */
public class AuctionBidBundle implements IBidBundle {
	private final AuctionBid BIDS;
	private final BundleType BT;
	
	/**
	 * For Kryo do not use
	 */
	public AuctionBidBundle() {
		this.BIDS = null;
		this.BT = null;
	}
	
	/**
	 * Actual constructor
	 * @param move - agent's bid
	 * @param agent - agent ID
	 */
	public AuctionBidBundle(Map<ITradeable, Double> bids) {
		if (bids == null) {
			throw new IllegalArgumentException("Null bids");
		}
		List<ITradeable> t = new LinkedList<ITradeable>();
		t.add(new SimpleTradeable(0));
		for (ITradeable b : t) {
		  bids.put(b, 1.0);
		}
		ITradeable a = new SimpleTradeable(0);
		bids.put(a, 1000.0);
		this.BIDS = new AuctionBid(bids);
		this.BT = BundleType.Simple;
	}

  @Override
	public double getCost() {
		double total = 0;
		for (Double b : this.BIDS.bids.values()) {
			total += b;
		}
		return total;
	}

  public AuctionBid getBids() {
    return this.BIDS;
  }
  
	@Override
	public BundleType getType() {
		return this.BT;
	}
	
	public Double getBid(ITradeable t) {
	  return BIDS.bids.get(t);
	}
	
	@Override
	public String toString() {
		return "[" + this.BT + ": " + this.BIDS + "]";
	}
	
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((BIDS == null) ? 0 : BIDS.hashCode());
    result = prime * result + ((BT == null) ? 0 : BT.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return(obj instanceof AuctionBidBundle && 
        ((AuctionBidBundle) obj).BIDS.equals(this.BIDS) &&
        ((AuctionBidBundle) obj).BT.equals(this.BT));
  }	
}