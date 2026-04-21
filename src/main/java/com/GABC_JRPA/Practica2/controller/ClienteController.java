package com.GABC_JRPA.Practica2.controller;

import com.GABC_JRPA.Practica2.model.Cliente;
import com.GABC_JRPA.Practica2.model.Usuario;
import com.GABC_JRPA.Practica2.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    private Usuario obtenerUsuario(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }

    @GetMapping
    public String listarClientes(Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "lista_clientes";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        return "formulario_cliente";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("cliente", clienteRepository.findById(id).orElseThrow());
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        return "formulario_cliente";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/borrar/{id}")
    public String mostrarConfirmacionBorrar(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        Cliente cliente = clienteRepository.findById(id).orElseThrow();

        Map<String, String> detalles = new LinkedHashMap<>();
        detalles.put("Nombre Completo", cliente.getNombreCompleto() != null ? cliente.getNombreCompleto() : "Desconocido");
        detalles.put("Teléfono", cliente.getTelefono() != null && !cliente.getTelefono().isEmpty() ? cliente.getTelefono() : "Sin teléfono");
        detalles.put("Correo", cliente.getCorreo() != null && !cliente.getCorreo().isEmpty() ? cliente.getCorreo() : "Sin correo");

        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        model.addAttribute("entidadNombre", "Cliente");
        model.addAttribute("detalles", detalles);
        model.addAttribute("urlEliminar", "/clientes/eliminar/" + id);
        model.addAttribute("urlRegresar", "/clientes");

        return "confirmar_borrar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long id, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        clienteRepository.deleteById(id);
        return "redirect:/clientes";
    }
}