package cl;

import java.sql.Date;

public class Money_for_front extends Money{

    private boolean is_income;

    public Money_for_front(int id, String name, TypeMoney from_type, TypeMoney to_type, boolean is_income) {
        super(id, name, from_type, to_type);
        this.is_income = is_income;
    }

    public Money_for_front(Date m_date, int amount, int id, String name, TypeMoney from_type, TypeMoney to_type, boolean is_income) {
        super(m_date, amount, id, name, from_type, to_type);
        this.is_income = is_income;
    }

    public Money_for_front(boolean is_income) {
        this.is_income = is_income;
    }

    public Money_for_front() {
    }



    public boolean isIs_income() {
        return is_income;
    }

    public void setIs_income(boolean is_income) {
        this.is_income = is_income;
    }
}
