package incalpaca.pedidosincalpaca;

/**
 * Created by josimar on 18/04/16.
 */
public class Emails {
    private int id;
    private String email1;
    private String email2;
    private String email3;
    public Emails()
    {
    }
    public Emails(int id,String email1,String email2, String email3)
    {
        this.id=id;
        this.email1=email1;
        this.email2=email2;
        this.email3=email3;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setEmail1(String i) {
        this.email1 = i;
    }
    public void setEmail2(String n) {
        this.email2 = n;
    }
    public void setEmail3(String e) {
        this.email3 = e;
    }


    public int getId() {
        return id;
    }
    public String getEmail1() { return email1; }
    public String getEmail2() {
        return email2;
    }
    public String getEmail3() { return email3; }
}
