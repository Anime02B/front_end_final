package cl;

import java.sql.Date;

public class Money extends MoneyZero {
    private int id;
    private String name;
    private TypeMoney from_type;
    private TypeMoney to_type;

    public Money(int id, String name, TypeMoney from_type, TypeMoney to_type) {
        this.id = id;
        this.name = name;
        this.from_type = from_type;
        this.to_type = to_type;
    }

    public Money(Date m_date, int amount, int id, String name, TypeMoney from_type, TypeMoney to_type) {
        super(m_date, amount);
        this.id = id;
        this.name = name;
        this.from_type = from_type;
        this.to_type = to_type;
    }

    public Money() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeMoney getFrom_type() {
        return from_type;
    }

    public void setFrom_type(TypeMoney from_type) {
        this.from_type = from_type;
    }

    public TypeMoney getTo_type() {
        return to_type;
    }

    public void setTo_type(TypeMoney to_type) {
        this.to_type = to_type;
    }
}
