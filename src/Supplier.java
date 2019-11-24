import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Suppliers")
public class Supplier extends Company{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    private int bankAccountNumber;

    @OneToMany(mappedBy = "supplier")
    private Set<Product> products = new HashSet<>();

    public Set<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setSupplier(this);
    }

    public Supplier(){

    }

    public Supplier(String companyName, int bankAccountNumber, Address address){
        super(companyName, address);
        this.bankAccountNumber = bankAccountNumber;
    }

}
