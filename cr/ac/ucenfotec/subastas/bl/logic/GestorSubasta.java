package cr.ac.ucenfotec.subastas.bl.logic;

import cr.ac.ucenfotec.subastas.bl.dao.DAOOferta;
import cr.ac.ucenfotec.subastas.bl.dao.DAOSubasta;
import cr.ac.ucenfotec.subastas.bl.model.Coleccionista;
import cr.ac.ucenfotec.subastas.bl.model.Objeto;
import cr.ac.ucenfotec.subastas.bl.model.Oferta;
import cr.ac.ucenfotec.subastas.bl.model.Subasta;
import cr.ac.ucenfotec.subastas.bl.model.Usuario;
import cr.ac.ucenfotec.subastas.bl.model.Vendedor;

import java.util.ArrayList;

public class GestorSubasta {

    public GestorSubasta() {
    }

    public Subasta registrarSubasta(String titulo, Usuario creador, ArrayList<Objeto> objetos) {
        validarDatosSubasta(titulo, creador, objetos);

        Subasta subasta = new Subasta(titulo, creador);

        for (Objeto objeto : objetos) {
            if (objeto == null) {
                throw new IllegalArgumentException("No se permiten objetos nulos en la subasta.");
            }
            subasta.agregarObjeto(objeto);
        }

        if (creador instanceof Vendedor) {
            Vendedor vendedor = (Vendedor) creador;
            vendedor.agregarSubasta(subasta);
        }

        try {
            DAOSubasta.insertarSubasta(subasta);
            return subasta;
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar la subasta en la base de datos.");
        }
    }

    public Oferta hacerOferta(Subasta subasta, Coleccionista coleccionista, double monto) {
        if (subasta == null) {
            throw new IllegalArgumentException("Debe seleccionar una subasta válida.");
        }

        if (coleccionista == null) {
            throw new IllegalArgumentException("El oferente no es válido.");
        }

        if (!subasta.isActiva()) {
            throw new IllegalArgumentException("No se puede ofertar en una subasta cerrada.");
        }

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }

        if (subasta.getCreador() != null && subasta.getCreador().equals(coleccionista)) {
            throw new IllegalArgumentException("No puede ofertar en su propia subasta.");
        }

        Oferta mejorOferta = subasta.obtenerMejorOferta();
        if (mejorOferta != null && monto <= mejorOferta.getMonto()) {
            throw new IllegalArgumentException("La oferta debe ser mayor que la oferta actual.");
        }

        Oferta oferta = new Oferta(coleccionista, monto);
        subasta.agregarOferta(oferta);
        coleccionista.agregarOferta(oferta);

        try {
            DAOOferta.insertarOferta(subasta.getId(), oferta);
            return oferta;
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar la oferta en la base de datos.");
        }
    }

    public ArrayList<Subasta> listarSubastas() {
        try {
            return DAOSubasta.listarSubastas();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las subastas.");
        }
    }

    public ArrayList<Oferta> listarOfertas(Subasta subasta) {
        if (subasta == null) {
            return new ArrayList<Oferta>();
        }

        try {
            return DAOOferta.listarOfertasPorSubasta(subasta.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las ofertas de la subasta.");
        }
    }

    public Subasta buscarSubastaPorId(int idSubasta) {
        try {
            return DAOSubasta.seleccionarSubastaPorId(idSubasta);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar la subasta.");
        }
    }

    public void cerrarSubasta(Subasta subasta) {
        if (subasta == null) {
            throw new IllegalArgumentException("La subasta indicada no es válida.");
        }

        if (!subasta.isActiva()) {
            throw new IllegalArgumentException("La subasta ya se encuentra cerrada.");
        }

        try {
            DAOSubasta.cerrarSubasta(subasta.getId());
            subasta.cerrarSubasta();
        } catch (Exception e) {
            throw new RuntimeException("Error al cerrar la subasta.");
        }
    }

    private void validarDatosSubasta(String titulo, Usuario creador, ArrayList<Objeto> objetos) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("La subasta debe tener un título válido.");
        }

        if (creador == null) {
            throw new IllegalArgumentException("La subasta debe tener un creador válido.");
        }

        if (objetos == null || objetos.isEmpty()) {
            throw new IllegalArgumentException("La subasta debe tener al menos un objeto.");
        }

        for (Objeto objeto : objetos) {
            if (objeto == null) {
                throw new IllegalArgumentException("No se permiten objetos nulos en la subasta.");
            }

            if (objeto.getNombre() == null || objeto.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("Todos los objetos deben tener nombre.");
            }
        }
    }
}