import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private String productName;
    private int unitsOnStock;

    @ManyToOne
    @JoinColumn(name="SUPPLIER_ID")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<ProductInvoice> productInvoices = new HashSet<>();

    public Set<ProductInvoice> getProductInvoices() {
        return productInvoices;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        supplier.getProducts().add(this);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        if(!category.getProducts().contains(this))
            category.getProducts().add(this);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        productName = productName;
    }

    public int getUnitsOnStock() {
        return unitsOnStock;
    }

    public void setUnitsOnStock(int unitsOnStock) {
        unitsOnStock = unitsOnStock;
    }

    public Product(){

    }

    public Product(String productName, int unitsOnStock){
        this.productName = productName;
        this.unitsOnStock = unitsOnStock;
    }
}
