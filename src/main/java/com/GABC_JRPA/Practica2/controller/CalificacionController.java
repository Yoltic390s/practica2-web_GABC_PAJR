package com.GABC_JRPA.Practica2.controller;

import com.GABC_JRPA.Practica2.model.Calificacion;
import com.GABC_JRPA.Practica2.model.Curso;
import com.GABC_JRPA.Practica2.model.Estudiante;
import com.GABC_JRPA.Practica2.model.Usuario;
import com.GABC_JRPA.Practica2.repository.CalificacionRepository;
import com.GABC_JRPA.Practica2.repository.CursoRepository;
import com.GABC_JRPA.Practica2.repository.EstudianteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/calificaciones")
public class CalificacionController {

    private final CalificacionRepository calificacionRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    // Inyectamos los 3 repositorios para poder mostrar nombres reales
    public CalificacionController(CalificacionRepository calRepo, EstudianteRepository estRepo, CursoRepository curRepo) {
        this.calificacionRepository = calRepo;
        this.estudianteRepository = estRepo;
        this.cursoRepository = curRepo;
    }

    private Usuario obtenerUsuario(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }

    @GetMapping
    public String listarCalificaciones(Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";

        // 1. Obtenemos las calificaciones
        Iterable<Calificacion> calificaciones = calificacionRepository.findAll();

        // 2. Creamos "Diccionarios" (Mapas) para traducir IDs a Nombres reales
        Map<Long, String> mapaEstudiantes = new HashMap<>();
        for (Estudiante e : estudianteRepository.findAll()) {
            mapaEstudiantes.put(e.getId(), e.getNombre());
        }

        Map<Long, String> mapaCursos = new HashMap<>();
        for (Curso c : cursoRepository.findAll()) {
            mapaCursos.put(c.getId(), c.getNombre());
        }

        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        model.addAttribute("calificaciones", calificaciones);
        model.addAttribute("mapaEstudiantes", mapaEstudiantes); // Se manda a la vista HTML
        model.addAttribute("mapaCursos", mapaCursos);           // Se manda a la vista HTML

        return "lista_calificaciones";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("calificacion", new Calificacion());
        model.addAttribute("estudiantes", estudianteRepository.findAll()); // Para el menú select
        model.addAttribute("cursos", cursoRepository.findAll());           // Para el menú select
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        return "formulario_calificacion";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("calificacion", calificacionRepository.findById(id).orElseThrow());
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        return "formulario_calificacion";
    }

    @PostMapping("/guardar")
    public String guardarCalificacion(@ModelAttribute Calificacion calificacion, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        calificacionRepository.save(calificacion);
        return "redirect:/calificaciones";
    }

    @GetMapping("/borrar/{id}")
    public String mostrarConfirmacionBorrar(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        Calificacion cal = calificacionRepository.findById(id).orElseThrow();

        String nomEstudiante = "Desconocido";
        if (cal.getEstudianteId() != null) {
            nomEstudiante = estudianteRepository.findById(cal.getEstudianteId()).map(Estudiante::getNombre).orElse("Desconocido");
        }

        String nomCurso = "Desconocido";
        if (cal.getCursoId() != null) {
            nomCurso = cursoRepository.findById(cal.getCursoId()).map(Curso::getNombre).orElse("Desconocido");
        }

        Map<String, String> detalles = new LinkedHashMap<>();
        detalles.put("Estudiante", nomEstudiante);
        detalles.put("Curso", nomCurso);
        detalles.put("Semestre", cal.getSemestre() != null ? cal.getSemestre() : "N/A");
        detalles.put("Calificación Final", cal.getCalificacion() != null ? String.valueOf(cal.getCalificacion()) : "N/A");

        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        model.addAttribute("entidadNombre", "Calificación");
        model.addAttribute("detalles", detalles);
        model.addAttribute("urlEliminar", "/calificaciones/eliminar/" + id);
        model.addAttribute("urlRegresar", "/calificaciones");

        return "confirmar_borrar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCalificacion(@PathVariable("id") Long id, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        calificacionRepository.deleteById(id);
        return "redirect:/calificaciones";
    }
}