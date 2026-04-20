package com.GABC_JRPA.Practica2.controller;

import com.GABC_JRPA.Practica2.model.Curso;
import com.GABC_JRPA.Practica2.model.Usuario;
import com.GABC_JRPA.Practica2.repository.CursoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoRepository cursoRepository;

    public CursoController(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    private Usuario obtenerUsuario(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }

    @GetMapping
    public String listarCursos(Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        model.addAttribute("cursos", cursoRepository.findAll());
        return "lista_cursos";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("curso", new Curso());
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        return "formulario_curso";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        model.addAttribute("curso", cursoRepository.findById(id).orElseThrow());
        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        return "formulario_curso";
    }

    @PostMapping("/guardar")
    public String guardarCurso(@ModelAttribute Curso curso, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        cursoRepository.save(curso);
        return "redirect:/cursos";
    }


    @GetMapping("/borrar/{id}")
    public String mostrarConfirmacionBorrar(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        Curso curso = cursoRepository.findById(id).orElseThrow();

        // Armamos el diccionario
        Map<String, String> detalles = new LinkedHashMap<>();
        detalles.put("Código", curso.getCodigo());
        detalles.put("Nombre del Curso", curso.getNombre());
        detalles.put("Total de Créditos", String.valueOf(curso.getCreditos()));

        model.addAttribute("nombreUsuario", obtenerUsuario(session).getNombre());
        model.addAttribute("entidadNombre", "Curso");
        model.addAttribute("detalles", detalles);
        model.addAttribute("urlEliminar", "/cursos/eliminar/" + id);
        model.addAttribute("urlRegresar", "/cursos");

        return "confirmar_borrar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable("id") Long id, HttpSession session) {
        if (obtenerUsuario(session) == null) return "redirect:/login";
        cursoRepository.deleteById(id);
        return "redirect:/cursos";
    }
}