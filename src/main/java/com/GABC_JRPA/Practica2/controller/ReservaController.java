package com.GABC_JRPA.Practica2.controller;

import com.GABC_JRPA.Practica2.model.Reserva;
import com.GABC_JRPA.Practica2.model.Usuario;
import com.GABC_JRPA.Practica2.repository.ClienteRepository;
import com.GABC_JRPA.Practica2.repository.ReservaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository; // Necesario para el menú desplegable

    public ReservaController(ReservaRepository reservaRepository, ClienteRepository clienteRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
    }

    private Usuario obtenerUsuario(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }

    @GetMapping
    public String listarReservas(Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        model.addAttribute("reservas", reservaRepository.findAll());
        return "lista_reservas";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("reserva", new Reserva());
        // Enviamos los clientes para llenar el <select>
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        return "formulario_reserva";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("reserva", reservaRepository.findById(id).orElseThrow());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        return "formulario_reserva";
    }

    @PostMapping("/guardar")
    public String guardarReserva(@ModelAttribute Reserva reserva, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        reservaRepository.save(reserva);
        return "redirect:/reservas";
    }

    @GetMapping("/borrar/{id}")
    public String mostrarConfirmacionBorrar(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        Reserva reserva = reservaRepository.findById(id).orElseThrow();

        Map<String, String> detalles = new LinkedHashMap<>();
        detalles.put("Habitación", reserva.getHabitacion() != null ? String.valueOf(reserva.getHabitacion()) : "Sin Cuarto");
        detalles.put("Fecha Entrada", reserva.getFechaEntrada() != null ? reserva.getFechaEntrada().toString() : "Sin Fecha");
        detalles.put("Fecha Salida", reserva.getFechaSalida() != null ? reserva.getFechaSalida().toString() : "Sin Fecha");
        detalles.put("ID Cliente", reserva.getIdCliente() != null ? String.valueOf(reserva.getIdCliente()) : "Desconocido");

        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        model.addAttribute("entidadNombre", "Reserva");
        model.addAttribute("detalles", detalles);
        model.addAttribute("urlEliminar", "/reservas/eliminar/" + id);
        model.addAttribute("urlRegresar", "/reservas");

        return "confirmar_borrar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable("id") Long id, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        reservaRepository.deleteById(id);
        return "redirect:/reservas";
    }
}