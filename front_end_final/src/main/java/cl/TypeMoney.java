package cl;

public class TypeMoney {
    private int id;
    private String name;
    private int amount;

    private String img;

    public TypeMoney(int id, String name, int amount, String img) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.img = img;
    }

    public TypeMoney() {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
