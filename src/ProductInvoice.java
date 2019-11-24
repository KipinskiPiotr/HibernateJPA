import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Product_Invoice")
public class ProductInvoice implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn
    private Invoice invoice;

    private int quantity;

    public Product getProduct() {
        return product;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductInvoice(){

    }

    public ProductInvoice(Invoice invoice, Product product, int quantity){
        this.invoice = invoice;
        this.product = product;
        this.quantity = quantity;
        product.getProductInvoices().add(this);
    }
}
