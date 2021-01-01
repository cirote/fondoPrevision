package org.mercosur.fondoPrevision.cargadedatos;

public enum CargaDatos {
	SALDOS("Saldos"),
	SUELDOS("Sueldos"),
	PRESTAMOS("Prestamos"),
	SALDOSPRESTAMOS("Saldos Prestamos"),
	MOVIMIENTOS("Movimientos"),
	VINCULOCARGO("VinculoFunc-Cargo");
	
    private final String displayValue;
    
    private CargaDatos(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
}
