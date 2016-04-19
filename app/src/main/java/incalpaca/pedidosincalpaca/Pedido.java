package incalpaca.pedidosincalpaca;

import java.util.ArrayList;

/**
 * Created by josimar on 17/04/16.
 */
public class Pedido {
    private int id;
    private String items;
    private String nombre;
    private String empresa;
    private String direccion;
    private String pais;
    private String email;
    private String observaciones;
    private String foto;
    private String fecha;
    public Pedido()
    {
    }
    public Pedido(String items,String nombre, String empresa, String direccion, String pais, String email, String observaciones, String foto, String fecha)
    {
        //this.id=id;
        this.items=items;
        this.nombre=nombre;
        this.empresa=empresa;
        this.direccion=direccion;
        this.pais=pais;
        this.email=email;
        this.observaciones=observaciones;
        this.foto=foto;
        this.fecha=fecha;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setItems(String i) {
        this.items = i;
    }
    public void setNombre(String n) {
        this.nombre = n;
    }
    public void setEmpresa(String e) {
        this.empresa = e;
    }
    public void setDireccion(String d) {
        this.direccion = d;
    }
    public void setPais(String p) {
        this.pais = p;
    }
    public void setEmail(String e) {
        this.email = e;
    }
    public void setObservaciones(String o) {
        this.observaciones = o;
    }
    public void setFoto(String f) {
        this.foto = f;
    }
    public void setFecha(String f) {
        this.fecha = f;
    }


    public int getId() {
        return id;
    }
    public String getItems() { return items; }
    public String getNombre() {
        return nombre;
    }
    public String getEmpresa() {
        return empresa;
    }
    public String getDireccion() {
        return direccion;
    }
    public String getPais() {
        return pais;
    }
    public String getEmail() {
        return email;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public String getFoto() {
        return foto;
    }
    public String getFecha() {
        return fecha;
    }
}
