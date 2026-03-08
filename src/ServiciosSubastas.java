import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiciosSubastas {

    private List<Subasta> subastas = new ArrayList<>();

    public List<Subasta> listarSubastas() { return List.copyOf(subastas); }

    public void crearSubasta(){
        Subasta subasta = new Subasta();
        subastas.add(subasta);
    }

    public void crearObjetoSubasta(Subasta subasta, Objeto objeto){
        //subasta.agregarObjeto(obj);
    }



}
