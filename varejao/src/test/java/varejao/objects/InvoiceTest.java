package varejao.objects;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class InvoiceTest {

    private Sale sale;
    private String[] expectedInvoiceContent;

    public InvoiceTest(Sale sale, String[] expectedInvoiceContent) {
        this.sale = sale;
        this.expectedInvoiceContent = expectedInvoiceContent;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Product product1 = new Product("001", "Laptop", 1500.0, "unit");
        Product product2 = new Product("002", "Mouse", 50.0, "unit");
        SaleItem item1 = new SaleItem(product1, 1);
        SaleItem item2 = new SaleItem(product2, 2);

        Customer customer1 = new Customer("johndoe", Customer.CustomerType.Default, new Address("SP", true));
        Sale sale1 = new Sale(Arrays.asList(item1, item2), customer1, PaymentMethod.OTHER, "2023-07-01");

        String[] expectedInvoice1 = {
            "Invoice for Customer: johndoe",
            "Date: 2023-07-01",
            "Items:",
            "  - Laptop: 1 x 1500.0 = 1500.0",
            "  - Mouse: 2 x 50.0 = 100.0",
            "Total: 1600.0",
            "Discount: 0.0",
            "Freight: 7.0",
            "ICMS: 192.0",
            "Municipal Tax: 64.0",
            "Cashback: 0.0",
            "Final Price: 1863.0"
        };

        return Arrays.asList(new Object[][]{
            {sale1, expectedInvoice1}
        });
    }

    @Test
    public void testPrintInvoice() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Invoice invoice = new Invoice(sale);
        invoice.printInvoice();

        String[] printedInvoice = outContent.toString().split("\n");
        for (int i = 0; i < expectedInvoiceContent.length; i++) {
            assertTrue(printedInvoice[i].trim().contains(expectedInvoiceContent[i].trim()));
        }
    }
}
