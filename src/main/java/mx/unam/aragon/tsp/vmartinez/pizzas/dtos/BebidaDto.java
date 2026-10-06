package mx.unam.aragon.tsp.vmartinez.pizzas.dtos;

public class BebidaDto {
    private int id;
    private String nombre;
    private String tamano;
    private double precio;

    public BebidaDto() {}

    public BebidaDto(int id, String nombre, String tamano, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.tamano = tamano;
        this.precio = precio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}