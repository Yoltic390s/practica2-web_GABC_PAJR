package com.GABC_JRPA.Practica2.controller;

import com.GABC_JRPA.Practica2.model.Estudiante;
import com.GABC_JRPA.Practica2.model.Usuario;
import com.GABC_JRPA.Practica2.repository.EstudianteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteRepository estudianteRepository;

    public EstudianteController(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    private Usuario obtenerUsuario(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }

    @GetMapping
    public String listarEstudiantes(Model model, HttpSession session) {
        Usuario user = obtenerUsuario(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("nombreUsuario", user.getNombre());
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        return "lista_estudiantes";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        return "formulario_estudiante";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        Estudiante est = estudianteRepository.findById(id).orElseThrow();
        model.addAttribute("estudiante", est);
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        return "formulario_estudiante";
    }

    @PostMapping("/guardar")
    public String guardarEstudiante(@ModelAttribute Estudiante estudiante, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        estudianteRepository.save(estudiante);
        return "redirect:/estudiantes";
    }
    //aqui tienes tu pagina pendejo
    @GetMapping("/borrar/{id}")
    public String mostrarConfirmacionBorrar(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        Estudiante est = estudianteRepository.findById(id).orElseThrow();
        model.addAttribute("estudiante", est);
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        return "confirmar_borrado";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable("id") Long id, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        estudianteRepository.deleteById(id);
        return "redirect:/estudiantes";
    }
}