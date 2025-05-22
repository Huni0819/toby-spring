package tobyspring.hellospring;

public class ObjectFactory {
    public PaymentService paymentService() {

        return new PaymentService(this.exRateProvider());
    }

    public ExRateProvider exRateProvider() {

        return new WebApiExRateProvider();
    }
}
