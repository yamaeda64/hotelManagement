package client.model.customer;


public class CreditCard
{
    private String cardNumber;
    private int expMonth;
    private int expYear;
    private int cvv;
    
    
    // Getters & Setters
    
    
    public String getCardNumber()
    {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }
    
    public int getExpMonth()
    {
        return expMonth;
    }
    
    public void setExpMonth(int expMonth)
    {
        this.expMonth = expMonth;
    }
    
    public int getExpYear()
    {
        return expYear;
    }
    
    public void setExpYear(int expYear)
    {
        this.expYear = expYear;
    }
    
    public int getCvv()
    {
        return cvv;
    }
    
    public void setCvv(int cvv)
    {
        this.cvv = cvv;
    }
}