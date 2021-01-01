package org.mercosur.fondoPrevision.enums;

public enum AprobSolicitud {
	APROBAR("Aprobar"),
	RECHAZAR("Rechazar"),
	ELIMINARAPROB("Eliminar Aprob."),
	ELIMINARRECHAZO("Eliminar Rechazo");
	
    private final String displayValue;
    
    private AprobSolicitud(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }

}
