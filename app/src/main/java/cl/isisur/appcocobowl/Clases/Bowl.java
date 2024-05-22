package cl.isisur.appcocobowl.Clases;

public class Bowl {
    private String idBowl;
    private String nombre;
    private String estado;
    private String nombreTBowl;

    private Integer cantidad;

    public Bowl()
    {
        this.idBowl="";
        this.nombre="";
        this.estado="";
        this.nombreTBowl="";
        this.cantidad= 0;
    }

    public Bowl( String idBowl, String nombre, String estado,String nombreTBowl, Integer cantidad )
    {
        this.idBowl=idBowl;
        this.nombre=nombre;
        this.estado=estado;
        this.nombreTBowl=nombreTBowl;
        this.cantidad=0;
    }
    public String getIdBowl() {
        return idBowl;
    }
    public void setIdBowl(String idBowl) {
        this.idBowl = idBowl;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getNombreTBowl() {return nombreTBowl;}
    public void setNombreTBowl(String nombreTBowl) {this.nombreTBowl = nombreTBowl;}
    public Integer getCantidad() {return cantidad;}
    public void setCantidad(Integer cantidad) {this.cantidad = 0;}

    @Override
    public String toString() {
        return "Autor{" +
                "idBowl='" + idBowl + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", nombreTBowl='"+nombreTBowl+'\''+
                ", cantidad='"+cantidad+'\''+
                '}';
    }

}
