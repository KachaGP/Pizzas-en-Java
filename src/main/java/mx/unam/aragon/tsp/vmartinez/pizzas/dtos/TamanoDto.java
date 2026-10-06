package mx.unam.aragon.tsp.vmartinez.pizzas.dtos;

public class TamanoDto {
    private int id;
    private String nombre;
    private int rebanadas;

    public TamanoDto() {}

    public TamanoDto(int id, String nombre, int rebanadas) {
        this.id = id;
        this.nombre = nombre;
        this.rebanadas = rebanadas;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getRebanadas() { return rebanadas; }
    public void setRebanadas(int rebanadas) { this.rebanadas = rebanadas; }
}