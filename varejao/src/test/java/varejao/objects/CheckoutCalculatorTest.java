package varejao.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import varejao.objects.Customer.CustomerType;

@RunWith(Parameterized.class)
public class CheckoutCalculatorTest {

    private double usedCashback;
    private CustomerType customerType;
    private double expectedTotal;
    private Address address;

    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
            {0.0, CustomerType.Default, 110.0, new Address("SP", false)},
            {20.0, CustomerType.Prime, 40.0, new Address("RJ", true)},
            {10.0, CustomerType.Special, 73.0, new Address("MG", false)},
            // Adicione mais casos de teste conforme necessário
        });
    }

    public CheckoutCalculatorTest(double usedCashback, CustomerType customerType, double expectedTotal, Address address) {
        this.usedCashback = usedCashback;
        this.customerType = customerType;
        this.expectedTotal = expectedTotal;
        this.address = address;
    }

    @Test
    public void testCheckout() {
        // Criação de objetos diretamente, sem o uso de mocks
        Customer customer = new Customer("username", customerType, address);
        Sale sale = new Sale();
        sale.setICMS(50.0);
        sale.setShippingCost(50.0);
        sale.setMunicipalTax(10.0);
        sale.setCustomer(customer);

        CheckoutCalculator calculator = new CheckoutCalculator(sale);

        // Calcula o total
        double total = calculator.checkout(usedCashback);

        // Verifica o valor esperado
        assertEquals(expectedTotal, total, 0.01);
    }
}
