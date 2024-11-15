class Cuenta {
    private double saldo;

    public Cuenta(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo.");
        }
        this.saldo = saldoInicial;
    }

    public void ingresar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a ingresar debe ser mayor que 0.");
        }
        saldo += monto;
        System.out.println("Se ingresaron " + monto + " unidades. Saldo actual: " + saldo);
    }

    public void extraer(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a extraer debe ser mayor que 0.");
        }
        if (monto > saldo) {
            throw new IllegalArgumentException("Fondos insuficientes. Saldo actual: " + saldo);
        }
        saldo -= monto;
        System.out.println("Se extrajeron " + monto + " unidades. Saldo actual: " + saldo);
    }

    public double getSaldo() {
        return saldo;
    }
}

class CuentaConLimite extends Cuenta {
    private double limite;

    public CuentaConLimite(double saldoInicial, double limiteInicial) {
        super(saldoInicial);
        if (limiteInicial < 0) {
            throw new IllegalArgumentException("El límite inicial no puede ser negativo.");
        }
        this.limite = limiteInicial;
    }

    @Override
    public void extraer(double monto) {
        if (monto > limite) {
            throw new IllegalArgumentException("El monto excede el límite permitido: " + limite);
        }
        super.extraer(monto); 
    }

    public void ajustarLimite() {
        limite = getSaldo() / 2;
        System.out.println("El límite se ajustó automáticamente a: " + limite);
    }

    public double getLimite() {
        return limite;
    }
}


public class ejercicio4 {
    public static void main(String[] args) {
        try {
        
            Cuenta cuentaBasica = new Cuenta(500);
            cuentaBasica.ingresar(200);
            cuentaBasica.extraer(150);
            System.out.println("Saldo final en cuenta básica: " + cuentaBasica.getSaldo());

            CuentaConLimite cuentaLimitada = new CuentaConLimite(1000, 300);
            cuentaLimitada.ingresar(500);
            cuentaLimitada.extraer(250);
            System.out.println("Saldo antes de ajustar el límite: " + cuentaLimitada.getSaldo());
            cuentaLimitada.ajustarLimite();
            System.out.println("Nuevo límite: " + cuentaLimitada.getLimite());
            cuentaLimitada.extraer(300); 
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
