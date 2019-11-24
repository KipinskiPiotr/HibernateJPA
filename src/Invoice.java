import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int invoiceNumber;

    @OneToMany(mappedBy = "invoice")
    @Cascade(CascadeType.ALL)
    private Set<ProductInvoice> productInvoices = new HashSet<>();

    @ManyToOne
    private Customer customer;

    public Set<ProductInvoice> getProductInvoices() {
        return productInvoices;
    }

    public void addProduct(Product product, int quantity){
        this.productInvoices.add(new ProductInvoice(this, product, quantity));
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public Invoice(){

    }

    public Invoice(Customer customer){
        this.customer = customer;
    }
}
