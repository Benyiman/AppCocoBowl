package cl.isisur.appcocobowl.Clases;
import android.os.Parcel;
import android.os.Parcelable;
public class SelectedBowl {
    private String nombre;
    private String estado;

    // Constructor
    public SelectedBowl(String nombre, String estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    // Getters y Setters

    // Métodos para Parcelable
    protected SelectedBowl(Parcel in) {
        nombre = in.readString();
        estado = in.readString();
    }

    public static final Parcelable.Creator<SelectedBowl> CREATOR = new Parcelable.Creator<SelectedBowl>() {
        @Override
        public SelectedBowl createFromParcel(Parcel in) {
            return new SelectedBowl(in);
        }

        @Override
        public SelectedBowl[] newArray(int size) {
            return new SelectedBowl[size];
        }
    };




}
