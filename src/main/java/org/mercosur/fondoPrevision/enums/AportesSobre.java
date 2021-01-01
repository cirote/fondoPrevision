package org.mercosur.fondoPrevision.enums;

public enum AportesSobre {
	HABERES("Haberes"),
	AGUINALDO("Aguinaldo");
	
    private final String displayValue;
    
    private AportesSobre(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }

}
