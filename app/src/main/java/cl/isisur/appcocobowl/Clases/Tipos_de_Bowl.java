package cl.isisur.appcocobowl.Clases;

public class Tipos_de_Bowl {
    private String idTBowl;
    private String nombreTBowl;

    public Tipos_de_Bowl()
    {
        this.idTBowl="";
        this.nombreTBowl="";
    }

    public Tipos_de_Bowl( String IdTBowl, String NombreTBowl )
    {
        this.idTBowl=IdTBowl;
        this.nombreTBowl=NombreTBowl;
    }
    public String getIdTBowl() {
        return idTBowl;
    }

    public void setIdTBowl(String idBowl) {
        this.idTBowl = idBowl;
    }

    public String getNombreTBowl() {
        return nombreTBowl;
    }

    public void setNombreTBowl(String NombreTBowl) {
        this.nombreTBowl = NombreTBowl;
    }

    @Override
    public String toString() {
        return "Tipos_de_Bowl{" +
                "IdTBowl='" + idTBowl + '\'' +
                ", Nombre del Bowl='" + nombreTBowl + '\'' +
                '}';
    }
}
