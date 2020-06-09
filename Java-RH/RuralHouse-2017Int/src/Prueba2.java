import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public final class Prueba2
{
    public static void main(String[] args) throws Exception {
 
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(
                "objectdb://localhost:6136/myapp.odb;admin");
        emf.createEntityManager().close();
        emf.close();
    }
}
