package pe.edu.cibertec.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "montos")
public class Montos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmontos")
    private Integer id;
    @Column(name = "dias")
    private Integer dias;
    @Column(name = "cientocincuenta")
    private Double cientocincuenta;
    @Column(name = "doscientos")
    private Double doscientos;
    @Column(name = "trescientos")
    private Double trescientos;
    @Column(name = "cuatrocientos")
    private Double cuatrocientos;
    @Column(name = "quinientos")
    private Double quinientos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Double getCientocincuenta() {
        return cientocincuenta;
    }

    public void setCientocincuenta(Double cientocincuenta) {
        this.cientocincuenta = cientocincuenta;
    }

    public Double getDoscientos() {
        return doscientos;
    }

    public void setDoscientos(Double doscientos) {
        this.doscientos = doscientos;
    }

    public Double getTrescientos() {
        return trescientos;
    }

    public void setTrescientos(Double trescientos) {
        this.trescientos = trescientos;
    }

    public Double getCuatrocientos() {
        return cuatrocientos;
    }

    public void setCuatrocientos(Double cuatrocientos) {
        this.cuatrocientos = cuatrocientos;
    }

    public Double getQuinientos() {
        return quinientos;
    }

    public void setQuinientos(Double quinientos) {
        this.quinientos = quinientos;
    }
}
