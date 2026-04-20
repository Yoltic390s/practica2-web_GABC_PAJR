package com.GABC_JRPA.Practica2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("reservas")
public class Reserva {
    @Id
    @Column("id_reserva")
    private Long idReserva;

    @Column("fecha_entrada")
    private LocalDate fechaEntrada;

    @Column("fecha_salida")
    private LocalDate fechaSalida;

    private Integer habitacion;

    @Column("id_cliente")
    private Long idCliente;

    public Reserva() {}

    public Long getIdReserva() { return idReserva; }
    public void setIdReserva(Long idReserva) { this.idReserva = idReserva; }

    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    public Integer getHabitacion() { return habitacion; }
    public void setHabitacion(Integer habitacion) { this.habitacion = habitacion; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
}