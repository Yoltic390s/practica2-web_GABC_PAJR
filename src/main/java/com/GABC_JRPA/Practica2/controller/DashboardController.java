package com.GABC_JRPA.Practica2.controller;

import com.GABC_JRPA.Practica2.model.Alumno;
import com.GABC_JRPA.Practica2.model.Usuario;
import com.GABC_JRPA.Practica2.repository.AlumnoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final AlumnoRepository alumnoRepository;

    public DashboardController(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    private Usuario obtenerUsuarioAutenticado(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }

    @GetMapping
    public String verDashboard(Model model, HttpSession session) {
        Usuario usuario = obtenerUsuarioAutenticado(session);
        if (usuario == null) return "redirect:/login";

        // AQUÍ ENVIAMOS TU NOMBRE A LA PÁGINA PARA QUE NO SE VEA VACÍO
        model.addAttribute("nombreUsuario", usuario.getNombre());

        model.addAttribute("alumnos", alumnoRepository.findAll());
        model.addAttribute("totalSistemas", alumnoRepository.countByCarrera("Ingeniería en Sistemas Computacionales"));
        model.addAttribute("totalMecatronica", alumnoRepository.countByCarrera("Ingeniería Mecatrónica"));
        model.addAttribute("totalAmbiental", alumnoRepository.countByCarrera("Ingeniería Ambiental"));
        model.addAttribute("totalDatos", alumnoRepository.countByCarrera("Ciencias de Datos"));

        return "dashboard";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model, HttpSession session) {
        Usuario usuario = obtenerUsuarioAutenticado(session);
        if (usuario == null) return "redirect:/login";

        model.addAttribute("nombreUsuario", usuario.getNombre());
        model.addAttribute("alumno", new Alumno());
        return "formulario_alumno";
    }

    @PostMapping("/guardar")
    public String guardarAlumno(@ModelAttribute Alumno alumno, HttpSession session) {
        if (obtenerUsuarioAutenticado(session) == null) return "redirect:/login";
        alumnoRepository.save(alumno);
        return "redirect:/dashboard";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model, HttpSession session) {
        Usuario usuario = obtenerUsuarioAutenticado(session);
        if (usuario == null) return "redirect:/login";

        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));

        model.addAttribute("nombreUsuario", usuario.getNombre());
        model.addAttribute("alumno", alumno);
        return "formulario_alumno";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAlumno(@PathVariable("id") Long id, HttpSession session) {
        if (obtenerUsuarioAutenticado(session) == null) return "redirect:/login";
        alumnoRepository.deleteById(id);
        return "redirect:/dashboard";
    }
}