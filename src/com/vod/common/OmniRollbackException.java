package com.vod.common;
/**
 * This exception is for Transaction Management, throw/re-throw this exception when the transaction requires rollback.
 * It should be thrown in BizService implementation's ..Tx method ONLY 
 * @author Haoyu Tang
 * 
 *  
 */
public class OmniRollbackException extends RuntimeException
{

    /**
     * 
     */
    private static final long serialVersionUID = 7923687226961120919L;

     /**
      * Use rollback(msg) instead
      * @param reason
      */
     @Deprecated
    public OmniRollbackException(String reason)
    {
        super(reason);
    }
    
     /**
      * Use rollback(e) instead
      * @param Exception e
      */
     @Deprecated
     public OmniRollbackException(Throwable e)
    {
        super(e);
    }


}
