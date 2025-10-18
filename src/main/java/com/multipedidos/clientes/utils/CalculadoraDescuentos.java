package com.multipedidos.clientes.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;

/**
 * Clase de utilidad para cálculos relacionados con descuentos, IVA y totales.
 * Implementa la lógica de negocio común para ambos microservicios.
 */
public class CalculadoraDescuentos {

    private static final Logger log = Logger.getLogger(CalculadoraDescuentos.class.getName());

    private static final BigDecimal IVA = new BigDecimal("0.15"); // 15% IVA
    private static final BigDecimal DESCUENTO_BASICO = new BigDecimal("0.05"); // 5%
    private static final BigDecimal DESCUENTO_MEDIO = new BigDecimal("0.10"); // 10%
    private static final BigDecimal DESCUENTO_PREMIUM = new BigDecimal("0.15"); // 15%

    /**
     * Calcula el total con IVA aplicado.
     *
     * @param subtotal El subtotal sin IVA
     * @return El total con IVA incluido
     */
    public static BigDecimal calcularTotalConIVA(BigDecimal subtotal) {
        if (subtotal == null || subtotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El subtotal no puede ser nulo o negativo");
        }

        BigDecimal iva = subtotal.multiply(IVA);
        BigDecimal total = subtotal.add(iva);
        
        log.info("Cálculo de IVA - Subtotal: " + subtotal + ", IVA: " + iva + ", Total: " + total);
        
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Aplica descuento escalonado basado en el monto total.
     *
     * @param total El total sobre el cual aplicar el descuento
     * @return El total con descuento aplicado
     */
    public static BigDecimal aplicarDescuentoEscalonado(BigDecimal total) {
        if (total == null || total.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El total no puede ser nulo o negativo");
        }

        BigDecimal descuento;
        
        if (total.compareTo(new BigDecimal("1000")) >= 0) {
            descuento = total.multiply(DESCUENTO_PREMIUM);
            log.info("Aplicando descuento premium (15%) - Total: " + total + ", Descuento: " + descuento);
        } else if (total.compareTo(new BigDecimal("500")) >= 0) {
            descuento = total.multiply(DESCUENTO_MEDIO);
            log.info("Aplicando descuento medio (10%) - Total: " + total + ", Descuento: " + descuento);
        } else if (total.compareTo(new BigDecimal("100")) >= 0) {
            descuento = total.multiply(DESCUENTO_BASICO);
            log.info("Aplicando descuento básico (5%) - Total: " + total + ", Descuento: " + descuento);
        } else {
            descuento = BigDecimal.ZERO;
            log.info("Sin descuento aplicable - Total: " + total);
        }

        BigDecimal totalConDescuento = total.subtract(descuento);
        return totalConDescuento.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula el total final con IVA y descuento aplicados.
     *
     * @param subtotal El subtotal sin IVA ni descuento
     * @return El total final
     */
    public static BigDecimal calcularTotalFinal(BigDecimal subtotal) {
        if (subtotal == null || subtotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El subtotal no puede ser nulo o negativo");
        }

        // Primero aplicar descuento al subtotal
        BigDecimal subtotalConDescuento = aplicarDescuentoEscalonado(subtotal);
        
        // Luego aplicar IVA
        BigDecimal totalFinal = calcularTotalConIVA(subtotalConDescuento);
        
        log.info("Cálculo final - Subtotal original: " + subtotal + 
                ", Con descuento: " + subtotalConDescuento + 
                ", Total final: " + totalFinal);
        
        return totalFinal;
    }

    /**
     * Obtiene el porcentaje de IVA utilizado.
     *
     * @return El porcentaje de IVA como BigDecimal
     */
    public static BigDecimal getPorcentajeIVA() {
        return IVA;
    }

    /**
     * Obtiene el porcentaje de descuento básico.
     *
     * @return El porcentaje de descuento básico como BigDecimal
     */
    public static BigDecimal getPorcentajeDescuentoBasico() {
        return DESCUENTO_BASICO;
    }

    /**
     * Obtiene el porcentaje de descuento medio.
     *
     * @return El porcentaje de descuento medio como BigDecimal
     */
    public static BigDecimal getPorcentajeDescuentoMedio() {
        return DESCUENTO_MEDIO;
    }

    /**
     * Obtiene el porcentaje de descuento premium.
     *
     * @return El porcentaje de descuento premium como BigDecimal
     */
    public static BigDecimal getPorcentajeDescuentoPremium() {
        return DESCUENTO_PREMIUM;
    }
}
