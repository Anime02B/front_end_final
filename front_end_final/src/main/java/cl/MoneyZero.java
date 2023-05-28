package cl;

import java.sql.Date;

public class MoneyZero {
    private Date m_date;
    private int amount;

    public MoneyZero() {
    }

    public MoneyZero(Date m_date, int amount) {
        this.m_date = m_date;
        this.amount = amount;
    }

    public Date getM_date() {
        return m_date;
    }

    public void setM_date(Date m_date) {
        this.m_date = m_date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

