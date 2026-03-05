import java.util.ArrayList;
import java.util.List;

public class ServiciosSubastas {

    private List<Subasta> subastas = new ArrayList<>();

    public List<Subasta> listarSubastas() { return List.copyOf(subastas); }

}
