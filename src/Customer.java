import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Customers")
public class Customer extends Company{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private int discount;

    @OneToMany(mappedBy = "customer")
    @Cascade(CascadeType.ALL)
    private Set<Invoice> invoices = new HashSet<>();

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setCustomer(this);
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Customer(){

    }

    public Customer(String companyName, int discount, Address address){
        super(companyName, address);
        this.discount = discount;
    }
}
