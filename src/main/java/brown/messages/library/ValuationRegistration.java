package brown.messages.library; 

import brown.messages.library.Registration;
import brown.value.valuation.IValuation;
import brown.value.valuation.library.AdditiveValuation;
import brown.value.valuation.library.BundleValuation;
import brown.value.valuationrepresentation.AbsValuationRepresentation;

/**
 * sends agents their valuations from the server.
 * @author acoggins
 *
 */
public class ValuationRegistration extends Registration {
  
  private final AbsValuationRepresentation personalValue; 
  private final AdditiveValuation addDistribution; 
  private final BundleValuation bundleDistribution; 
  
  public ValuationRegistration() {
    super(null);
    this.personalValue = null; 
    this.addDistribution = null; 
    this.bundleDistribution = null; 
  }
  
  /**
   * valuation registration without underlying distribution.
   * @param id
   * @param personalValue
   */
  public ValuationRegistration(Integer id, AbsValuationRepresentation personalValue){ 
    super(id); 
    this.personalValue = personalValue; 
    this.addDistribution = null; 
    this.bundleDistribution = null; 
  }
      
  /**
   * valuation registration with underlying distribution.
   * @param id
   * @param personalValue
   */
  public ValuationRegistration(Integer id, AbsValuationRepresentation personalValue,
      IValuation distribution) {
    super(id); 
    this.personalValue = personalValue; 
    
    if (distribution instanceof AdditiveValuation) {
      AdditiveValuation val = (AdditiveValuation) distribution;
      this.addDistribution = val; 
      this.bundleDistribution = null; 
    }
    
    else if (distribution instanceof BundleValuation) {
      BundleValuation val = (BundleValuation) distribution;
      this.addDistribution = null; 
      this.bundleDistribution = val; 
    }
    
    else {
      this.addDistribution = null; 
      this.bundleDistribution = null; 
    }

  }
  
  /**
   * getter.
   * @return the agent's valuation
   */
  public AbsValuationRepresentation getValuation() {
    return this.personalValue; 
  }
  
  /**
   * getter. 
   * @return the distribution on which the agent is bidding, 
   * if applicable. 
   */
  public IValuation getDistribution() {
    if (addDistribution != null) {
        return this.addDistribution; 
    }
    if (bundleDistribution != null) {
      return this.bundleDistribution; 
    }
    return null; 
  }
  
  
}